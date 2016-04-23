package homeautomation.capstone.com.homeautomation;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import homeautomation.capstone.com.homeautomation.Crypto.Asymmetric;
import homeautomation.capstone.com.homeautomation.Crypto.TweetNaclFast;

/**
 * Created by Matthew on 4/9/2016.
 */
public class Settings {

    private int isSynced= 0;
    final protected char[] hexArray = "0123456789ABCDEF".toCharArray();
    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


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


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
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

    public String toHexString(byte[] ba) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < ba.length; i++)
            str.append(String.format("%02x", ba[i]));
        return str.toString();
    }

    public void SyncToServer(final Context c){
        if(isSynced == 1) return;
        Http http = new Http();
        String pubkey = http.get(this.getURL() + "getPubKey", "", c);
        if(pubkey.length() > 5){
            String key = pubkey.substring(2, pubkey.length()-3);
            Log.d("PUBKEY", key);
            this.ServerPubKey = StrToByteArray(key);
            isSynced = 1;
        }
    }

    private static Settings mInstance= null;


    private byte[] ServerPubKey = null;
    private byte[] MyPubKey = null;
    private byte[] MyPrivKey = null;


    private byte[] MyPubSig = null;
    private byte[] MyPrivSig = null;

    private Asymmetric crypto = null;

    public void SetKeyPair(String pk, String sk){
        this.MyPubKey = hexStringToByteArray(pk);
        this.MyPrivKey = hexStringToByteArray(sk);
    }

    public void SetSigPair(String pk, String sk){
        this.MyPubSig = hexStringToByteArray(pk);
        this.MyPrivSig = hexStringToByteArray(sk);

    }
    public String GetPubKey(){
        return bytesToHex(MyPubKey);
    }

    private String URL = "https://192.168.1.84:5000/";

    public void setURL(String val, final Context c){
        URL = val;
        this.SyncToServer(c);
    }
    public String getURL(){
        return URL;
    }

    public String Encrypt(String m, Context c){
        if(crypto==null){
            crypto = new Asymmetric(this.ServerPubKey, MyPrivKey, MyPrivSig);
        }
        return crypto.Encrypt(StrToByteArray(m), c);
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
