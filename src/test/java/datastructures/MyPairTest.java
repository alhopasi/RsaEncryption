
package datastructures;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;

public class MyPairTest {

    private MyPair<PublicKey, PrivateKey> pair;
    
    @Before
    public void setUp() {
        PublicKey publicKey = new PublicKey(new BigInteger("3233"),new BigInteger("17"));
        PrivateKey privateKey = new PrivateKey(new BigInteger("3233"), new BigInteger("2753"));
        pair = new MyPair<>(publicKey, privateKey);
    }
    
    @Test
    public void pairIsCreated() {
        assertNotNull(pair);
    }
    
    @Test
    public void firstObjectEqualsStored() {
        assertEquals(new PublicKey(new BigInteger("3233"),new BigInteger("17")), pair.getKey());
    }
    
    @Test
    public void secondObjectEqualsStored() {
        assertEquals(new PrivateKey(new BigInteger("3233"), new BigInteger("2753")), pair.getValue());
    }
}
