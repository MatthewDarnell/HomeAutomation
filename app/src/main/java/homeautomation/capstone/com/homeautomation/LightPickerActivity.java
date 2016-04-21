package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LightPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_picker);
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
        LinearLayout layout;
        LinearLayout.LayoutParams params;
        layout = (LinearLayout) findViewById(R.id.light_picker_layout);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.topMargin = 94;

        Http http = new Http();
        //String lightStatus = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"></head><body>Light1: out (of the scope of this project)</body></html>";
        Document doc = http.jsoupGet(Settings.getInstance().getURL() + "get_light_status");

        //Document doc = Jsoup.parse(lightStatus);
        Elements body = doc.select("body");
        final String status = body.text();

        TextView tv = new TextView(context);
        tv.setLayoutParams(params);
        tv.setText(status);
        layout.addView(tv);
    }

}
