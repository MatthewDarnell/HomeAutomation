package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class DoorPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_picker);
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

        final Context context = this;

        Button door1 = (Button) findViewById(R.id.door1),
                door2 = (Button) findViewById(R.id.door2),
                door3 = (Button) findViewById(R.id.door3);

        door1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleDoor(context, 0);
            }
        });

        door2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleDoor(context, 1);
            }
        });


        door3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToggleDoor(context, 2);
            }
        });


    }

    private void ToggleDoor(Context c, int whichDoor) {
        Intent doorToggle = new Intent(c, homeautomation.capstone.com.homeautomation.Devices.Door.class);
        doorToggle.putExtra("DoorID", whichDoor);
        startActivity(doorToggle);
    }

}
