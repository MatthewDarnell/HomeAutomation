package homeautomation.capstone.com.homeautomation.Devices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import homeautomation.capstone.com.homeautomation.DoorPickerActivity;
import homeautomation.capstone.com.homeautomation.Http;
import homeautomation.capstone.com.homeautomation.R;
import homeautomation.capstone.com.homeautomation.Settings;


public class Door extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int doorID = 0;
        if (getIntent().hasExtra("DoorID")) {
            doorID = getIntent().getIntExtra("DoorID", 0);
        } else {
            throw new IllegalArgumentException("Door Toggle Activity cannot find bundled [DoorID]");
        }

        String url = Settings.getInstance().getURL();

 /*"You chose to toggle door " + doorID + ". IP = " + Settings.getInstance().getURL()*/
       // Toast.makeText(this, Settings.getInstance().Encrypt("Hello, World!"), Toast.LENGTH_SHORT).show();
        //Get Door Status
        //Toast.makeText(this, new Http().get(url + "toggle_door?door_number=" + doorID, ""), Toast.LENGTH_LONG).show();
        Toast.makeText(this,  "Toggling "+doorID, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DoorPickerActivity.class);
        startActivity(intent);
        finish();
    }

}
