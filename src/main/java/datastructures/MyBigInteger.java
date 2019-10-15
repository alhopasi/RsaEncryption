package datastructures;

import java.util.Arrays;

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
     * Constructor to create MyBigInteger
     *
     * @param bytes should be bytes with value 0-9.
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

    private int[] stringToIntArray(String number) {
        int[] numbers = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            numbers[i] = number.charAt(i) - 48;
        }
        return numbers;
    }

    /**
     * Gives the MyBigIntegers value as integer. If the value is too big to fit
     * into integer, returns -1.
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
        for (int bit = e.bitLength() - 2; bit >= 0; bit--) {
            newBigInt = newBigInt.multiply(newBigInt);
            newBigInt = newBigInt.mod(m);

            if (e.testBit(bit) == true) {
                newBigInt = newBigInt.multiply(this).mod(m);
            }
        }
        
        return newBigInt;
    }

    /**
     * Calculates the sum of this and another MyBigInteger.
     *
     * @param x MyBigInteger that is added.
     * @return new MyBigInteger with the value of this + x.
     */
    public MyBigInteger add(MyBigInteger x) {
        int[] biggerArray;
        int[] smallerArray;
        if (this.array.length < x.array.length) {
            biggerArray = new int[x.array.length + 1];
            System.arraycopy(x.array, 0, biggerArray, 1, x.array.length);
            smallerArray = new int[this.array.length];
            System.arraycopy(this.array, 0, smallerArray, 0, this.array.length);
        } else {
            biggerArray = new int[this.array.length + 1];
            System.arraycopy(this.array, 0, biggerArray, 1, this.array.length);
            smallerArray = new int[x.array.length];
            System.arraycopy(x.array, 0, smallerArray, 0, x.array.length);
        }
        int carry = 0;
        int biggerArrayIndex = 0;
        for (int i = 0; i < smallerArray.length; i++) {
            biggerArrayIndex = biggerArray.length - 1 - i;
            biggerArray[biggerArrayIndex] += carry;
            biggerArray[biggerArrayIndex] += smallerArray[smallerArray.length - 1 - i];
            if (biggerArray[biggerArrayIndex] > 9) {
                carry = 1;
                biggerArray[biggerArrayIndex] -= 10;
            } else {
                carry = 0;
            }
        }
        biggerArrayIndex--;
        while (carry == 1) {
            biggerArray[biggerArrayIndex] += 1;
            if (biggerArray[biggerArrayIndex] > 9) {
                carry = 1;
                biggerArray[biggerArrayIndex] -= 10;
            } else {
                carry = 0;
            }
            biggerArrayIndex--;
        }
        int[] finalArray = biggerArray;
        for (int i = 0; i < biggerArray.length; i++) {
            if (biggerArray[i] != 0) {
                if (i == 0) {
                    break;
                }
                finalArray = new int[biggerArray.length - i];
                System.arraycopy(biggerArray, i, finalArray, 0, finalArray.length);
                break;
            }
            if (i == biggerArray.length - 1) {
                finalArray = new int[1];
                finalArray[0] = biggerArray[biggerArray.length - 1];
            }
        }
        return new MyBigInteger(finalArray);
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
        int[] biggerArray;
        int[] smallerArray;
        if (this.array.length < x.array.length) {
            biggerArray = new int[x.array.length + 1];
            System.arraycopy(x.array, 0, biggerArray, 1, x.array.length);
            smallerArray = new int[this.array.length];
            System.arraycopy(this.array, 0, smallerArray, 0, this.array.length);
        } else {
            biggerArray = new int[this.array.length + 1];
            System.arraycopy(this.array, 0, biggerArray, 1, this.array.length);
            smallerArray = new int[x.array.length];
            System.arraycopy(x.array, 0, smallerArray, 0, x.array.length);
        }
        int carry = 0;
        int biggerArrayIndex = 0;
        for (int i = 0; i < smallerArray.length; i++) {
            biggerArrayIndex = biggerArray.length - 1 - i;
            biggerArray[biggerArrayIndex] -= carry;
            biggerArray[biggerArrayIndex] -= smallerArray[smallerArray.length - 1 - i];
            if (biggerArray[biggerArrayIndex] < 0) {
                carry = 1;
                biggerArray[biggerArrayIndex] += 10;
            } else {
                carry = 0;
            }
        }
        biggerArrayIndex--;
        while (carry == 1) {
            biggerArray[biggerArrayIndex] -= 1;
            if (biggerArray[biggerArrayIndex] < 0) {
                carry = 1;
                biggerArray[biggerArrayIndex] += 10;
            } else {
                carry = 0;
            }
            biggerArrayIndex--;
        }
        int[] finalArray = removeZerosFromBeginning(biggerArray);

        return new MyBigInteger(finalArray);
    }

    private int[] removeZerosFromBeginning(int[] array) {
        int[] finalArray = array;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                if (i == 0) {
                    break;
                }
                finalArray = new int[array.length - i];
                System.arraycopy(array, i, finalArray, 0, finalArray.length);
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
        int[] biggerArray;
        int[] smallerArray;
        if (this.array.length < x.array.length) {
            biggerArray = new int[x.array.length];
            System.arraycopy(x.array, 0, biggerArray, 0, x.array.length);
            smallerArray = new int[this.array.length];
            System.arraycopy(this.array, 0, smallerArray, 0, this.array.length);
        } else {
            biggerArray = new int[this.array.length];
            System.arraycopy(this.array, 0, biggerArray, 0, this.array.length);
            smallerArray = new int[x.array.length];
            System.arraycopy(x.array, 0, smallerArray, 0, x.array.length);
        }
        MyBigInteger bigger = new MyBigInteger(biggerArray);
        int[] result = new int[smallerArray.length + biggerArray.length + 1];
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
        result = removeZerosFromBeginning(result);
        return new MyBigInteger(result);
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
        int compareResult = this.compareTo(x);
        if (compareResult < 0) {
            return MyBigInteger.ZERO;
        } else if (compareResult == 0) {
            return MyBigInteger.ONE;
        }
        int[] thisCopy = new int[this.array.length];
        System.arraycopy(this.array, 0, thisCopy, 0, this.array.length);
        MyBigInteger dividend = new MyBigInteger(thisCopy);
        int thisBeginIndex = 0;
        int thisEndIndex = 1;
        int[] result = new int[this.array.length];
        while (true) {
            
            
            if (thisEndIndex > this.array.length) {
                break;
            }
            int[] longDividerArray = new int[thisEndIndex - thisBeginIndex];
            System.arraycopy(dividend.array, thisBeginIndex, longDividerArray, 0, thisEndIndex - thisBeginIndex);
            MyBigInteger longDividend = new MyBigInteger(longDividerArray);
            int times = 0;
            if (longDividend.compareTo(x) >= 0) {
                while (longDividend.compareTo(x) >= 0) {
                    longDividend = longDividend.subtract(x);
                    times++;
                }
                if (longDividend.compareTo(MyBigInteger.ZERO) == 0) {
                    thisBeginIndex = thisEndIndex;
                } else {
                    int digitsLeftFromSubtract = longDividend.array.length;
                    thisBeginIndex = thisEndIndex - digitsLeftFromSubtract;
                    System.arraycopy(longDividend.array, 0, dividend.array, thisBeginIndex, digitsLeftFromSubtract);
                }
            }
            result[thisEndIndex - 1] = times;
            thisEndIndex++;

        }
        result = removeZerosFromBeginning(result);
        return new MyBigInteger(result);
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
        int[] newArray;
        boolean bit = false;
        if (this.array[0] >= 5) {
            newArray = new int[this.array.length + 1];
            System.arraycopy(this.array, 0, newArray, 1, this.array.length);
        } else {
            newArray = new int[this.array.length];
            System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        }
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

    private MyBigInteger shiftRightOnce() {
        int[] newArray;
        boolean bit = false;
        if (this.array[0] == 1) {
            if (this.array.length == 1) {
                return MyBigInteger.ZERO;
            }
            newArray = new int[this.array.length - 1];
            System.arraycopy(this.array, 1, newArray, 0, newArray.length);
            bit = true;
        } else {
            newArray = new int[this.array.length];
            System.arraycopy(this.array, 0, newArray, 0, this.array.length);
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

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

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
     * Compares this with another MyBigInteger
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

    public boolean[] toBitArray() {
        String thisBits = "";
        MyBigInteger toCheck = this;
        while (toCheck.compareTo(MyBigInteger.ZERO) > 0) {
            if (toCheck.array[toCheck.array.length - 1] % 2 == 1) {
                thisBits = "1" + thisBits;
            } else {
                thisBits = "0" + thisBits;
            }
            toCheck = toCheck.shiftRightOnce();
        }
        boolean[] bitsToReturn = new boolean[thisBits.length()];
        for (int i = 0; i < thisBits.length(); i++) {
            bitsToReturn[i] = thisBits.charAt(i) == '1';
        }
        return bitsToReturn;
    }
}
