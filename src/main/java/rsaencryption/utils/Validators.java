package rsaencryption.utils;

import java.io.File;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;

/**
 * This class contains methods to validate inputs or to check things.
 */
public class Validators {

    /**
     * Checks that "keys" folder exists under project folder. If not, creates
     * it.
     *
     * @return File keysFolder
     */
    public static File checkKeysFolderExists() {
        String folderName = "keys/";
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    /**
     * Checks that filename for saving contains only letters
     *
     * @param input filename for saving
     * @return true if valid filename for saving
     */
    public static boolean checkValidSaveInput(String input) {
        if (!input.matches("[a-zA-Z]+")) {
            System.out.println("ERROR: Input must include only letters");
            return false;
        }
        return true;
    }

    /**
     * Checks that filename for key(s) is valid when loading keys.
     *
     * @param input filename for key(s)
     * @return true if key(s) name is valid
     */
    public static boolean checkValidKeyNameInput(String input) {
        if (!input.matches("[a-zA-Z]+.(public|private)|[a-zA-Z]+")) {
            System.out.println("ERROR: Key name must be [name], [name].public or [name].private");
            return false;
        }
        return true;
    }

    /**
     * Checks that public key is not null
     * 
     * @param publicKey
     * @return true if public key exists
     */
    public static boolean checkPublicKey(PublicKey publicKey) {
        if (publicKey == null) {
            System.out.println("ERROR: Public key not found.");
            return false;
        }
        return true;
    }
    
    /**
     * Checks that private key is not null
     *
     * @param privateKey
     * @return true if private key exists
     */
    public static boolean checkPrivateKey(PrivateKey privateKey) {
        if (privateKey == null) {
            System.out.println("ERROR: Private key not found.");
            return false;
        }
        return true;
    }
    
    /**
     * Checks that a string (key) is in hex format
     * 
     * @param hex The string (key)
     * @return Returns true if the string is a valid hex.
     */
    public static boolean checkValidKey(String hex) {
        if (!hex.matches("[0-9A-F]+")) {
            System.out.println("ERROR: Key is not in hex format: 0-9,A-F");
            return false;
        }
        return true;
    }

    /**
     * Checks that hex is correct length and includes correct characters when trying to decrypt
     * 
     * @param hex Hex that is going to be decrypted
     * @return true if hex is valid and correct length
     */
    public static boolean checkValidHexInputOnDecrypt(String hex) {
        if (hex.length() > 512 || hex.length() < 510) {
            System.out.println("ERROR: Input is not correct length");
            return false;
        }
        if (!hex.matches("[0-9A-F]+")) {
            System.out.println("ERROR: Input must include only hex characters: 0-9,A-F");
            return false;
        }
        return true;
    }
}
