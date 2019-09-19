package rsaencryption.ui;

import java.util.Scanner;
import javafx.util.Pair;
import rsaencryption.domain.KeyGenerator;
import rsaencryption.domain.PrivateKey;
import rsaencryption.domain.PublicKey;

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
        TextUi ui = new TextUi(scanner);
        ui.run();
        
    }
}
