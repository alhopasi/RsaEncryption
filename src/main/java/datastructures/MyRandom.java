package datastructures;

public class MyRandom {

    private long m;
    private long a;
    private long c;
    private long x;
    private byte[] array;
    
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
     * @param length How many bytes the created array is.
     * @return Returns an array of random bytes.
     */
    public byte[] nextArray(int length) {
        array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = nextByte();
        }
        return array;
    }
    
    /**
     * 
     * @return 
     */
    public void setSeed(long seed) {
        this.x = seed / 2;
        
    }

    private byte nextByte() {
        int res = 0;
        for (int i = 0; i < 8; i++) {
            x = (a * x + c) % m;
            boolean bit = (x * 1.0 / m > 0.5);
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