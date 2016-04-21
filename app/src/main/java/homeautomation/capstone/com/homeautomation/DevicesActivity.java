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
import android.widget.Toast;

public class DevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
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

        Button doorButton = (Button) findViewById(R.id.doors_button),
               lightButton = (Button) findViewById(R.id.lights_button),
               cameraButton = (Button) findViewById(R.id.cameras_button),
                tempButton =  (Button) findViewById(R.id.temperature_button);

        final Context context = this;

        doorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoorPickerActivity.class);
                //Toast.makeText(context, "Door Button Clicked", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LightPickerActivity.class);
                //Toast.makeText(context, "Light Button Clicked", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CameraPickerActivity.class);
                //Toast.makeText(context, "Camera Button Clicked", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        tempButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, TemperatureActivity.class);
                startActivity(intent);
            }
        });



    }

}
