package datastructures;

import java.util.Arrays;
import rsaencryption.utils.Utils;

/**
 * MyBigInteger is a BigInteger implementation
 *
 * @author alhopasi
 */
public class MyBigInteger implements Comparable<MyBigInteger> {

    private final int[] array;
    private boolean[] bits;

    public static final MyBigInteger ONE = new MyBigInteger("1");
    public static final MyBigInteger ZERO = new MyBigInteger("0");

    /**
     * Constructor to create MyBigInteger
     *
     * @param number Value given to MyBigInteger
     */
    public MyBigInteger(String number) {
        int[] intArray = stringToIntArray(number);
        array = removeZerosFromBeginning(intArray);
    }

    /**
     * Constructor to create MyBigInteger Constructs a number from the bytes
     * given. Rightmost bit/byte is the least significant.
     *
     * @param array the bytes given.
     */
    public MyBigInteger(byte[] array) {
        int[] intArray = byteArrayToIntArray(array);
        this.array = removeZerosFromBeginning(intArray);
    }

    /**
     * Constructor to create MyBigInteger
     *
     * @param array int array, where each int should be number 0-9.
     */
    public MyBigInteger(int[] array) {
        this.array = removeZerosFromBeginning(array);
    }

    /**
     * String representation of this MyBigIntegers value
     *
     * @return Value of the MyBigInteger as a String
     */
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < array.length; i++) {
            res += Integer.toString(array[i]);
        }
        return res;
    }

    private static int[] stringToIntArray(String number) {
        int[] numbers = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            numbers[i] = number.charAt(i) - 48;
        }
        numbers = removeZerosFromBeginning(numbers);
        return numbers;
    }

    /**
     * Returns the MyBigIntegers value as integer. If the value is too big to
     * fit into integer, returns -1.
     *
     * @return Value of this MyBigInteger.
     */
    public int intValue() {
        try {
            return Integer.valueOf(toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Powers MyBigInteger with exponent and the calculates the modulo.
     *
     * @param e The power
     * @param m The modulo
     * @return the result
     */
    public MyBigInteger modPow(MyBigInteger e, MyBigInteger m) {
        MyBigInteger newBigInt = this;
        MyBigInteger two = new MyBigInteger("2");
        if (m.compareTo(MyBigInteger.ONE) == 0) {
            return MyBigInteger.ZERO;
        }
        MyBigInteger r = MyBigInteger.ONE;
        newBigInt = newBigInt.mod(m);
        while (e.compareTo(MyBigInteger.ZERO) > 0) {
            if (e.mod(two).compareTo(MyBigInteger.ONE) == 0) {
                r = r.multiply(newBigInt).mod(m);
            }
            e = e.shiftRightOnce();
            newBigInt = newBigInt.multiply(newBigInt).mod(m);
        }
        return r;
    }

    /**
     * Calculates the sum of this and another MyBigInteger.
     *
     * @param x MyBigInteger that is added.
     * @return new MyBigInteger with the value of this + x.
     */
    public MyBigInteger add(MyBigInteger x) {
        MyPair<int[], int[]> arrays = getSmallerAndBiggerArray(this.array, x.array);
        int[] biggerArray = arrays.getKey();
        int[] smallerArray = arrays.getValue();
        int carry = 0;
        int biggerArrayIndex = 0;
        for (int i = 0; i < smallerArray.length; i++) {
            biggerArrayIndex = biggerArray.length - 1 - i;
            biggerArray[biggerArrayIndex] += carry;
            biggerArray[biggerArrayIndex] += smallerArray[smallerArray.length - 1 - i];
            carry = addCheckCarry(biggerArray, biggerArrayIndex, carry);
        }
        while (carry == 1) {
            biggerArrayIndex--;
            biggerArray[biggerArrayIndex] += 1;
            carry = addCheckCarry(biggerArray, biggerArrayIndex, carry);
        }
        int[] finalArray = removeZerosFromBeginning(biggerArray);
        return new MyBigInteger(finalArray);
    }

    private int addCheckCarry(int[] biggerArray, int biggerArrayIndex, int carry) {
        if (biggerArray[biggerArrayIndex] > 9) {
            carry = 1;
            biggerArray[biggerArrayIndex] -= 10;
        } else {
            carry = 0;
        }
        return carry;
    }

    private static MyPair<int[], int[]> getSmallerAndBiggerArray(int[] thisArray, int[] otherArray) {
        int[] biggerArray;
        int[] smallerArray;
        boolean thisBigger = getSmallerAndBiggerCheckIfThisArraytIsBigger(thisArray, otherArray);
        if (thisBigger) {
            biggerArray = new int[thisArray.length + 1];
            Utils.arrayCopyInt(thisArray, 0, biggerArray, 1, thisArray.length);
            smallerArray = new int[otherArray.length];
            Utils.arrayCopyInt(otherArray, 0, smallerArray, 0, otherArray.length);
        } else {
            biggerArray = new int[otherArray.length + 1];
            Utils.arrayCopyInt(otherArray, 0, biggerArray, 1, otherArray.length);
            smallerArray = new int[thisArray.length];
            Utils.arrayCopyInt(thisArray, 0, smallerArray, 0, thisArray.length);
        }
        return new MyPair(biggerArray, smallerArray);
    }
    
    private static boolean getSmallerAndBiggerCheckIfThisArraytIsBigger(int[] thisArray, int[] otherArray) {
        if (thisArray.length > otherArray.length) {
            return true;
        }
        if (thisArray.length == otherArray.length) {
            for (int i = 0; i < thisArray.length; i++) {
                if (thisArray[i] < otherArray[i]) {
                    return false;
                } else if (thisArray[i] > otherArray[i]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Tests if a bit at certain index is positive. Index starts at 0, being the
     * least significant.
     *
     * @param index
     * @return true or false
     */
    public boolean testBit(int index) {
        bits = getBitArray();
        return bits[bits.length - 1 - index];
    }

    /**
     * Subtracts the value of this and another MyBigInteger. The value is
     * subtracted from bigger MyBigInteger.
     *
     * @param x MyBigInteger that is used in the process.
     * @return new MyBigInteger with the value of bigger - smaller.
     */
    public MyBigInteger subtract(MyBigInteger x) {
        MyPair<int[], int[]> arrays = getSmallerAndBiggerArray(this.array, x.array);
        int[] biggerArray = arrays.getKey();
        int[] smallerArray = arrays.getValue();
        int carry = 0;
        int biggerArrayIndex = 0;
        for (int i = 0; i < smallerArray.length; i++) {
            biggerArrayIndex = biggerArray.length - 1 - i;
            biggerArray[biggerArrayIndex] -= smallerArray[smallerArray.length - 1 - i] + carry;
            carry = subtractCheckCarry(biggerArray, biggerArrayIndex, carry);
        }
        biggerArrayIndex--;
        while (carry == 1) {
            biggerArray[biggerArrayIndex] -= 1;
            carry = subtractCheckCarry(biggerArray, biggerArrayIndex, carry);
            biggerArrayIndex--;
        }
        int[] finalArray = removeZerosFromBeginning(biggerArray);
        return new MyBigInteger(finalArray);
    }

    private static int subtractCheckCarry(int[] biggerArray, int biggerArrayIndex, int carry) {
        if (biggerArray[biggerArrayIndex] < 0) {
            carry = 1;
            biggerArray[biggerArrayIndex] += 10;
        } else {
            carry = 0;
        }
        return carry;
    }

    private static int[] removeZerosFromBeginning(int[] array) {
        int[] finalArray = array;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                if (i == 0) {
                    break;
                }
                finalArray = new int[array.length - i];
                Utils.arrayCopyInt(array, i, finalArray, 0, finalArray.length);
                break;
            }
            if (i == array.length - 1) {
                finalArray = new int[1];
                finalArray[0] = array[array.length - 1];
            }
        }
        return finalArray;
    }

    /**
     * Multiplies the MyBigInteger with another one.
     *
     * @param x multiplyer.
     * @return new MyBigInteger, that is the result of multiplication.
     */
    public MyBigInteger multiply(MyBigInteger x) {
        if (x.compareTo(MyBigInteger.ZERO) == 0 || this.compareTo(MyBigInteger.ZERO) == 0) {
            return MyBigInteger.ZERO;
        }
        MyPair<int[], int[]> arrays = getSmallerAndBiggerArray(this.array, x.array);
        int[] biggerArray = arrays.getKey();
        int[] smallerArray = arrays.getValue();
        MyBigInteger bigger = new MyBigInteger(biggerArray);
        int[] result = new int[smallerArray.length + biggerArray.length + 1];
        multiplyCalculateResult(smallerArray, result, bigger);
        result = removeZerosFromBeginning(result);
        return new MyBigInteger(result);
    }

    private static void multiplyCalculateResult(int[] smallerArray, int[] result, MyBigInteger bigger) {
        for (int i = 0; i < smallerArray.length; i++) {
            MyBigInteger total = MyBigInteger.ZERO;
            for (int j = 0; j < smallerArray[smallerArray.length - 1 - i]; j++) {
                total = total.add(bigger);
            }
            for (int j = 0; j < total.array.length; j++) {
                int resultIndex = result.length - i - j - 1;
                int totalIndex = total.array.length - j - 1;
                result[resultIndex] += total.array[totalIndex];
                if (result[resultIndex] > 9) {
                    result[resultIndex] -= 10;
                    result[resultIndex - 1] += 1;
                }
            }
        }
    }

    /**
     * Calculates this mod other, where this and other are MyBigIntegers.
     *
     * @param m The modulo taken from this.
     * @return new MyBigInteger with the modulo result as value.
     */
    public MyBigInteger mod(MyBigInteger m) {
        MyBigInteger divided = this.divide(m);
        return this.subtract(divided.multiply(m));
    }

    /**
     * Divides this MyBigInteger with another one.
     *
     * @param x The divider as MyBigInteger.
     * @return new MyBigInteger with the result as value.
     */
    public MyBigInteger divide(MyBigInteger x) {
        if (x.compareTo(MyBigInteger.ZERO) <= 0) {
            throw new NumberFormatException("Divider must be 1 or higher");
        }
        if (this.compareTo(x) <= 0) {
            return this.compareTo(x) == 0 ? MyBigInteger.ONE : MyBigInteger.ZERO;
        }
        int[] thisCopy = new int[this.array.length];
        Utils.arrayCopyInt(this.array, 0, thisCopy, 0, this.array.length);
        MyBigInteger dividend = new MyBigInteger(thisCopy);
        int[] result = divideDoLongDivision(dividend, this.array, x);
        result = removeZerosFromBeginning(result);
        return new MyBigInteger(result);
    }

    private static int[] divideDoLongDivision(MyBigInteger dividend, int[] thisArray, MyBigInteger x) {
        int thisBeginIndex = 0;
        int thisEndIndex = 1;
        int[] result = new int[thisArray.length];
        while (true) {
            if (thisEndIndex > thisArray.length) {
                break;
            }
            MyBigInteger longDividend = divideCheckLongDividend(thisBeginIndex, thisEndIndex, dividend.array);
            int times = 0;
            while (longDividend.compareTo(x) >= 0) {
                longDividend = longDividend.subtract(x);
                times++;
            }
            thisBeginIndex = divideCheckForSubtract(dividend, longDividend, thisBeginIndex, thisEndIndex);
            result[thisEndIndex - 1] = times;
            thisEndIndex++;
        }
        return result;
    }

    private static int divideCheckForSubtract(MyBigInteger dividend, MyBigInteger longDividend, int thisBeginIndex, int thisEndIndex) {
        if (longDividend.compareTo(MyBigInteger.ZERO) == 0) {
            thisBeginIndex = thisEndIndex;
        } else {
            int digitsLeftFromSubtract = longDividend.array.length;
            thisBeginIndex = thisEndIndex - digitsLeftFromSubtract;
            Utils.arrayCopyInt(longDividend.array, 0, dividend.array, thisBeginIndex, digitsLeftFromSubtract);
        }
        return thisBeginIndex;
    }

    private static MyBigInteger divideCheckLongDividend(int thisBeginIndex, int thisEndIndex, int[] dividendArray) {
        int[] longDividerArray = new int[thisEndIndex - thisBeginIndex];
        Utils.arrayCopyInt(dividendArray, thisBeginIndex, longDividerArray, 0, thisEndIndex - thisBeginIndex);
        MyBigInteger longDividend = new MyBigInteger(longDividerArray);
        return longDividend;
    }

    /**
     * Does shiftRight operation (multiply by 2) x times.
     *
     * @param x how many times is shifted
     * @return new MyBigInteger with the new value.
     */
    public MyBigInteger shiftLeft(int x) {
        MyBigInteger result = new MyBigInteger(toString());
        for (int i = 0; i < x; i++) {
            result = result.shiftLeftOnce();
        }
        return result;
    }

    /**
     * Does shiftRight operation (divide by 2) x times.
     *
     * @param x how many times is shifted
     * @return new MyBigInteger with the new value.
     */
    public MyBigInteger shiftRight(int x) {
        MyBigInteger result = new MyBigInteger(toString());
        for (int i = 0; i < x; i++) {
            result = result.shiftRightOnce();
        }
        return result;
    }

    private MyBigInteger shiftLeftOnce() {
        boolean bit = false;
        int[] newArray = shiftLeftCopyArray(this.array);
        for (int i = newArray.length - 1; i >= 0; i--) {
            newArray[i] = newArray[i] << 1;
            boolean tmp = newArray[i] > 9;
            if (tmp) {
                newArray[i] -= 10;
            }
            if (bit) {
                newArray[i]++;
            }
            bit = tmp;
        }
        return new MyBigInteger(newArray);
    }

    private int[] shiftLeftCopyArray(int[] thisArray) {
        int[] newArray;
        if (thisArray[0] >= 5) {
            newArray = new int[thisArray.length + 1];
            Utils.arrayCopyInt(thisArray, 0, newArray, 1, thisArray.length);
        } else {
            newArray = new int[thisArray.length];
            Utils.arrayCopyInt(thisArray, 0, newArray, 0, thisArray.length);
        }
        return newArray;
    }

    private MyBigInteger shiftRightOnce() {
        boolean bit = false;
        if (this.array[0] == 1 && this.array.length == 1) {
            return MyBigInteger.ZERO;
        }
        int[] newArray = shiftRightCopyArray(this.array);
        if (this.array.length != newArray.length) {
            bit = true;
        }
        for (int i = 0; i < newArray.length; i++) {
            boolean tmp = newArray[i] % 2 == 1;
            newArray[i] = newArray[i] >> 1;
            if (bit) {
                newArray[i] += 5;
            }
            bit = tmp;
        }
        return new MyBigInteger(newArray);
    }

    private int[] shiftRightCopyArray(int[] thisArray) {
        int[] newArray;
        if (thisArray[0] == 1) {
            newArray = new int[thisArray.length - 1];
            Utils.arrayCopyInt(thisArray, 1, newArray, 0, newArray.length);
        } else {
            newArray = new int[thisArray.length];
            Utils.arrayCopyInt(thisArray, 0, newArray, 0, thisArray.length);
        }
        return newArray;
    }

    /**
     * Equals checks from the MyBigIntegers array if it's the same.
     *
     * @param obj Another object to compare.
     * @return true if another object is same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyBigInteger other = (MyBigInteger) obj;
        if (!Arrays.equals(this.array, other.array)) {
            return false;
        }
        return true;
    }

    /**
     * Compares this with another MyBigInteger. Compares the MyBigIntegers
     * arrays length. If they are same, compares the values.
     *
     * @param other MyBigInteger that is compared to this
     * @return result of the comparison
     */
    @Override
    public int compareTo(MyBigInteger other) {
        if (this.array.length < other.array.length) {
            return -1;
        }
        if (this.array.length > other.array.length) {
            return 1;
        }
        for (int i = 0; i < this.array.length; i++) {
            if (this.array[i] < other.array[i]) {
                return -1;
            }
            if (this.array[i] > other.array[i]) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Calculates how many bits are in the MyBigInteger value.
     *
     * @return number of bits.
     */
    public int bitLength() {
        bits = getBitArray();
        if (bits.length == 1 && bits[0] == false) {
            return 0;
        }
        return bits.length;
    }

    private boolean[] getBitArray() {
        if (bits == null) {
            bits = toBitArray();
        }
        return bits;
    }

    private int[] byteArrayToIntArray(byte[] array) {
        MyBigInteger result = MyBigInteger.ZERO;
        MyBigInteger bit = MyBigInteger.ONE;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < 8; j++) {
                if (array[array.length - i - 1] % 2 == 1) {
                    result = result.add(bit);
                }
                bit = bit.shiftLeftOnce();
                array[array.length - i - 1] /= 2;
            }
        }
        return result.array;
    }

    private boolean[] toBitArray() {
        boolean[] thisBits = new boolean[100];
        int index = 0;
        MyBigInteger toCheck = this;
        while (toCheck.compareTo(MyBigInteger.ZERO) > 0) {
            if (index == thisBits.length) {
                boolean[] newBits = new boolean[thisBits.length * 2];
                Utils.arrayCopyBoolean(thisBits, 0, newBits, thisBits.length, thisBits.length);
                thisBits = newBits;
            }
            thisBits[thisBits.length - 1 - index] = toCheck.array[toCheck.array.length - 1] % 2 == 1;
            toCheck = toCheck.shiftRightOnce();
            index++;
        }
        thisBits = removeFalseFromBeginning(thisBits);
        return thisBits;
    }

    private boolean[] removeFalseFromBeginning(boolean[] thisBits) {
        for (int i = 0; i <= thisBits.length; i++) {
            if (i == thisBits.length) {
                return new boolean[1];
            }
            if (thisBits[i] == true) {
                boolean[] newBits = new boolean[thisBits.length - i];
                Utils.arrayCopyBoolean(thisBits, i, newBits, 0, newBits.length);
                thisBits = newBits;
                break;
            }
        }
        return thisBits;
    }
}
