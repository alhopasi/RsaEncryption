package rsaencryption.domain;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class PublicKeyTest {
    
    
    private PublicKey publicKey;
    
    @Before
    public void setUp() {
        publicKey = new PublicKey(new BigInteger("3233"), new BigInteger("17"));
    }

    @Test
    public void keyGeneratorIsCreated() {
        assertNotNull(publicKey);
    }

    @Test
    public void encryptWorks() {
        String message = "a";
        String cipher = publicKey.encrypt(message);
        assertEquals("660", cipher);
    }
}
