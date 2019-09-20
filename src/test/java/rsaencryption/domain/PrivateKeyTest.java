package rsaencryption.domain;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import rsaencryption.utils.Utils;

public class PrivateKeyTest {

    private PrivateKey privateKey;
    private BigInteger n;
    private BigInteger d;

    @Before
    public void setUp() {
        n = new BigInteger("3233");
        d = new BigInteger("2753");
        privateKey = new PrivateKey(n, d);
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

    @Test
    public void toStringIsCorrect() {
        assertEquals(Utils.decimalToHex(n) + "\n" + Utils.decimalToHex(d), privateKey.toString());
    }
    
    @Test
    public void equals() {
        PrivateKey key = new PrivateKey(n,d);
        PrivateKey key2 = new PrivateKey(d,n);
        assertTrue(key.equals(privateKey));
        assertTrue(!key.equals(key2));
        assertTrue(!key.equals("hi"));
    }
}
