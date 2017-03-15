package zoomkart.paykart.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import java.text.NumberFormat;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Order;
import zoomkart.paykart.models.ZoomKart;

public class PaymentActivity extends AppCompatActivity {

    // You will need to use your live API key even while testing
    public static final String PUBLISHABLE_KEY = " pk_live_E6uv3wI3bXTQNNZaU3Dbhgg1" +
            "";
    public static final String TAG = "PaymentActivity";

    // Unique identifiers for asynchronous requests:
    private static final int LOAD_MASKED_WALLET_REQUEST_CODE = 1000;
    private static final int LOAD_FULL_WALLET_REQUEST_CODE = 1001;

    private GoogleApiClient googleApiClient;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_payment);
        Paper.init(this);
        getSupportActionBar().hide();

        LinearLayout initiatePayButton = (LinearLayout) findViewById(R.id.initiate_pay_button);
        String customerId = ZoomKart.getCustomer().getId();

        final Order order = Paper.book(customerId).read("CurrentOrder");
        double subTotal, taxTotal, finalTotal;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        TextView subTotalView = (TextView) findViewById(R.id.subtotal_price);
        TextView taxTotalView = (TextView) findViewById(R.id.tax_price);
        TextView totalView = (TextView) findViewById(R.id.total_price);

        subTotal = order.getmTotalAmount();
        taxTotal = subTotal * 0.13;
        finalTotal = subTotal + taxTotal;

        subTotalView.setText(formatter.format(subTotal));
        taxTotalView.setText(formatter.format(taxTotal));
        totalView.setText(formatter.format(finalTotal));

        initiatePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(PaymentActivity.this);
                String url ="http://ZoomKart.pythonanywhere.com/payments?user_id=1&order_id=" + order.getmId() + "&token=1";
                final ProgressDialog progress = new ProgressDialog(PaymentActivity.this);
                progress.setTitle("Loading");
                progress.setMessage("Completing Payment ...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.d("Status", "Success!");
                                progress.setTitle("Payment Complete!");
                                progress.setMessage("Thanks for using ZoomKart!");
                                Runnable progressRunnable = new Runnable() {

                                    @Override
                                    public void run() {
                                        progress.dismiss();
                                        cleanUpActivity();
                                    }
                                };

                                Handler pdCanceller = new Handler();
                                pdCanceller.postDelayed(progressRunnable, 2000);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Status", "Failure!");
                                progress.dismiss();
                            }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }

    private void cleanUpActivity(){
        Intent i = new Intent(this, HomepageActivity.class);
        startActivity(i);
        finish();
    }

}
