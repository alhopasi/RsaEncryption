package rsaencryption.ui;

import datastructures.MyBigInteger;
import datastructures.MyRandom;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
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
