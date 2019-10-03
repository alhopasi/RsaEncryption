package datastructures;

/**
 * THIS IS NOT WORKING YET
 *
 * @author alhopasi
 */
public class MyBigInteger implements Comparable<MyBigInteger> {

    private int[] words;

    public static final MyBigInteger ONE = MyBigInteger.valueOf(1);
    public static final MyBigInteger ZERO = MyBigInteger.valueOf(0);

    public MyBigInteger(String number) {
        words = stringToIntArray(number);
    }
    
    public MyBigInteger(byte[] bytes) {
            
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < words.length; i++) {
            res += Integer.toString(words[i]);
        }
        return res;
    }
    
    private int[] stringToIntArray(String number) {
        int[] numbers = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            numbers[i] = number.charAt(i) - 48;
        }
        return numbers;
    }
    
    /**
     * Powers MyBigInteger with exponent and the takes modulo.
     *
     * @param e The power
     * @param m The modulo
     * @return the result
     */
    public MyBigInteger modPow(MyBigInteger e, MyBigInteger m) {
        
        MyBigInteger newBigInt = this;
        
        for (int bit = e.bitLength() - 2; bit >= 0; bit--) {
            newBigInt = (newBigInt.multiply(newBigInt)).mod(m);
            if (e.testBit(bit) == true) {
                newBigInt = newBigInt.multiply(this).mod(m);
            }
        }
        return this;
    }
    
    public static MyBigInteger valueOf(int i) {
        return new MyBigInteger(String.valueOf(i));
    }
    
    public boolean testBit(int bit) {
        return false;
    }
    
    public MyBigInteger add(MyBigInteger x) {
        return null;
    }
    
    public MyBigInteger subtract(MyBigInteger x) {
        return null;
    }
    
    public MyBigInteger multiply(MyBigInteger x) {
        return null;
    }
    
    public MyBigInteger mod(MyBigInteger m) {
        return null;
    }
    
    public MyBigInteger shiftLeft(int x) {
        return null;
    }
    
    public MyBigInteger shiftRight(int x) {
        return null;
    }
    
    @Override
    public int compareTo(MyBigInteger other) {
        return 0;
    }
    
    public int bitLength() {
        return words.length * 32;
    }
}
