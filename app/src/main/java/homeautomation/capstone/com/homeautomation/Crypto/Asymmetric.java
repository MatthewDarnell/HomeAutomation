package homeautomation.capstone.com.homeautomation.Crypto;

import android.content.Context;
import android.util.Log;

import homeautomation.capstone.com.homeautomation.Crypto.TweetNaclFast.Box;
import homeautomation.capstone.com.homeautomation.Settings;

import java.security.SecureRandom;

/**
 * Created by Matthew on 3/7/2016.
*
*   To Use: Get Server Public Key, Pass to Constructor Asymmetric()
*   Encrypt() with the plaintext message.
*
*   Will generate [nonce, encrypted message, signed message, hash(encrypted message + signed message)]
*
*   The signature should be verified as trusted on the server side before decrypting. If the signature is
*   not trusted, require the master password to establish trust.
* */
public class Asymmetric {
    private TweetNaclFast.Box.KeyPair keypair; //Public Key Encryption to the Server
    private TweetNaclFast.Signature.KeyPair signature; //Our Signature Used To Verify Requests
                                                       // After Initial Password Verification
    private TweetNaclFast.Signature sig;

    private byte[] pk;

    public Asymmetric(byte[] pk, byte[] MyPrivKey, byte[] MyPrivSig){
        this.keypair = TweetNaclFast.Box.keyPair_fromSecretKey(MyPrivKey);
        this.signature = TweetNaclFast.Signature.keyPair_fromSecretKey(MyPrivSig);
        sig = null;
        this.pk = pk;
        sig = new TweetNaclFast.Signature(this.pk, signature.getSecretKey());
    }

    public String Encrypt(byte[] message, Context c){
        byte[] n;
        SecureRandom securerandom = new SecureRandom();
        n = securerandom.generateSeed(24); //Max 8 bytes for long :(
        long nonce = 0;
        for(int i=0; i < n.length; i++){
            nonce += ((long) n[i] & 0xffL) << (8 * i);
        }
        Box box = new Box(this.pk, this.keypair.getSecretKey(), nonce);
        byte[] encryptedMessage = box.box(message);
        byte[] signedMessage = sig.sign(message);

        byte[] preimage = new byte[encryptedMessage.length + signedMessage.length];

        System.arraycopy(encryptedMessage, 0, preimage, 0, encryptedMessage.length);
        System.arraycopy(signedMessage, 0, preimage, encryptedMessage.length, signedMessage.length);

        byte[] hash = TweetNaclFast.Hash.sha512(preimage);

        //JSON object
        String retVal = "{" +
                        "\"nonce\":\"" + Settings.getInstance(c).bytesToHex(n) + "\"," +
                        "\"hash\":\"" + Settings.getInstance(c).bytesToHex(hash) + "\"," +
                        "\"publicKey\":\"" + Settings.getInstance(c).bytesToHex(keypair.getPublicKey()) + "\"," +
                        "\"encryptedMessage\":\"" + Settings.getInstance(c).bytesToHex(encryptedMessage) + "\"," +
                        "\"signature\":\"" +  Settings.getInstance(c).bytesToHex(signedMessage) + "\"" +
                        "}";

        String hex= Settings.getInstance(c).toHexString(
                Settings.getInstance(c).StrToByteArray(retVal)
                );
        Log.d("PUBKEY", hex);
        return hex;
    }

}
