package zoomkart.paykart.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import zoomkart.paykart.R;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().hide();

        LinearLayout initiateCheckoutButton = (LinearLayout) findViewById(R.id.initiate_checkout_button);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.user_profile_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, CardInformationActivity.class);
                HomepageActivity.this.startActivity(intent);
            }
        });

        initiateCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, NFCPairActivity.class);
                HomepageActivity.this.startActivity(intent);
            }
        });

        LinearLayout initiateHistoryButton = (LinearLayout) findViewById(R.id.initiate_history_button);

        initiateHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, HistoryActivity.class);
                HomepageActivity.this.startActivity(intent);
            }
        });
    }

}
