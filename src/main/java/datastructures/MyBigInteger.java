package datastructures;

import java.util.Arrays;

/**
 * UNDER CONSTRUCTION
 *
 * @author alhopasi
 */
public class MyBigInteger implements Comparable<MyBigInteger> {

    private int[] array;

    public static final MyBigInteger ONE = MyBigInteger.valueOf(1);
    public static final MyBigInteger ZERO = MyBigInteger.valueOf(0);

    public MyBigInteger(String number) {
        array = stringToIntArray(number);
    }

    public MyBigInteger(byte[] bytes) {
        array = byteArrayToIntArray(bytes);
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < array.length; i++) {
            res += Integer.toString(array[i]);
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
        MyBigInteger bigger = this.compareTo(x) > 0 ? new MyBigInteger(this.toString()) : new MyBigInteger(x.toString());
        MyBigInteger smaller = this.compareTo(x) > 0 ? x : this;

        int nextInt = 0;
        for (int i = 0; i < smaller.array.length; i++) {
            int biggerIndex = (bigger.array.length - i) - 1;
            nextInt += smaller.array[(smaller.array.length - i) - 1];
            bigger.array[biggerIndex] += nextInt;
            if (bigger.array[biggerIndex] > 9) {
                nextInt = 1;
                bigger.array[biggerIndex] -= 10;
            } else {
                nextInt = 0;
            }
            if (i == smaller.array.length - 1 && nextInt == 1) {
                if (bigger.array.length == smaller.array.length) {
                    bigger = new MyBigInteger("1".concat(bigger.toString()));
                } else {
                    bigger.array[biggerIndex - 1] += 1;
                }
            }
        }
        return bigger;
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
        MyBigInteger result = new MyBigInteger(toString());
        for (int i = 0; i < x; i++) {
            result = shiftLeftOnce(result);
        }
        return result;
    }

    public MyBigInteger shiftRight(int x) {
        MyBigInteger result = new MyBigInteger(toString());
        for (int i = 0; i < x; i++) {
            result = shiftRightOnce(result);
        }
        return result;
    }

    private MyBigInteger shiftLeftOnce(MyBigInteger bigInt) {
        MyBigInteger result = new MyBigInteger(bigInt.toString());
        int overFlow = 0;
        for (int i = result.array.length - 1; i >= 0; i--) {
            result.array[i] *= 2;
            if (overFlow != 0) {
                result.array[i] += overFlow;
                overFlow = 0;
            }
            if (result.array[i] > 9) {
                overFlow = 1;
                result.array[i] -= 10;
            }
            if (i == 0 && overFlow == 1) {
                result = new MyBigInteger("1".concat(result.toString()));
            }
        }
        return result;
    }

    private MyBigInteger shiftRightOnce(MyBigInteger bigInt) {
        MyBigInteger result = new MyBigInteger(bigInt.toString());
        int overFlow = 0;
        for (int i = 0; i < result.array.length; i++) {
            result.array[i] /= 2;
            if (overFlow != 0) {
                result.array[i] += overFlow;
                overFlow = 0;
            }
            if (bigInt.array[i] % 2 != 0) {
                overFlow = 5;
            }
            if (i == result.array.length - 1 && result.array[0] == 0 && result.array.length > 1) {
                result = new MyBigInteger(result.toString().substring(1));
            }
        }
        return result;
    }

    // This is copied from BigIntegers source, but NOT working. Only works on one int size byte arrays.
    private int[] byteArrayToIntArray(byte[] bytes) {
        // Determine number of words needed.
        int[] newWords = new int[bytes.length / 4 + 1];
        int nwords = newWords.length;

        // Create a int out of modulo 4 high order bytes.
        int bptr = 0;
        int word = 0;
        for (int i = bytes.length % 4; i > 0; --i, bptr++) {
            word = (word << 8) | (bytes[bptr] & 0xff);
        }
        newWords[--nwords] = word;

        // Elements remaining in byte[] are a multiple of 4.
        while (nwords > 0) {
            newWords[--nwords] = bytes[bptr++] << 24
                    | (bytes[bptr++] & 0xff) << 16
                    | (bytes[bptr++] & 0xff) << 8
                    | (bytes[bptr++] & 0xff);
        }
        int[] finalArray = createFinalIntArray(newWords);
        return finalArray;
    }

    private int[] createFinalIntArray(int[] array) {
        if (array == null) {
            int[] zero = {0};
            return zero;
        }

        int[] newArray = new int[array.length * 10];
        int finalLength = 0;
        for (int i = 0; i < array.length; i++) {
            String word = Integer.toUnsignedString(array[i]);
            for (int j = 0; j < word.length(); j++) {
                newArray[i * 10 + j] = word.charAt(j) - 48;
                if (i == array.length - 1 && j == word.length() - 1) {
                    finalLength = i * 10 + j + 1;
                }
            }
        }
        int[] finalArray = new int[newArray.length - (newArray.length - finalLength)];
        System.arraycopy(newArray, 0, finalArray, 0, finalArray.length);
        return finalArray;
    }

    @Override
    public int compareTo(MyBigInteger other) {
        String thisString = this.toString();
        String otherString = other.toString();
        if (thisString.length() > otherString.length()) {
            return 1;
        }
        if (thisString.length() < otherString.length()) {
            return -1;
        }

        for (int i = 0; i < thisString.length(); i++) {
            if (thisString.charAt(i) > otherString.charAt(i)) {
                return 1;
            }
            if (thisString.charAt(i) < otherString.charAt(i)) {
                return -1;
            }
        }
        return 0;
    }

    public int bitLength() {
        // because each int-array stores value from 0-9, it only uses possible
        //the first 4 bits, but if it would be value 16, it would still use
        //that, but in array it would then use 8 bits.

        return 0;
    }
}
