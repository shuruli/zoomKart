package zoomkart.paykart.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.Order;
import zoomkart.paykart.models.ZoomKart;

public class HistoryActivity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setTitle("Past Orders");

        listView = (ListView) findViewById(R.id.items_list);


        // Code here to retrieve order from server and items associated
        Paper.init(this);
        final Customer customer = ZoomKart.getCustomer();

        List<String> keys = Paper.book(customer.getId()).getAllKeys();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, keys);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = ((TextView)view).getText().toString();
                Order order = Paper.book(customer.getId()).read(key);
                String[] orderItems = new String[order.getmItems().length];

                for (int i = 0; i < orderItems.length; i ++){
                    orderItems[i] = order.getmItems()[i].getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, orderItems);

                listView.setAdapter(adapter);
            }
        });

    }
}
