package com.example.contactstatusapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        String action = intent.getAction();

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(intent.getStringExtra(TelephonyManager.EXTRA_STATE))) {
            // Get the status message from SharedPreferences
            SharedPreferences preferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
            String statusMessage = preferences.getString("statusMessage", "");

            if (!statusMessage.isEmpty()) {
                // Display a toast/notification with the status message
                Toast.makeText(context, "Status: " + statusMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
}
