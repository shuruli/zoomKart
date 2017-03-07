package zoomkart.paykart.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import zoomkart.paykart.R;

public class BillActivity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        setTitle("Your Items");

        listView = (ListView) findViewById(R.id.items_list);


        // Code here to retrieve order from server and items associated

        // Mock Data for now
        String[] values = new String[] { "Stoli",
                "Mango Rubicon",
                "Bourne Vita",
                "Sona Masoori",
                "Loewenbrau",
                "Chai",
                "Egg Curry",
                "Sunflower Seeds",
                "All Dressed Ruffles",
                "Lysol Wipes",
                "TP",
                "Phylo Pastry",
                "Eggs",
                "Misc Muk",
                "Swiffer Pads"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        FloatingActionButton paymentButton = (FloatingActionButton) findViewById(R.id.payment_button);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent paymentIntent = new Intent(BillActivity.this, PaymentActivity.class);

                // currentContext.startActivity(activityChangeIntent);

                BillActivity.this.startActivity(paymentIntent);
            }
        });
    }
}
