package rsaencryption.ui;

import datastructures.MyBigInteger;
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

        /*
        // Working on my own Big Integer, ignore this.
        MyBigInteger test = MyBigInteger.valueOf(12);
        byte[] bytes = {0b001, 0b0100};
        MyBigInteger test2 = new MyBigInteger(bytes);
        System.out.println(test);
        System.out.println(MyBigInteger.ONE);
        System.out.println(MyBigInteger.ZERO);
        System.out.println(test.testBit(3));
        */
        
        Scanner scanner = new Scanner(System.in);
        IoController io = new IoController(scanner);
        TextUi ui = new TextUi(io);
        ui.run();

    }
}
