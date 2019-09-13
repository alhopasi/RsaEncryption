package rsaencryption.domain;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyGeneratorTest {

    private KeyGenerator keyGen;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Before
    public void setUp() {
        keyGen = new KeyGenerator();
        Pair<PublicKey, PrivateKey> keys = keyGen.generateKeys(2048);
        publicKey = keys.getKey();
        privateKey = keys.getValue();
    }

    @Test
    public void keyGeneratorIsCreated() {
        assertNotNull(keyGen);
    }

    @Test
    public void keyGeneratorProducesPublicAndPrivateKeys() {
        assertNotNull(privateKey);
        assertNotNull(publicKey);
    }
    
    @Test
    public void encryptAndDecryptWorks() {
        String message = "kissa on kiva";
        String cipher = publicKey.encrypt(message);
        String plaintext = privateKey.decrypt(cipher);
        assertEquals(message, plaintext);
    }
}
