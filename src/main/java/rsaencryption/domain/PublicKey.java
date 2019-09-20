package rsaencryption.domain;

import rsaencryption.utils.Utils;
import java.math.BigInteger;
import java.util.Objects;

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
        BigInteger encrypted = decrypted.modPow(e, n);
        String hexed = Utils.decimalToHex(encrypted);
        
        return hexed;
    }
    
    @Override
    public String toString() {
        return Utils.decimalToHex(n) + "\n" + Utils.decimalToHex(e);
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
        final PublicKey other = (PublicKey) obj;
        if (!Objects.equals(this.e, other.e)) {
            return false;
        }
        if (!Objects.equals(this.n, other.n)) {
            return false;
        }
        return true;
    }
}
