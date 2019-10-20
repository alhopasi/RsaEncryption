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
        x = (System.currentTimeMillis() % m) / 2;  // start value (seed)
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
            if (i == 0) {
                array[i] = nextByte(true);
            } else {
                array[i] = nextByte(false);
            }
        }
        return array;
    }

    /**
     * Used to set seed for tests.
     *
     * @param seed the seed given
     */
    public void setSeed(long seed) {
        this.x = seed / 2;
    }

    private double randomValue() {
        x = (a * x + c) % m;
        return (x * 1.0 / m);
    }

    private byte nextByte(boolean onlyPositive) {
        int res = 0;
        int value = 1;
        for (int i = 0; i < 7; i++) {
            boolean bit = randomValue() > 0.5;
            if (bit) {
                res += value;
            }
            value *= 2;
        }
        if (!onlyPositive) {
            boolean bit = randomValue() > 0.5;
            if (bit) {
                res += value;
            }
        }
        return (byte) res;
    }
}
