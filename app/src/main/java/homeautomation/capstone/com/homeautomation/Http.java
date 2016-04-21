package homeautomation.capstone.com.homeautomation;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
    private Document doc = null;

    public String get(final String url, final String bodyRequest){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.setProperty("javax.net.ssl.trustStore", "HubServer.jks");
          //          System.setProperty("javax.net.ssl.keyStore", "HubServer.jks");

                    System.out.println("CURRENT TRUSTSTORE: " + System.getProperty("javax.net.ssl.trustStore"));


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


    public Document jsoupGet(final String url){
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    doc = Jsoup.connect(url).get();
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
        return doc;
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
