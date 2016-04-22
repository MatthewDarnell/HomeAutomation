package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.content.SharedPreferences;

import homeautomation.capstone.com.homeautomation.Crypto.Asymmetric;
import homeautomation.capstone.com.homeautomation.Crypto.TweetNaclFast;

/**
 * Created by Matthew on 4/9/2016.
 */
public class Settings {

    public byte[] StrToByteArray(String s)
    {
        byte[] byteArray = null;
        if(s!=null)
        {
            if(s.length()>0)
            {
                try
                {
                    byteArray = s.getBytes();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return byteArray;
    }

    public String ByteArrayToStr(byte[] byteArray)
    {
        String s = null;
        if(byteArray!=null)
        {
            if(byteArray.length>0)
            {
                try
                {
                    s = new String(byteArray,"UTF-8");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }

    public void SyncToServer(final Context c){
        Http http = new Http();
        String pubkey = http.get(this.getURL() + "getPubKey", "", c);
        if(pubkey.length() > 0){
            this.ServerPubKey = StrToByteArray(pubkey);
        }
    }

    private static Settings mInstance= null;


    private byte[] ServerPubKey = null;
    private String MyPubKey = null;
    private String MyPrivKey = null;

    private Asymmetric crypto = new Asymmetric(this.ServerPubKey);

    public void SetKeyPair(String pk, String sk){
        this.MyPubKey = pk;
        this.MyPrivKey = sk;
    }
    public String GetPubKey(){
        return MyPubKey;
    }

    private String URL = "https://192.168.1.84:5000/";

    public void setURL(String val, final Context c){
        URL = val;
        this.SyncToServer(c);
    }
    public String getURL(){
        return URL;
    }

    public String Encrypt(String m){
        return crypto.Encrypt(StrToByteArray(m));
    }

    protected Settings(final Context c){
        this.SyncToServer(c);
    }

    public static synchronized Settings getInstance(final Context c){
        if(null == mInstance){
            mInstance = new Settings(c);
        }
        return mInstance;
    }

}
