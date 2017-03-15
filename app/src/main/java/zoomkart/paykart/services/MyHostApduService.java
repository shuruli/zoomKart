package zoomkart.paykart.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.nfc.cardemulation.CardEmulation;

import java.nio.ByteBuffer;

public class MyHostApduService extends HostApduService {

    private int messageCounter = 0;
    private CardEmulation mCardEmulation;

    @Override
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        SharedPreferences sharedPref = this.getSharedPreferences("NFC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean isValidRead = sharedPref.getBoolean("isNFCOn", false);
        byte[] welcomeMessage = null;

        Log.d("MyHostApduService", "isNFCOn is : " + Boolean.toString(isValidRead));

        if (isValidRead && selectAidApdu(apdu)) {
            Log.i("HCEDEMO", "Application selected");
            Integer intValue = ByteBuffer.wrap(getWelcomeMessage()).getInt();
            Log.i("HCEDEMO INT SENT: ", intValue.toString());
            welcomeMessage = getWelcomeMessage();
        }

        return welcomeMessage;
    }

    private byte[] getWelcomeMessage() {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(100);
        return b.array();
    }
    private byte[] getNextMessage() {
        return ("Message from android: " + messageCounter++).getBytes();
    }

    private boolean selectAidApdu(byte[] apdu) {
        return apdu.length >= 2 && apdu[0] == (byte)0 && apdu[1] == (byte)0xa4;
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i("HCEDEMO", "Deactivated: " + reason);
    }
}