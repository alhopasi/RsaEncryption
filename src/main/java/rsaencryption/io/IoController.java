package rsaencryption.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;
import rsaencryption.utils.Utils;
import rsaencryption.utils.Validators;
import static rsaencryption.utils.Validators.checkValidKey;

public class IoController {

    private Scanner scanner;

    public IoController(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readInput(String line) {
        System.out.print(line);
        return scanner.nextLine();
    }

    public PrivateKey loadPrivateKey(File folder, String keyName) {
        File file = new File(folder + "/" + keyName);
        if (!file.exists()) {
            System.out.println("ERROR: File " + file.getPath() + " does not exist");
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(folder + "/" + keyName))) {
            String first = reader.readLine();
            String second = reader.readLine();
            if (!checkValidKey(first) || !checkValidKey(second)) {
                return null;
            }
            System.out.println("Private key loaded successfully");
            return new PrivateKey(Utils.hexToDecimal(first), Utils.hexToDecimal(second));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public PublicKey loadPublicKey(File folder, String keyName) {
        File file = new File(folder + "/" + keyName);
        if (!file.exists()) {
            System.out.println("ERROR: File " + file.getPath() + " does not exist");
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(folder + "/" + keyName))) {
            String first = reader.readLine();
            String second = reader.readLine();
            if (!Validators.checkValidKey(first) || !Validators.checkValidKey(second)) {
                return null;
            }
            System.out.println("Public key loaded successfully");
            return new PublicKey(Utils.hexToDecimal(first), Utils.hexToDecimal(second));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public void saveFiles(File folder, String keyName, PublicKey publicKey, PrivateKey privateKey) {
        try (FileWriter fileWriter = new FileWriter(folder + "/" + keyName + ".public")) {
            fileWriter.write(publicKey.toString());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
        try (FileWriter fileWriter2 = new FileWriter(folder + "/" + keyName + ".private")) {
            fileWriter2.write(privateKey.toString());
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
        System.out.println("Public key saved to " + folder.getAbsolutePath() + "/" + keyName + ".public");
        System.out.println("Private key saved to " + folder.getAbsolutePath() + "/" + keyName + ".private");
    }

    public String readPlainText() {
        String plaintext = readInput("Enter input: ");
        if (plaintext.length() > 256) {
            System.out.println("Input must be 256 characters or less.");
            return "";
        } else if (plaintext.length() == 0) {
            System.out.println("Input can not be empty");
            return "";
        }
        return plaintext;
    }
}
