package rsaencryption.ui;

import java.util.Scanner;

/**
 * RSA-encryption
 * @author alhopasi
 */
public class RsaEncryption {

    /**
     * main class that starts the user interface.
     * @param args 
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TextUi ui = new TextUi(scanner);
        ui.run();

    }
}
