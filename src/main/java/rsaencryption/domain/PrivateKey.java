package rsaencryption.domain;

import datastructures.MyBigInteger;
import rsaencryption.utils.Utils;
import java.util.Objects;

/**
 * Private key for RSA encryption.
 */
public class PrivateKey {

    private final MyBigInteger n;
    private final MyBigInteger d;

    /**
     * Creates a new private key for RSA encryption.
     * Private key can be used to decrypt messages that are encrypted with the public key.
     * Keep this only to yourself!
     * @param n the modulus for public and private key.
     * @param d the secret exponent for private key.
     */
    public PrivateKey(MyBigInteger n, MyBigInteger d) {
        this.n = n;
        this.d = d;
    }
    
    /**
     * Decrypt a hexed message into plaintext.
     * @param hexed the encrypted message in hex format.
     * @return plaintext message.
     */
    public String decrypt(String hexed) {
        MyBigInteger encrypted = Utils.hexToDecimal(hexed);
        
        MyBigInteger decrypted = encrypted.modPow(d, n);
        
        String message = Utils.decimalToString(decrypted);
        return message;
    }
    
    @Override
    public String toString() {
        return Utils.decimalToHex(n) + "\n" + Utils.decimalToHex(d);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PrivateKey other = (PrivateKey) obj;
        if (!Objects.equals(this.n, other.n)) {
            return false;
        }
        if (!Objects.equals(this.d, other.d)) {
            return false;
        }
        return true;
    }
}
