package rsaencryption.domain;

import datastructures.MyBigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import rsaencryption.utils.Utils;

public class PrivateKeyTest {

    private PrivateKey privateKey;
    private MyBigInteger n;
    private MyBigInteger d;

    @Before
    public void setUp() {
        n = new MyBigInteger("3233");
        d = new MyBigInteger("2753");
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
