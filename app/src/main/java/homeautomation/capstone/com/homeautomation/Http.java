package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Matthew on 4/9/2016.
 */
public class Http {

    private String s = "";
    public String get(final String url, final String bodyRequest){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, bodyRequest);
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    s = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e){
        }
        return s;
    }

    public String post(final String url, final String bodyRequest){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(JSON, bodyRequest);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    s = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    s = "Error";
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e){
        }
        return s;
    }

}
