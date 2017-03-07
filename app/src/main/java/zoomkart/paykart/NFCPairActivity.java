package zoomkart.paykart;

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
import android.widget.TextView;
import android.widget.Toast;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import java.nio.charset.Charset;

import zoomkart.paykart.services.MyHostApduService;

public class NFCPairActivity extends Activity {

    NfcAdapter mNfcAdapter;
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcpair);
        Context context = getApplicationContext();

        mSharedPreferences= context.getSharedPreferences("NFC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("validRead", true);
        editor.putBoolean("readComplete", false);
        editor.commit();


        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

    }

    private void subscribeToPusher(){
        Pusher pusher = new Pusher("d6f65af47015b83ec19b");

        Channel channel = pusher.subscribe("channel");

        channel.bind("my-event", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                Log.d("[NFCPairActivity] Event", "Channel Name: " + channelName + " data: " + data);
            }
        });

        pusher.connect();
    }

    private class NFCTapTask extends AsyncTask<String, Void, Integer> {
        private Activity mActivity;
        private SharedPreferences mTapTaskSharedPreferences;

        public NFCTapTask (Activity activity){
            mActivity = activity;
        }

        protected Integer doInBackground(String ... s) {
            mTapTaskSharedPreferences = mActivity.getSharedPreferences("NFC", Context.MODE_PRIVATE);
            while (mTapTaskSharedPreferences.getBoolean("readComplete", false) == false)
            {

            }
            return 0;
        }

        protected void onPostExecute(Integer result) {
            Log.d("Result is: ", result.toString());
        }
    }


}
