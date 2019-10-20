package rsaencryption.utils;

import datastructures.MyBigInteger;

/**
 * Contains several static utility functions to manipulate Strings, Hexes and
 * Integers.
 */
public class Utils {

    public static void arrayCopyInt(int[] oldArray, int oldArrayStartingIndex, int[] newArray, int newArrayStartingIndex, int length) {
        for (int i = 0; i < length; i++) {
            newArray[i + newArrayStartingIndex] = oldArray[i + oldArrayStartingIndex];
        }
    }
    
    public static void arrayCopyBoolean(boolean[] oldArray, int oldArrayStartingIndex, boolean[] newArray, int newArrayStartingIndex, int length) {
        for (int i = 0; i < length; i++) {
            newArray[i + newArrayStartingIndex] = oldArray[i + oldArrayStartingIndex];
        }
    }
    
    /**
     * Converts a decimal integer to hex String.
     *
     * @param integer Decimal to be converted.
     * @return String that is hexes.
     */
    public static String decimalToHex(MyBigInteger integer) {
        char hexchars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        MyBigInteger value = integer;
        int remaining;
        MyBigInteger sixteen = new MyBigInteger("16");
        String hex = "";

        while (value.compareTo(MyBigInteger.ZERO) > 0) {
            remaining = value.mod(sixteen).intValue();
            hex = hexchars[remaining] + hex;
            value = value.divide(sixteen);
        }
        return hex;
    }

    /**
     * Converts String to hexes and then represents that as integer.
     *
     * @param message String to be converted.
     * @return Integer that represents the hexes that were converted from a
     * String.
     */
    public static MyBigInteger stringToDecimal(String message) {
        String hex = stringToHex(message);

        MyBigInteger uncryptedMsg = hexToDecimal(hex);
        return uncryptedMsg;
    }

    /**
     * Converts a decimal integer into hexes and then to String.
     *
     * @param integer the decimal to be converted.
     * @return String in readable format.
     */
    public static String decimalToString(MyBigInteger integer) {
        String hex = decimalToHex(integer);
        //System.out.println(hex);
        String message = hexToString(hex);
        return message;
    }

    /**
     * Converts a hex String into integer.
     *
     * @param hex the String to be converted.
     * @return Integer representation of the hex.
     */
    public static MyBigInteger hexToDecimal(String hex) {
        MyBigInteger sixteen = new MyBigInteger("16");
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        MyBigInteger value = MyBigInteger.ZERO;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            MyBigInteger intValue = getValueOfHex(c);
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
            MyBigInteger value = hexToDecimal(character);
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

    private static MyBigInteger getValueOfHex(char hex) {
        if (hex == 'A') {
            return new MyBigInteger("10");
        } else if (hex == 'B') {
            return new MyBigInteger("11");
        } else if (hex == 'C') {
            return new MyBigInteger("12");
        } else if (hex == 'D') {
            return new MyBigInteger("13");
        } else if (hex == 'E') {
            return new MyBigInteger("14");
        } else if (hex == 'F') {
            return new MyBigInteger("15");
        } else {
            return new MyBigInteger(String.valueOf(hex));
        }
    }
}