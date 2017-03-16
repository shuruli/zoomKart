package zoomkart.paykart.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import io.paperdb.Paper;
import zoomkart.paykart.R;
import zoomkart.paykart.models.Customer;
import zoomkart.paykart.models.Order;
import zoomkart.paykart.models.ZoomKart;

import static java.security.AccessController.getContext;

public class CardInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_information);
        Paper.init(this);

        final Customer customer = ZoomKart.getCustomer();
        Token token = Paper.book(customer.getId()).read("payment_token", null);
        final Order order = Paper.book(customer.getId()).read("CurrentOrder", null);

        if (token != null){
            TextView textView = (TextView) findViewById(R.id.card_details_label);
            textView.setText("Modify Card Details");
        }
        Spinner spinner = (Spinner) findViewById(R.id.province_value);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provinces_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        final CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        LinearLayout submitPaymentInformationButton = (LinearLayout) findViewById(R.id.complete_payment_form);

        submitPaymentInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card cardToSave = mCardInputWidget.getCard();
                EditText nameValue = (EditText) findViewById(R.id.name_value);
                EditText addressValue = (EditText) findViewById(R.id.address_value);
                EditText postalCodeValue = (EditText) findViewById(R.id.postal_code_value);
                Spinner provinceValue = (Spinner) findViewById(R.id.province_value);
                final Context context = CardInformationActivity.this;

                final ProgressDialog progress = new ProgressDialog(context);
                progress.setTitle("Validating");
                progress.setMessage("Saving Information...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                cardToSave.setName(nameValue.getText().toString());
                cardToSave.setAddressLine1(addressValue.getText().toString());
                cardToSave.setAddressZip(postalCodeValue.getText().toString());
                cardToSave.setAddressState(provinceValue.getSelectedItem().toString());
                cardToSave.setAddressCountry("Canada");

                Stripe stripe = null;
                try {
                    stripe = new Stripe(context, "pk_test_Jkep3xbvvLLdsbKPDNvRG35E");
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                }
                stripe.createToken(
                        cardToSave,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                // Send token to your server
                                Log.d("CardInformationActivity", "Stripe validation succeded");
                                Paper.book(customer.getId()).write("payment_token", token);

                                if (order == null){
                                    Intent intent = new Intent(CardInformationActivity.this, HomepageActivity.class);
                                    CardInformationActivity.this.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(CardInformationActivity.this, BillActivity.class);
                                    CardInformationActivity.this.startActivity(intent);
                                }
                                finish();
                                progress.dismiss();
                            }
                            public void onError(Exception error) {
                                // Show localized error message
                                Log.d("CardInformationActivity", "Stripe validation failed");
                                progress.dismiss();
                            }
                        }
                );

            }
        });
    }
}
