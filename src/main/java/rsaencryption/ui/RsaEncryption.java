package rsaencryption.ui;

import java.util.Scanner;
import rsaencryption.io.IoController;

/**
 * RSA-encryption
 *
 * @author alhopasi
 */
public class RsaEncryption {

    /**
     * main class that starts the user interface.
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IoController io = new IoController(scanner);
        TextUi ui = new TextUi(io);
        ui.run();

    }
}
