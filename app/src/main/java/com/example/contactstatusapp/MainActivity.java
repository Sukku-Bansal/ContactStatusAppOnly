package com.example.contactstatusapp;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;

    String statusMessage;
    Button setStatusButton;
    ToggleButton toggleButton;
    EditText statusEditText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////


//         Add this in onCreate() method
        if (ContextCompat.checkSelfPermission(this, READ_PHONE_STATE)
                != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{ READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }


        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        PhoneStateReceiver receiver = new PhoneStateReceiver();
        registerReceiver(receiver, intentFilter);

//<=================================================================================================>
        // Save status message
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Retrieve status message
        String statusMessage = preferences.getString("statusMessage", "");

        // Retrieve active/deactivate state
        boolean isActive = preferences.getBoolean("isActive", false);
        editor.putString("statusMessage", statusMessage);
        editor.putBoolean("isActive", isActive);
        editor.apply();
//<=================================================================================================>
        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isActive", isChecked);
                editor.apply();
            }
        });


        setStatusButton = findViewById(R.id.button);

        setStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText statusEditText = findViewById(R.id.status_message_edit_text);

                String statusMessage = statusEditText.getText().toString();

                // Save the status message
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("statusMessage", statusMessage);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Status Saved Success",Toast.LENGTH_SHORT).show();
            }
        });
    }
}