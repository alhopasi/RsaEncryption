package datastructures;

import java.util.Arrays;

/**
 * UNDER CONSTRUCTION
 *
 * @author alhopasi
 */
public class MyBigInteger implements Comparable<MyBigInteger> {

    private int[] array;
    private boolean[] bits;

    public static MyBigInteger[] bitsSaved;
    public static final MyBigInteger ONE = new MyBigInteger("1");
    public static final MyBigInteger ZERO = new MyBigInteger("0");

    /**
     * Constructor to create MyBigInteger
     * @param number Value given to MyBigInteger
     */
    public MyBigInteger(String number) {
        array = stringToIntArray(number);
    }

    /**
     * String representation of this MyBigIntegers value
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
     * Gives the MyBigIntegers value as integer.
     * If the value is too big to fit into integer, returns -1.
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
     * @param x MyBigInteger that is added.
     * @return new MyBigInteger with the value of this + x.
     */
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
                biggerIndex--;
                bigger = addOneToBigInteger(bigger, biggerIndex);
            }
        }
        return bigger;
    }

    private static MyBigInteger addOneToBigInteger(MyBigInteger bigInt, int index) {
        while (true) {
            if (index == -1) {
                bigInt = new MyBigInteger("1".concat(bigInt.toString()));
                break;
            }
            if (bigInt.array[index] == 9) {
                bigInt.array[index] = 0;
                index--;
                continue;
            }
            bigInt.array[index] += 1;
            break;
        }
        return bigInt;
    }

    /**
     * Tests if a bit at certain index is positive.
     * Index starts at 0, being the least significant.
     * @param index
     * @return true or false
     */
    public boolean testBit(int index) {
        bits = getBitArray();
        return bits[bits.length - 1 - index];
    }

    public MyBigInteger subtract(MyBigInteger x) {
        MyBigInteger bigger = this.compareTo(x) > 0 ? new MyBigInteger(this.toString()) : new MyBigInteger(x.toString());
        MyBigInteger smaller = this.compareTo(x) > 0 ? x : this;
        int nextInt = 0;
        for (int i = 0; i < smaller.array.length; i++) {
            int biggerIndex = bigger.array.length - i - 1;
            int smallerIndex = smaller.array.length - i - 1;
            checkOverflowForSubtract(bigger.array, smaller.array, biggerIndex, smallerIndex);
            bigger.array[biggerIndex] -= smaller.array[smallerIndex];
            if (bigger.array[biggerIndex] < 0) {
                bigger.array[biggerIndex] += 10;
            }
        }
        while (bigger.array.length > 1 && bigger.array[0] == 0) {
            bigger = new MyBigInteger(bigger.toString().substring(1));
        }
        return bigger;
    }

    private static void checkOverflowForSubtract(int[] biggerArray, int[] smallerArray, int biggerIndex, int smallerIndex) {
        if (biggerArray[biggerIndex] < smallerArray[smallerIndex]) {
            int j = 1;
            while (true) {
                if (biggerArray[biggerIndex - j] == 0) {
                    biggerArray[biggerIndex - j] = 9;
                    j++;
                    continue;
                }
                biggerArray[biggerIndex - j] -= 1;
                break;
            }
        }
    }

    /**
     * Multiplies the MyBigInteger with another one.
     * @param x multiplyer.
     * @return new MyBigInteger, that is the result of multiplication.
     */
    public MyBigInteger multiply(MyBigInteger x) {
        if (x.intValue() > -1 && this.intValue() > -1) {
            return new MyBigInteger(String.valueOf((long) this.intValue() * (long) x.intValue()));
        }
        if (x.intValue() > -1) {
            return multiplyByInteger(this, x.intValue());
        }
        return this.multiplyByBigInts(x);
    }

    private MyBigInteger multiplyByBigInts(MyBigInteger x) {
        if (this.toString().equals("0") || x.toString().equals("0")) {
            return MyBigInteger.ZERO;
        }
        if (this.toString().equals("1")) {
            return x;
        }
        if (x.toString().equals("1")) {
            return this;
        }

        boolean[] thisBits = this.getBitArray();
        boolean[] multiplyer = x.getBitArray();

        String multiplyerString = "";
        String thisString = "";
        for (int i = 0; i < thisBits.length; i++) {
            thisString += thisBits[i] ? 1 : 0;
        }

        String[] sumArray = new String[multiplyer.length];

        for (int i = multiplyer.length - 1; i >= 0; i--) {
            if (multiplyer[i] == false) {
                continue;
            }
            String oneResult = "";
            if (i == multiplyer.length - 1) {
                sumArray[i] = thisString;
                continue;
            }
            for (int j = i; j < multiplyer.length - 1; j++) {
                oneResult += "0";
            }
            oneResult = thisString + oneResult;

            sumArray[i] = oneResult;
        }

        String finalBinaryString = "";
        for (int i = 0; i < sumArray.length; i++) {
            if (sumArray[i] == null) {
                continue;
            }
            if (finalBinaryString.equals("")) {
                finalBinaryString = sumArray[i];
                continue;
            }

            finalBinaryString = binaryAdd(finalBinaryString, sumArray[i]);
        }
        MyBigInteger finalBigInt = binaryStringToBigInteger(finalBinaryString);
        return finalBigInt;
    }

    private static String binaryAdd(String x, String y) {
        for (int i = 0; i < y.length(); i++) {
            int yIndex = y.length() - 1 - i;
            int xIndex = x.length() - 1 - i;
            if (y.charAt(yIndex) == '0') {
                continue;
            }
            
            while (x.charAt(xIndex) == '1') {
                x = x.substring(0, xIndex) + "0" + x.substring(xIndex + 1, x.length());
                if (xIndex == 0) {
                    x = "1" + x;
                    break;
                }
                xIndex--;
            }
            if (xIndex == 0) {
                continue;
            }
            x = x.substring(0, xIndex) + "1" + x.substring(xIndex + 1, x.length());
        }
        return x;
    }

    private MyBigInteger multiplyByInteger(MyBigInteger x, int y) {
        int[] arr = x.array;
        int[] result = new int[x.toString().length() + 32];
        for (int i = arr.length - 1; i >= 0; i--) {
            int value = arr[i] * y;
            String valueString = String.valueOf(value);
            for (int j = valueString.length() - 1; j >= 0; j--) {
                int number = valueString.charAt(j) - 48;
                int correctIndex = result.length - (arr.length - i - 1) - (valueString.length() - j);
                result[correctIndex] += number;
                int indexToCheck = correctIndex;
                while (result[indexToCheck] > 9) {
                    result[indexToCheck] -= 10;
                    result[indexToCheck - 1] += 1;
                    indexToCheck--;
                }
            }
        }
        String finalResult = "";
        for (int i = 0; i < result.length; i++) {
            if (result[i] != 0) {
                for (int j = i; j < result.length; j++) {
                    finalResult += result[j];
                }
                break;
            }
        }
        if (finalResult.equals("")) {
            finalResult = "0";
        }
        return new MyBigInteger(finalResult);
    }

    /**
     * Calculates this mod other, where this and other are MyBigIntegers.
     * @param m The modulo taken from this.
     * @return new MyBigInteger with the modulo result as value.
     */
    public MyBigInteger mod(MyBigInteger m) {
        MyBigInteger divided = this.divide(m);
        return this.subtract(divided.multiply(m));
    }

    /**
     * Divides this MyBigInteger with another one.
     * @param x The divider as MyBigInteger.
     * @return new MyBigInteger with the result as value.
     */
    public MyBigInteger divide(MyBigInteger x) {
        MyBigInteger newBigInt = this;
        int divider = x.intValue();
        if (divider == 0) {
            throw new NumberFormatException("Divider must be 1 or higher");
        }
        if (newBigInt.intValue() > -1 && divider > 0) {
            return new MyBigInteger(String.valueOf(newBigInt.intValue() / x.intValue()));
        }
        String result = "";
        if (divider > 0) {
            result = newBigInt.divideByInteger(divider);
        } else {
            MyBigInteger a = newBigInt.divideByBigIntBits(x);
            return a;
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result = result.substring(1);
        }
        return new MyBigInteger(result);
    }

    private String divideByInteger(int divider) {
        String result = "";
        int[] arr = this.array;
        long tmp = 0;
        for (int i = 0; i < arr.length; i++) {
            tmp += arr[i];
            long value = tmp / divider;
            if (value == 0) {
                tmp = tmp * 10;
                result += 0;
                continue;
            }
            result += value;
            tmp = (tmp - value * divider) * 10;
        }
        return result;
    }

    private MyBigInteger divideByBigIntBits(MyBigInteger divider) {
        boolean[] thisBits = this.getBitArray();
        boolean[] dividerBits = divider.getBitArray();
        if (this.compareTo(divider) < 0) {
            return MyBigInteger.ZERO;
        }
        if (thisBits.length == dividerBits.length) {
            return MyBigInteger.ONE;
        }
        String dividerString = "";
        String thisString = "";
        for (int i = 0; i < dividerBits.length; i++) {
            dividerString += dividerBits[i] ? 1 : 0;
        }
        for (int i = 0; i < thisBits.length; i++) {
            thisString += thisBits[i] ? 1 : 0;
        }
        MyBigInteger dividerAsBit = new MyBigInteger(dividerString);
        String numberToCheck = "";
        String result = "";
        for (int i = 0; i < thisString.length(); i++) {
            numberToCheck += thisString.charAt(i);
            MyBigInteger number = new MyBigInteger(numberToCheck);
            if (number.compareTo(dividerAsBit) >= 0) {
                result += 1;

                for (int j = 0; j < dividerString.length(); j++) {
                    int dividerIndex = dividerString.length() - 1 - j;
                    int numberIndex = numberToCheck.length() - 1 - j;
                    if (dividerString.charAt(dividerIndex) == '1') {
                        if (numberToCheck.charAt(numberIndex) == '1') {
                            numberToCheck = numberToCheck.substring(0, numberIndex) + "0" + numberToCheck.substring(numberIndex + 1, numberToCheck.length());
                            continue;
                        }
                        if (numberToCheck.charAt(numberIndex) == '0') {
                            int tmpIndex = 0;
                            while (true) {
                                if (numberIndex - tmpIndex - 1 < 0) {
                                    tmpIndex--;
                                    break;
                                }
                                numberToCheck = numberToCheck.substring(0, numberIndex - tmpIndex) + "1" + numberToCheck.substring(numberIndex + 1 - tmpIndex, numberToCheck.length());
                                if (numberToCheck.charAt(numberIndex - tmpIndex - 1) == '1') {
                                    break;
                                }
                                tmpIndex++;
                            }
                            tmpIndex++;
                            numberToCheck = numberToCheck.substring(0, numberIndex - tmpIndex) + "0" + numberToCheck.substring(numberIndex + 1 - tmpIndex, numberToCheck.length());
                            continue;
                        }
                    }
                }
            } else {
                result += 0;
            }
            for (int j = 0; j < numberToCheck.length(); j++) {
                if (numberToCheck.charAt(j) == '1') {
                    numberToCheck = numberToCheck.substring(j);
                    break;
                }
            }
        }
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '1') {
                result = result.substring(i, result.length());
                break;
            }
        }
        MyBigInteger finalValue = binaryStringToBigInteger(result);

        return finalValue;
    }

    private static MyBigInteger binaryStringToBigInteger(String x) {
        MyBigInteger finalValue = MyBigInteger.ZERO;
        MyBigInteger bitValue = MyBigInteger.ONE;
        MyBigInteger two = new MyBigInteger("2");
        for (int i = x.length() - 1; i >= 0; i--) {
            if (x.charAt(i) == '1') {
                finalValue = finalValue.add(bitValue);
            }
            bitValue = bitValue.multiply(two);
        }

        return finalValue;
    }

    /**
     * Does shiftRight operation (multiply by 2) x times.
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
        MyBigInteger result = new MyBigInteger(this.toString());
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

    private MyBigInteger shiftRightOnce() {
        MyBigInteger result = new MyBigInteger(this.toString());
        int overFlow = 0;
        for (int i = 0; i < result.array.length; i++) {
            result.array[i] /= 2;
            if (overFlow != 0) {
                result.array[i] += overFlow;
                overFlow = 0;
            }
            if (this.array[i] % 2 != 0) {
                overFlow = 5;
            }
            if (i == result.array.length - 1 && result.array[0] == 0 && result.array.length > 1) {
                result = new MyBigInteger(result.toString().substring(1));
            }
        }
        return result;
    }

    /**
     * Compares this with another MyBigInteger
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

    private boolean[] toBitArray() {
        // DO THIS: Check last digit, if uneven, add 1 to final binary, if even add 0. Do move right (/2). Repeat, adding numbers to left side.
        
        if (bitsSaved == null) {
            bitsSaved = new MyBigInteger[2048];
            MyBigInteger bitToCheck = MyBigInteger.ONE;
            MyBigInteger two = new MyBigInteger("2");
            for (int i = 0; i < bitsSaved.length; i++) {
                bitsSaved[i] = bitToCheck;
                bitToCheck = bitToCheck.multiply(two);
            }
        }
        int index = 0;
        int biggestIndex = 0;
        boolean[] bitArray = new boolean[2048];
        MyBigInteger two = new MyBigInteger("2");
        MyBigInteger integerToCheck = this;
        MyBigInteger bitToCheck = MyBigInteger.ONE;

        for (int i = 0; i < bitsSaved.length; i++) {
            if (integerToCheck.compareTo(bitsSaved[i]) >= 0) {
                index++;
            } else {
                break;
            }
        }

        if (index == 0) {
            return new boolean[1];
        }
        index--;
        biggestIndex = index;

        while (true) {
            if (integerToCheck.compareTo(bitsSaved[index]) >= 0) {
                bitArray[bitArray.length - 1 - index] = true;
                integerToCheck = integerToCheck.subtract(bitsSaved[index]);
            }
            index--;
            if (index < 0) {
                break;
            }
        }

        boolean[] finalBitArray = new boolean[biggestIndex + 1];
        System.arraycopy(bitArray, bitArray.length - 1 - biggestIndex, finalBitArray, 0, finalBitArray.length);

        return finalBitArray;
    }
}
