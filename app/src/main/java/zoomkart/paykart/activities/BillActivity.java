package zoomkart.paykart.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.ListViewAdapter;
import zoomkart.paykart.models.Order;
import zoomkart.paykart.models.ZoomKart;

import static zoomkart.paykart.models.Constants.FIRST_COLUMN;
import static zoomkart.paykart.models.Constants.SECOND_COLUMN;

public class BillActivity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bill);
        getSupportActionBar().hide();

        Paper.init(this);

        listView = (ListView) findViewById(R.id.items_list);
        Customer customer = ZoomKart.getCustomer();
        final Order order = Paper.book(customer.getId()).read("CurrentOrder");


        ListView listView=(ListView)findViewById(R.id.bills_items_list);
        ArrayList<HashMap<String,String>> list =new ArrayList<HashMap<String,String>>();

        String[] names = new String[order.getmItems().length];
        float subTotal, taxTotal, finalTotal;
        subTotal = 0f;
        taxTotal = 0f;
        finalTotal = 0f;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        for (int i = 0; i < names.length; i++){
            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(FIRST_COLUMN, order.getmItems()[i].getName());
            temp.put(SECOND_COLUMN, formatter.format(order.getmItems()[i].getPrice()));
            subTotal += order.getmItems()[i].getPrice();
            list.add(temp);
        }

        ListViewAdapter adapter=new ListViewAdapter(this, list);
        listView.setAdapter(adapter);

        TextView textView = new TextView(this);
        textView.setText("Items in Basket");
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 14f);
        textView.setGravity(Gravity.CENTER);
        listView.addHeaderView(textView);

        LinearLayout initiateCheckoutButton = (LinearLayout) findViewById(R.id.initiate_payment_button);

        initiateCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, PaymentActivity.class);
                BillActivity.this.startActivity(intent);
            }
        });
    }
}
