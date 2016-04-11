package homeautomation.capstone.com.homeautomation.Crypto;

import homeautomation.capstone.com.homeautomation.Crypto.TweetNaclFast.Box;
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

    public Asymmetric(byte[] pk){
        this.keypair = TweetNaclFast.Box.keyPair();
        this.signature = TweetNaclFast.Signature.keyPair();
        sig = null;
        this.pk = pk;
        sig = new TweetNaclFast.Signature(this.pk, signature.getSecretKey());
    }

    public String Encrypt(byte[] message){
        byte[] n;
        SecureRandom securerandom = new SecureRandom();
        n = securerandom.generateSeed(8); //Max 8 bytes for long :(
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
                        "\"nonce\":" + nonce + "," +
                        "\"hash\":" + hash.toString() + "\"," +
                        "\"encryptedMessage\":" + encryptedMessage.toString() + "\"," +
                        "\"signature\":" + signedMessage.toString() + "\"" +
                        "}";

        return retVal;
    }

}
