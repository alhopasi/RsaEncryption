
package rsaencryption.io;

import java.io.File;
import java.util.Scanner;
import datastructures.MyPair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import rsaencryption.domain.KeyGenerator;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;

public class IoControllerTest {
    
    IoController io;
    
    @Before
    public void setUp() {
        io = new IoController(new Scanner("cat says meow"));
    }
    
    @Test
    public void readInputReturnsInput() {
        assertEquals("cat says meow", io.readInput(""));
    }
    
    @Test
    public void saveKeysSavesKeys() {
        KeyGenerator keyGen = new KeyGenerator();
        MyPair<PublicKey, PrivateKey> keys = keyGen.generateKeys(2048);
        PublicKey publicKey = keys.getKey();
        PrivateKey privateKey = keys.getValue();
        boolean test1 = io.saveKeys(new File("keys"), "test", publicKey, privateKey);
        boolean test2 = io.saveKeys(new File("noFolder"), "test", publicKey, privateKey);
        assertTrue(test1);
        assertTrue(!test2);
    }
    
    @Test
    public void loadPrivateKeyLoadsPrivateKey() {
        KeyGenerator keyGen = new KeyGenerator();
        MyPair<PublicKey, PrivateKey> keys = keyGen.generateKeys(2048);
        PublicKey publicKey = keys.getKey();
        PrivateKey privateKey = keys.getValue();
        io.saveKeys(new File("keys"), "test", publicKey, privateKey);
        PrivateKey loadedPrivateKey = io.loadPrivateKey(new File("keys"), "test.private");
        assertEquals(privateKey, loadedPrivateKey);
        loadedPrivateKey = io.loadPrivateKey(new File("noFolder"), "test.private");
        assertEquals(null, loadedPrivateKey);
    }
    
    @Test
    public void loadPublicKeyLoadsPublicKey() {
        KeyGenerator keyGen = new KeyGenerator();
        MyPair<PublicKey, PrivateKey> keys = keyGen.generateKeys(2048);
        PublicKey publicKey = keys.getKey();
        PrivateKey privateKey = keys.getValue();
        io.saveKeys(new File("keys"), "test", publicKey, privateKey);
        PublicKey loadedPublicKey = io.loadPublicKey(new File("keys"), "test.public");
        assertEquals(publicKey, loadedPublicKey);
        loadedPublicKey = io.loadPublicKey(new File("noFolder"), "test.public");
        assertEquals(null, loadedPublicKey);
    }
    
    @Test
    public void readPlainTextWorksCorrectly() {
        assertEquals("cat says meow", io.readPlainText());
        io = new IoController(new Scanner("\n"));
        assertEquals("", io.readPlainText());
        io = new IoController(new Scanner("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaa"));
        assertEquals("", io.readPlainText());
    }
}
