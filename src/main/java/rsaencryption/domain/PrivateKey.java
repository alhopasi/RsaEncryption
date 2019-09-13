package rsaencryption.domain;

import rsaencryption.utils.Utils;
import java.math.BigInteger;

/**
 * Private key for RSA encryption.
 */
public class PrivateKey {

    private final BigInteger n;
    private final BigInteger d;

    /**
     * Creates a new private key for RSA encryption.
     * Private key can be used to decrypt messages that are encrypted with the public key.
     * Keep this only to yourself!
     * @param n the modulus for public and private key.
     * @param d the secret exponent for private key.
     */
    public PrivateKey(BigInteger n, BigInteger d) {
        this.n = n;
        this.d = d;
    }
    
    /**
     * Decrypt a hexed message into plaintext.
     * @param hexed the encrypted message in hex format.
     * @return plaintext message.
     */
    public String decrypt(String hexed) {
        BigInteger encrypted = Utils.hexToDecimal(hexed);
        
        //System.out.println("encrypted bit size: " + encrypted.bitLength());
        //System.out.println("encrypted: " + encrypted);
        
        BigInteger decrypted = encrypted.modPow(d, n);
        
        //System.out.println("decrypted bit size: " + decrypted.bitLength());
        //System.out.println("decrypted: " + decrypted);
        String message = Utils.decimalToString(decrypted);
        return message;
    }

}
