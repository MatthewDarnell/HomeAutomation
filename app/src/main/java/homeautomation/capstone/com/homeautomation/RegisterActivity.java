package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        final EditText password = (EditText) findViewById(R.id.password);
        Button registerButton = (Button) findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                String pubkey = Settings.getInstance(context).GetPubKey();
                String json = "{\"Password\":\"" + pass + "\", \"PubKey\": \"" + pubkey + "\"}";
                Http http = new Http();
                //Transmit Password + Public Key to Server
                String res = http.post("register", json);
                if(res.equals("success")){
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
