package zoomkart.paykart.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.ZoomKart;

public class HistoryActivity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setTitle("Your Items");

        listView = (ListView) findViewById(R.id.items_list);


        // Code here to retrieve order from server and items associated
        Paper.init(this);
        Customer customer = ZoomKart.getCustomer();

        List<String> keys = Paper.book(customer.getId()).getAllKeys();
        String[] values = (String[]) keys.toArray();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        FloatingActionButton paymentButton = (FloatingActionButton) findViewById(R.id.payment_button);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent paymentIntent = new Intent(HistoryActivity.this, PaymentActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                HistoryActivity.this.startActivity(paymentIntent);
            }
        });
    }
}
