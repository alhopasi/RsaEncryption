package rsaencryption.domain;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class PrivateKeyTest {
    
    private PrivateKey privateKey;
    
    @Before
    public void setUp() {
        privateKey = new PrivateKey(new BigInteger("3233"), new BigInteger("2753"));
    }

    @Test
    public void keyGeneratorIsCreated() {
        assertNotNull(privateKey);
    }

    @Test
    public void decryptWorks() {
        String cipher = "660";
        String plaintext = privateKey.decrypt(cipher);
        assertEquals("a", plaintext);
    }
}
