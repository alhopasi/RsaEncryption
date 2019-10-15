package datastructures;

public class MyRandom {

    private long m;
    private long a;
    private long c;
    private long x;

    /**
     * Class to create random big numbers as byte array.
     */
    public MyRandom() {
        m = 32769;  //modulus, 0 < m
        a = 25173;  // multiplier, 0 < a < m
        c = 13849;  // increment, 0 < c < m
        x = (System.currentTimeMillis() % m) / 2;  // start value (seed)  (clock?)
    }

    /**
     * Used to create a byte array containing random bits.
     *
     * @param length How many bytes the created array is.
     * @return Returns an array of random bytes.
     */
    public byte[] nextByteArray(int length) {
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = nextByte();
        }
        return array;
    }

    /**
     * Used to set seed for tests.
     * @param seed the seed given
     */
    public void setSeed(long seed) {
        this.x = seed / 2;
    }

    private int randomDecimalNotEven() {
        int value = 0;
        while (value % 2 == 0) {
            value = (int) (10.0 * randomValue());
        }
        return value;
    }

    private int randomDecimal() {
        int value = (int) (10.0 * randomValue());
        if (value == 10) {
            value = 9;
        }
        return value;
    }

    private double randomValue() {
        x = (a * x + c) % m;
        return (x * 1.0 / m);
    }

    private byte nextByte() {
        int res = 0;
        for (int i = 0; i < 7; i++) {
            boolean bit = randomValue() > 0.5;
            if (bit) {
                int value = 1;
                for (int j = 0; j < i; j++) {
                    value *= 2;
                }
                res += value;
            }
        }
        return (byte) res;
    }
}
