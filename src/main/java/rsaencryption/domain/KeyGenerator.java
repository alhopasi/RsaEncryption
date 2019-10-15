package rsaencryption.domain;

import datastructures.MyBigInteger;
import datastructures.MyPair;
import datastructures.MyRandom;

/**
 * Key generator for RSA public and private keys.
 */
public class KeyGenerator {

    MyRandom random;

    public KeyGenerator() {
        random = new MyRandom();
    }

    /**
     * Generates the public and private keys.
     *
     * @param k = bit-size of the key.
     * @return pair, where key is the PublicKey and value is the PrivateKey.
     */
    public MyPair<PublicKey, PrivateKey> generateKeys(int k) {
        MyBigInteger p;
        MyBigInteger q;
        MyBigInteger n;
        MyBigInteger totient;
        MyBigInteger e = new MyBigInteger("65537");
        MyBigInteger d;

        while (true) {
            p = generatePrime(k / 2);
            q = generatePrime(k - k / 2);
            n = p.multiply(q);
            totient = p.subtract(MyBigInteger.ONE).multiply(q.subtract(MyBigInteger.ONE));
            if (gcd(e, totient).compareTo(MyBigInteger.ONE) == 0) {
                break;
            }
        }
        d = extendedEuclideanAlgorithm(totient, e);
        MyPair<PublicKey, PrivateKey> keys = new MyPair<>(new PublicKey(n, e), new PrivateKey(n, d));
        return keys;
    }

    private MyBigInteger extendedEuclideanAlgorithm(MyBigInteger n, MyBigInteger m) {
        if (m.compareTo(n) > 0) {
            MyBigInteger a = n;
            n = m;
            m = a;
        }
        if (m.compareTo(MyBigInteger.ONE) == 0) {
            return MyBigInteger.ONE;
        }
        MyBigInteger d = MyBigInteger.ONE.add(n.multiply(m.subtract(extendedEuclideanAlgorithm(n.mod(m), m)))).divide(m);
        return d;
    }

    private MyBigInteger gcd(MyBigInteger a, MyBigInteger b) {
        while (b.compareTo(MyBigInteger.ZERO) != 0) {
            MyBigInteger tmp = a;
            a = b;
            b = tmp.mod(b);
        }
        return a;
    }

    private MyBigInteger generatePrime(int bitLength) {

        MyBigInteger prime = generatePositiveInteger(bitLength);
        
        boolean isPrime = true;
        while (true) {
            if (!isPrime) {
                prime = prime.add(new MyBigInteger("2"));
                isPrime = true;
            }
            if (!isPrimeSmallFactorTest(prime)) {
                isPrime = false;
                continue;
            }
            if (!isPrimeMillerRabin(prime, 4)) {
                isPrime = false;
                continue;
            } else {
                return prime;
            }
        }
    }

    private boolean isPrimeSmallFactorTest(MyBigInteger prime) {
        String[] primes
                = {"2", "3", "5", "7", "11", "13", "17", "19", "23", "29", "31", "37", "41", "43",
                    "47", "53", "59", "61", "67", "71", "73", "79", "83", "89", "97", "101", "103", "107",
                    "109", "113", "127", "131", "137", "139", "149", "151", "157", "163", "167", "173", "179", "181",
                    "191", "193", "197", "199", "211", "223", "227", "229", "233", "239", "241", "251"};

        for (int i = 0; i < primes.length; i++) {
            if (prime.mod(new MyBigInteger(primes[i])).compareTo(MyBigInteger.ZERO) == 0) {
                return false;
            }
        }
        return true;
    }

    private MyBigInteger generatePositiveInteger(int bitLength) {
        MyBigInteger prime;

        byte[] bytes = random.nextByteArray(bitLength / 8 + 1);
        bytes[0] = 0x00;
        int res = bytes[bytes.length - 1] | 0b1;
        bytes[bytes.length - 1] = (byte) res;
        prime = new MyBigInteger(bytes);

        return prime;
    }

    private boolean isPrimeMillerRabin(MyBigInteger n, int k) {
        MyBigInteger two = new MyBigInteger("2");

        if (n.compareTo(MyBigInteger.ONE) <= 0 || n.compareTo(new MyBigInteger("4")) == 0 || n.compareTo(new MyBigInteger("3")) <= 0) {
            return false;
        }
        
        MyBigInteger d = n.subtract(MyBigInteger.ONE);
        while (d.mod(two).compareTo(MyBigInteger.ZERO) == 0) {
            d = d.shiftRight(1);
        }
        for (int i = 0; i < k; i++) {
            if (millerRabin(d, n)) {
                return true;
            }
        }
        return false;
    }

    private boolean millerRabin(MyBigInteger d, MyBigInteger n) {
        MyBigInteger a = randomNumberForMillerRabin(n);
        MyBigInteger x = a.modPow(d, n);

        if (x.compareTo(MyBigInteger.ONE) == 0 || x.compareTo(n.subtract(MyBigInteger.ONE)) == 0) {
            return true;
        }
        while (d.compareTo(n.subtract(MyBigInteger.ONE)) != 0) {
            x = x.multiply(x).mod(n);
            d = d.shiftLeft(1);

            if (x.compareTo(MyBigInteger.ONE) == 0) {
                return false;
            }
            if (x.compareTo(n.subtract(MyBigInteger.ONE)) == 0) {
                return true;
            }
        }
        return false;
    }

    private MyBigInteger randomNumberForMillerRabin(MyBigInteger n) {
        MyBigInteger a;
        while (true) {
            a = generatePositiveInteger(n.bitLength());

            if (a.compareTo(n) >= 0 || a.compareTo(MyBigInteger.ONE) <= 0 || a.compareTo(n.subtract(new MyBigInteger("2"))) > 0) {
                continue;
            }
            break;
        }
        return a;
    }
}
