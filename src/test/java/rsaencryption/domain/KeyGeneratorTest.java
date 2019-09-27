package rsaencryption.domain;

import datastructures.MyPair;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class KeyGeneratorTest {

    private static KeyGenerator keyGen;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    
    @Before
    public void setUp() {
        keyGen = new KeyGenerator();
        MyPair<PublicKey, PrivateKey> keys = keyGen.generateKeys(2048);
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
