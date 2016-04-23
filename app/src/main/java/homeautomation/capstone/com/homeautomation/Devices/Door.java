package homeautomation.capstone.com.homeautomation.Devices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import homeautomation.capstone.com.homeautomation.DevicesActivity;
import homeautomation.capstone.com.homeautomation.DoorPickerActivity;
import homeautomation.capstone.com.homeautomation.Http;
import homeautomation.capstone.com.homeautomation.MainActivity;
import homeautomation.capstone.com.homeautomation.R;
import homeautomation.capstone.com.homeautomation.Settings;


public class Door extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        int doorID = 0;
        if (getIntent().hasExtra("DoorID")) {
            doorID = getIntent().getIntExtra("DoorID", 0);
        } else {
            throw new IllegalArgumentException("Door Toggle Activity cannot find bundled [DoorID]");
        }

        String url = Settings.getInstance(this).getURL();
        Http http = new Http();
        Settings settings = Settings.getInstance(this);

        String encrypted = settings.Encrypt(url + "toggle_door$" + doorID, this);
        Log.d("PUBKEY", "Encrypted: " + encrypted);
        http.get(url + "req$" + encrypted, "", this);

        //http.get(url + "toggle_door$" + doorID, "", this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
