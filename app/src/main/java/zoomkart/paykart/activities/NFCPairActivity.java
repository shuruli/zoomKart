package zoomkart.paykart.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.cardemulation.CardEmulation;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONObject;

import java.nio.charset.Charset;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.Item;
import zoomkart.paykart.models.ListItems;
import zoomkart.paykart.models.ZoomKart;
import zoomkart.paykart.services.MyHostApduService;

public class NFCPairActivity extends Activity {

    NfcAdapter mNfcAdapter;
    SharedPreferences mSharedPreferences;
    String mOrderString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcpair);
        Context context = getApplicationContext();
        Paper.init(this);

        mSharedPreferences= context.getSharedPreferences("NFC", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("isNFCOn", true);
        editor.commit();

        Pusher pusher = new Pusher("d6f65af47015b83ec19b");

        final Channel channel = pusher.subscribe("channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.d("[NFCPairActivity] Order", "Tap Complete");
                Log.d("[NFCPairActivity] Order", "Initiate Order Channel and show user UI to place items.");
                Customer customer = ZoomKart.getCustomer();
                JSONObject json, obj;
                String str_value = "";
                try {

                    json = new JSONObject(data);

                    str_value = json.getString("data");

                } catch (Exception e){
                    e.printStackTrace();
                }

                final String orderId = str_value;
                final String bookName = customer.getId();

                //Reset validRead
                editor.putBoolean("validRead", false);
                editor.commit();

                channel.bind(orderId, new SubscriptionEventListener() {
                    @Override
                    public void onEvent(String s, String s1, String s2) {
                        Item[] items = (new Gson()).fromJson(s2, Item[].class);
                        Paper.book(bookName).write(data, items);
                        for (int i = 0; i < items.length; i++){
                            Log.d("[NFCPairActivity] Item:", items[i].name);
                        }
                    }
                });
            }
        });

        pusher.connect();


        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }
}
