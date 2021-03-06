package rsaencryption.domain;

import datastructures.MyBigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import rsaencryption.utils.Utils;

public class PublicKeyTest {
    
    private PublicKey publicKey;
    private MyBigInteger n;
    private MyBigInteger e;
    
    @Before
    public void setUp() {
        n = new MyBigInteger("3233");
        e = new MyBigInteger("17");
        publicKey = new PublicKey(n, e);
    }

    @Test
    public void publicKeyIsCreated() {
        assertNotNull(publicKey);
    }

    @Test
    public void encryptWorks() {
        String message = "a";
        String cipher = publicKey.encrypt(message);
        assertEquals("660", cipher);
    }

    @Test
    public void toStringIsCorrect() {
        assertEquals(Utils.decimalToHex(n) + "\n" + Utils.decimalToHex(e), publicKey.toString());
    }
    
    
    @Test
    public void equals() {
        PublicKey key = new PublicKey(n,e);
        PublicKey key2 = new PublicKey(e,n);
        assertTrue(key.equals(publicKey));
        assertTrue(!key.equals(key2));
        assertTrue(!key.equals("hi"));
    }
}
