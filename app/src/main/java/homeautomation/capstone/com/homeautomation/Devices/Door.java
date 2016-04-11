package homeautomation.capstone.com.homeautomation.Devices;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import homeautomation.capstone.com.homeautomation.Http;
import homeautomation.capstone.com.homeautomation.Interface.WebService;
import homeautomation.capstone.com.homeautomation.R;
import homeautomation.capstone.com.homeautomation.Settings;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
 /*"You chose to toggle door " + doorID + ". IP = " + Settings.getInstance().getURL()*/
        Toast.makeText(this, Settings.getInstance().Encrypt("Hello, World!"), Toast.LENGTH_SHORT).show();
        //Get Door Status
        //Toast.makeText(this, new Http().get("https://192.168.1.84:8080/toggle_door?door_number=" + doorID, ""), Toast.LENGTH_LONG).show();



    }

}
