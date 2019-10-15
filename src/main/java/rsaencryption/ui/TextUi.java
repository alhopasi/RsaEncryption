package rsaencryption.ui;

import java.io.File;
import datastructures.MyPair;
import rsaencryption.domain.KeyGenerator;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;
import rsaencryption.io.IoController;
import rsaencryption.utils.Validators;
import static rsaencryption.utils.Validators.checkKeysFolderExists;

/**
 * User inteface for the RSA encryption system.
 */
public class TextUi {

    private final IoController io;
    private int bitLength;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * Interface for the RSA encryption system.
     *
     * @param io IoController to handle all io operations.
     */
    public TextUi(IoController io) {
        this.io = io;
        bitLength = 2048;
    }

    /**
     * Starts the program.
     */
    public void run() {
        printTitle();
        while (true) {
            printCommands();
            String input = io.readInput("Command: ");

            if (input.equals("q")) {
                System.out.println("ok, thx, bye.");
                break;
            } else if (input.equals("g")) {
                generateKeys();
            } else if (input.equals("e")) {
                encrypt();
            } else if (input.equals("d")) {
                decrypt();
            } else if (input.equals("s")) {
                save();
            } else if (input.equals("l")) {
                load();
            } else if (input.equals("b")) {
                changeBitLength();
            } else {
                System.out.println("Unknown command");
            }
        }
    }

    private void changeBitLength() {
        String newBitLength = io.readInput("Give new bit size: ");
        if (newBitLength.matches("[0-9]+")) {
            try {
                int bitLengthInt = Integer.valueOf(newBitLength);
                bitLength = bitLengthInt;
            } catch (Exception e) {
                System.out.println("e: " + e);
            }
        } else {
            System.out.println("Invalid input, cancelling...");
        }
    }

    private void load() {
        File folder = checkKeysFolderExists();

        String keyName = io.readInput("Give name of the key(s) to load: ");
        if (!Validators.checkValidKeyNameInput(keyName)) {
            return;
        }
        if (!keyName.contains(".")) {
            publicKey = io.loadPublicKey(folder, keyName + ".public");
            privateKey = io.loadPrivateKey(folder, keyName + ".private");
        } else {
            if (keyName.matches("[a-zA-Z]+.public$")) {
                publicKey = io.loadPublicKey(folder, keyName);
            } else {
                privateKey = io.loadPrivateKey(folder, keyName);
            }
        }
    }

    private void save() {
        if (!Validators.checkPublicKey(publicKey) || !Validators.checkPrivateKey(privateKey)) {
            return;
        }
        File folder = Validators.checkKeysFolderExists();

        String keyName = io.readInput("Give name for the keys: ");
        if (!Validators.checkValidSaveInput(keyName)) {
            return;
        }
        io.saveKeys(folder, keyName, publicKey, privateKey);
    }

    private void encrypt() {
        if (!Validators.checkPublicKey(publicKey)) {
            return;
        }
        String plaintext = io.readPlainText();
        if (plaintext.isEmpty()) {
            return;
        }
        System.out.println("Encrypting...");
        String crypted = publicKey.encrypt(plaintext);
        System.out.println("Encrypted: " + crypted);
    }

    private void decrypt() {
        if (!Validators.checkPrivateKey(privateKey)) {
            return;
        }
        String crypted = io.readInput("Enter input: ");
        if (!Validators.checkValidHexInputOnDecrypt(crypted)) {
            return;
        }
        System.out.println("Decrypting...");
        try {
            String message = privateKey.decrypt(crypted);
            System.out.println("Decrypted plaintext: " + message);
        } catch (Exception e) {
            System.out.println("ERROR: Something went horribly wrong! (decrypting failed)");
        }
    }

    private void generateKeys() {
        System.out.println("Generating keys...");
        KeyGenerator keyGen = new KeyGenerator();
        MyPair<PublicKey, PrivateKey> keys = keyGen.generateKeys(bitLength);

        publicKey = keys.getKey();
        privateKey = keys.getValue();

        System.out.println("Keys generated");
    }

    private void printCommands() {
        printKeyInfo();

        System.out.println("");
        System.out.println("g - generate new keys (" + bitLength + " bit)");
        System.out.println("e - encrypt a text");
        System.out.println("d - decrypt a text");
        System.out.println("s - save keys");
        System.out.println("l - load keys");
        System.out.println("b - change key bit size");
        System.out.println("q - quit");
    }

    private void printKeyInfo() {
        String pu = "";
        String pi = "";
        String no = "no keys";
        if (publicKey != null) {
            pu = "Public key";
            no = "";
        }
        if (privateKey != null) {
            if (publicKey != null) {
                pu = pu.concat(", ");
            }
            pi = "Private key";
            no = "";
        }
        System.out.println("");
        System.out.println("LOADED: " + pu + pi + no);

    }

    private void printTitle() {
        System.out.println("*****************");
        System.out.println("****         ****");
        System.out.println("****  R S A  ****");
        System.out.println("****         ****");
        System.out.println("*****************");
    }
}
