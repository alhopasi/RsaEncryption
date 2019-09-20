package rsaencryption.utils;

import java.math.BigInteger;

/**
 * Contains several static utility functions to manipulate Strings, Hexes and Integers.
 */
public class Utils {

    /**
     * Converts a decimal integer to hex String.
     * @param integer Decimal to be converted.
     * @return String that is hexes.
     */
    public static String decimalToHex(BigInteger integer) {
        char hexchars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        BigInteger value = integer;
        int remaining;
        BigInteger sixteen = new BigInteger("16");
        String hex = "";

        while (value.compareTo(BigInteger.ZERO) > 0) {
            remaining = value.mod(sixteen).intValue();
            hex = hexchars[remaining] + hex;
            value = value.divide(sixteen);
        }
        return hex;
    }

    /**
     * Converts String to hexes and then represents that as integer.
     * @param message String to be converted.
     * @return Integer that represents the hexes that were converted from a String.
     */
    public static BigInteger stringToDecimal(String message) {
        String hex = stringToHex(message);

        BigInteger uncryptedMsg = hexToDecimal(hex);
        return uncryptedMsg;
    }

    /**
     * Converts a decimal integer into hexes and then to String.
     * @param integer the decimal to be converted.
     * @return String in readable format.
     */
    public static String decimalToString(BigInteger integer) {
        String hex = decimalToHex(integer);
        //System.out.println(hex);
        String message = hexToString(hex);
        return message;
    }
    
    /**
     * Converts a hex String into integer.
     * @param hex the String to be converted.
     * @return Integer representation of the hex.
     */
    public static BigInteger hexToDecimal(String hex) {
        BigInteger sixteen = new BigInteger("16");
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        BigInteger value = BigInteger.ZERO;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            BigInteger intValue = getValueOfHex(c);
            value = value.multiply(sixteen).add(intValue);
        }
        return value;
    }

    private static String stringToHex(String message) {
        String hex = "";
        for (int i = 0; i < message.length(); i++) {
            hex += charToHex(message.charAt(i));
        }
        return hex;
    }

    private static String hexToString(String hex) {
        String message = "";
        String[] hexes = hex.split("");
        for (int i = 0; i < hex.length(); i += 2) {
            String character = hexes[i] + hexes[i + 1];
            BigInteger value = hexToDecimal(character);
            int intValue = Integer.valueOf(value.toString());
            message += (char) intValue;
        }
        return message;
    }

    private static String charToHex(char c) {
        char hexchars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int value = (int) c;
        int remaining;
        String hex = "";

        while (value > 0) {
            remaining = value % 16;
            hex = hexchars[remaining] + hex;
            value = value / 16;
        }
        return hex;
    }

    private static BigInteger getValueOfHex(char hex) {
        if (hex == 'A') {
            return new BigInteger("10");
        } else if (hex == 'B') {
            return new BigInteger("11");
        } else if (hex == 'C') {
            return new BigInteger("12");
        } else if (hex == 'D') {
            return new BigInteger("13");
        } else if (hex == 'E') {
            return new BigInteger("14");
        } else if (hex == 'F') {
            return new BigInteger("15");
        } else {
            return new BigInteger(String.valueOf(hex));
        }
    }
}
