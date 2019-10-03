package datastructures;

/**
 * THIS IS NOT WORKING YET
 * @author alhopasi
 */
public class MyBigInteger {

    private int[] bytes;

    public MyBigInteger(String number) {
        bytes = convertStringToByte(number);
    }

    private int[] convertStringToByte(String number) {
        
        bytes = new int[1];
        
        for (int i = number.length() - 1; i >= 0; i--) {
            int[] byteValue = getByteValue(number.charAt(i), i);
            //addNumber(bytes, byteValue);
        }
        
        return null;
    }

    private int[] getByteValue(char number, int index) {
        byte value = (byte) (number - 48);
        
        return null;
    }

    private int[] addNumber(byte[] oldNumber, byte[] newNumber) {
        return null;
    }
    
    private int[] doubleByteArraySize(byte[] old) {
        return null;
    }

}
