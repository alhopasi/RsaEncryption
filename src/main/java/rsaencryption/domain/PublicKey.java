package rsaencryption.domain;

import rsaencryption.utils.Utils;
import java.math.BigInteger;

/**
 * The public key for RSA encryption.
 */
public class PublicKey {

    private final BigInteger e;
    private final BigInteger n;

    /**
     * Creates new public key for RSA encryption.
     * Public key can be used to encrypt messages and sign them.
     * @param n the modulus for public and private key.
     * @param e the exponent used in encrypting the messages.
     */
    public PublicKey(BigInteger n, BigInteger e) {
        this.e = e;
        this.n = n;
    }

    /**
     * Encrypt a plaintext message into encrypted hex.
     * @param message plaintext message which will be encrypted.
     * @return hex String that is encrypted.
     */
    public String encrypt(String message) {
        //message = Padding.oaep(message);
        
        BigInteger decrypted = Utils.stringToDecimal(message);
        
        //System.out.println("decrypted: " + decrypted);
        BigInteger encrypted = decrypted.modPow(e, n);

        //System.out.println("encrypted: " + encrypted);

        String hexed = Utils.decimalToHex(encrypted);
        return hexed;
    }
    
}
