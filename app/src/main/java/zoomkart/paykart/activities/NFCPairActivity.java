package zoomkart.paykart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.util.Date;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.Item;
import zoomkart.paykart.models.Order;
import zoomkart.paykart.models.ZoomKart;

public class NFCPairActivity extends AppCompatActivity {

    NfcAdapter mNfcAdapter;
    SharedPreferences mSharedPreferences;
    String orderId;
    String customerId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcpair);
        Context context = getApplicationContext();
        getSupportActionBar().setTitle("Tap + Checkout");

        Paper.init(this);

        mSharedPreferences= context.getSharedPreferences("NFC", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("isNFCOn", true);
        editor.commit();

        Pusher pusher = new Pusher("d6f65af47015b83ec19b");
        Pusher pusher1 = new Pusher("d6f65af47015b83ec19b");

        final Channel channel = pusher.subscribe("channel");
        final Channel items_channel = pusher1.subscribe("items-channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.d("[NFCPairActivity] Order", "Tap Complete");
                Log.d("[NFCPairActivity] Order", "Initiate Order Channel and show user UI to place items.");
                final Customer customer = ZoomKart.getCustomer();
                JSONObject json;
                String str_value = "";

                try {

                    json = new JSONObject(data);

                    str_value = json.getString("data");

                } catch (Exception e){
                    e.printStackTrace();
                }

                orderId = str_value;
                customerId = customer.getId();

                //Reset validRead
                editor.putBoolean("validRead", false);
                editor.commit();

            }
        });

        items_channel.bind("items-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String s, String s1, String s2) {
                Item[] items = (new Gson()).fromJson(s2, Item[].class);
                Date date = new Date();
                float totalPrice = 0.0f;
                for (int i = 0; i < items.length; i++){
                    totalPrice += items[i].getPrice();
                }
                Order order = new Order(Integer.parseInt(orderId), customerId, date, totalPrice, items);

                Paper.book(customerId).write("Order ID: " + orderId + "\nDate: " + date.toString(), order);
                Paper.book(customerId).write("CurrentOrder", order);


                for (int i = 0; i < items.length; i++){
                    Log.d("[NFCPairActivity] Item", items[i].getName());
                }

                startBillActivity(order);
            }
        });

        pusher.connect();
        pusher1.connect();


        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }

    private void startBillActivity(Order order){
        Intent i = new Intent(this, BillActivity.class);
        i.putExtra("order", order);

        startActivity(i);
    }
}
