package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import homeautomation.capstone.com.homeautomation.Crypto.TweetNaclFast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //Sync Server Pub Key
        Settings.getInstance().SyncToServer();

        //Get My KeyPair
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.keypair), Context.MODE_PRIVATE);
        String MyPrivKey = sharedPref.getString(getString(R.string.privkey), "");
        String MyPubKey = sharedPref.getString(getString(R.string.pubkey), "");

        if(MyPrivKey.equals("") || MyPubKey.equals("")){
            //Generate a new keypair
            TweetNaclFast.Box.KeyPair kp = TweetNaclFast.Box.keyPair();
            final byte[] pk = kp.getPublicKey(),
                         sk = kp.getSecretKey();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.privkey), Settings.getInstance().ByteArrayToStr(sk));
            editor.putString(getString(R.string.pubkey), Settings.getInstance().ByteArrayToStr(pk));
            editor.commit();
        }
        else{
            Toast.makeText(this, MyPubKey, Toast.LENGTH_LONG).show();
        }

        Settings.getInstance().SetKeyPair(MyPubKey, MyPrivKey);


        //Buttons
        Button
        settingsButton = (Button) findViewById(R.id.settings_button),
        devicesButton = (Button) findViewById(R.id.devices_button),
        registerButton = (Button) findViewById(R.id.register_device_button),
        exitButton = (Button) findViewById(R.id.exit_button);

        //Actions
        if(settingsButton == null || devicesButton == null || registerButton == null || exitButton == null){
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(-1);
        }

        //Settings
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(context, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        //Devices
        devicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devicesIntent = new Intent(context, DevicesActivity.class);
                startActivity(devicesIntent);
            }
        });

        //Register
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(context, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        //Exit
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
