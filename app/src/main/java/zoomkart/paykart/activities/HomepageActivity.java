package zoomkart.paykart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import zoomkart.paykart.R;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homepage);

        getSupportActionBar().hide();

        LinearLayout mInitiateCheckoutButton = (LinearLayout) findViewById(R.id.initiate_checkout_button);

        mInitiateCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, NFCPairActivity.class);
                HomepageActivity.this.startActivity(intent);
            }
        });
    }

    private void initiateCheckout(){
        Intent intent = new Intent(HomepageActivity.this, NFCPairActivity.class);
        HomepageActivity.this.startActivity(intent);
    }
}
