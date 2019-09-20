package rsaencryption.utils;

import java.io.File;
import javafx.util.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import rsaencryption.domain.KeyGenerator;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;

public class ValidatorsTest {

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Pair<PublicKey, PrivateKey> keys;

    @Before
    public void setUp() {
        KeyGenerator keyGen = new KeyGenerator();
        keys = keyGen.generateKeys(2048);
        publicKey = keys.getKey();
        privateKey = keys.getValue();
    }

    @Test
    public void checkValidKeyValidatorWorksCorrectly() {
        String[] parts = publicKey.toString().split("\n");
        assertTrue(Validators.checkValidKey(parts[0]));
        assertTrue(Validators.checkValidKey(parts[1]));
        assertTrue(!Validators.checkValidKey("AFGFEDOOOO"));
    }

    @Test
    public void checkPrivateKeyValidatorWorksCorrectly() {
        privateKey = null;
        assertTrue(!Validators.checkPrivateKey(privateKey));
        privateKey = keys.getValue();
        assertTrue(Validators.checkPrivateKey(privateKey));
    }
    
    @Test
    public void checkPublicKeyValidatorWorksCorrectly() {
        publicKey = null;
        assertTrue(!Validators.checkPublicKey(publicKey));
        publicKey = keys.getKey();
        assertTrue(Validators.checkPublicKey(publicKey));
    }
    
    @Test
    public void checkValidHexInputOnDecryptValidatorWorksCorrectly() {
        String encrypted = publicKey.encrypt("cat says meow");
        String encryptedButOneLetterChangedToNonHex = encrypted.replaceFirst("A", "K");
        String encryptedButCut = encrypted.substring(2);
        assertTrue(Validators.checkValidHexInputOnDecrypt(encrypted));
        assertTrue(!Validators.checkValidHexInputOnDecrypt(encryptedButOneLetterChangedToNonHex));
        assertTrue(!Validators.checkValidHexInputOnDecrypt(encryptedButCut));
    }
    
    @Test
    public void checkValidKeyNameInputValidatorWorksCorrectly() {
        String working = "cat";
        String working2 = "cat.public";
        String working3 = "cat.private";
        String failing = "/etc/passwd";
        String failing2 = "../cat";
        String failing3 = "cat..";
        String failing4 = "cat.publi";
        assertTrue(Validators.checkValidKeyNameInput(working));
        assertTrue(Validators.checkValidKeyNameInput(working2));
        assertTrue(Validators.checkValidKeyNameInput(working3));
        assertTrue(!Validators.checkValidKeyNameInput(failing));
        assertTrue(!Validators.checkValidKeyNameInput(failing2));
        assertTrue(!Validators.checkValidKeyNameInput(failing3));
        assertTrue(!Validators.checkValidKeyNameInput(failing4));
    }
    
    @Test
    public void checkValidSaveInputValidatorWorksCorrectly() {
        String working = "cat";
        String failing = "../cat";
        String failing2 = "cat/hi";
        assertTrue(Validators.checkValidSaveInput(working));
        assertTrue(!Validators.checkValidSaveInput(failing));
        assertTrue(!Validators.checkValidSaveInput(failing2));
    }
    
    @Test
    public void checkKeysFolderExistsValidatorWorks() {
        assertEquals(new File("keys/"), Validators.checkKeysFolderExists());
    }
}
