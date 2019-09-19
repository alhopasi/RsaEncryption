package rsaencryption.ui;

import java.util.Scanner;
import javafx.util.Pair;
import rsaencryption.domain.KeyGenerator;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;

/**
 * User inteface for the RSA encryption system.
 */
public class TextUi {

    private final Scanner scanner;
    private final int bitLength;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * Interface for the RSA encryption system.
     *
     * @param scanner Scanner to handle input.
     */
    public TextUi(Scanner scanner) {
        this.scanner = scanner;
        bitLength = 2048;
    }

    /**
     * Starts the scanner.
     */
    public void run() {
        printTitle();
        while (true) {
            printCommands();
            System.out.print("Command: ");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                System.out.println("ok, thx, bye.");
                break;
            } else if (input.equals("g")) {
                keyOptions();
            } else {
                System.out.println("Work in progress");
            }
        }
    }

    private void keyOptions() {
        generateKeys();
        String plaintext = readPlainText();
        if (plaintext.isEmpty()) {
            return;
        }
        String crypted = encrypt(plaintext);
        decrypt(crypted);
    }

    private void decrypt(String crypted) {
        System.out.println("Decrypting...");
        String message = privateKey.decrypt(crypted);
        System.out.println("Decrypted plaintext: " + message);
    }

    private String encrypt(String plaintext) {
        System.out.println("Encrypting...");
        String crypted = publicKey.encrypt(plaintext);
        System.out.println("Encrypted: " + crypted);
        System.out.println("");

        return crypted;
    }

    private String readPlainText() {
        System.out.print("Enter input: ");
        String plaintext = scanner.nextLine();
        if (plaintext.length() > 214) {
            System.out.println("Input must be less than 215 characters.");
            return "";
        } else if (plaintext.length() == 0) {
            System.out.println("Input can not be empty");
            return "";
        }
        return plaintext;
    }

    private void generateKeys() {
        System.out.println("Generating keys...");
        KeyGenerator keyGen = new KeyGenerator();
        Pair<PublicKey, PrivateKey> keys = keyGen.generateKeys(bitLength);

        publicKey = keys.getKey();
        privateKey = keys.getValue();

        System.out.println("Keys generated");
        System.out.println("");
    }

    private void printCommands() {
        System.out.println("g - generate new key and enter input to encrypt");
        System.out.println("q - quit");
    }

    private void printTitle() {
        System.out.println("*****************");
        System.out.println("****         ****");
        System.out.println("****  R S A  ****");
        System.out.println("****         ****");
        System.out.println("*****************");
        System.out.println("");
    }

}
