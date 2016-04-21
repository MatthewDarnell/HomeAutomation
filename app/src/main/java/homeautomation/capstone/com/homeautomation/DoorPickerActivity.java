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
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;
import java.util.Map;

public class DoorPickerActivity extends AppCompatActivity {

    private LinearLayout layout;
    private LinearLayout.LayoutParams params;

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

        layout = (LinearLayout) findViewById(R.id.door_picker_linear_layout);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.topMargin = 94;

        Http http = new Http();
        Map<Integer, String> doorMap = new HashMap<Integer, String>();

        //String doorStatus = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body><doors><door id=\"1\" status=\"Closed\"><door id=\"2\" status=\"Closed\"><door id=\"3\" status=\"Closed\"><door id=\"4\" status=\"Open\"></door></door></door></door></doors></body></html>";

        Document doc = http.jsoupGet( Settings.getInstance().getURL() + "get_door_status");

        //Document doc = Jsoup.parse(doorStatus);
        Elements title = doc.select("door");
        for (Element door : title) {
            final String id = door.attr("id");
            final String status = door.attr("status");
            doorMap.put(Integer.parseInt(id), status);

            Button tempButton = new Button(context);
            tempButton.setLayoutParams(params);
            tempButton.setText("Door " + id + " - " + status);
            layout.addView(tempButton);

            tempButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ToggleDoor(context, Integer.parseInt(id));
                }
            });

        }
    }

    private void ToggleDoor(Context c, int whichDoor) {
        Intent doorToggle = new Intent(c, homeautomation.capstone.com.homeautomation.Devices.Door.class);
        doorToggle.putExtra("DoorID", whichDoor);
        startActivity(doorToggle);
        finish();
    }


}
