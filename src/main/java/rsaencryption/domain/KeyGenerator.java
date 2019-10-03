package rsaencryption.domain;

import java.math.BigInteger;
import datastructures.MyPair;
import datastructures.MyRandom;
import rsaencryption.utils.Utils;

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
        BigInteger p;
        BigInteger q;
        BigInteger n;
        BigInteger totient;
        BigInteger e = new BigInteger("65537");
        BigInteger d;

        while (true) {
            p = generatePrime(k / 2);
            q = generatePrime(k - k / 2);
            n = p.multiply(q);
            totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
            if (gcd(e, totient).compareTo(BigInteger.ONE) == 0) {
                break;
            }
        }
        d = extendedEuclideanAlgorithm(totient, e);
        MyPair<PublicKey, PrivateKey> keys = new MyPair<>(new PublicKey(n, e), new PrivateKey(n, d));
        return keys;
    }

    private BigInteger extendedEuclideanAlgorithm(BigInteger n, BigInteger m) {
        if (m.compareTo(n) > 0) {
            BigInteger a = n;
            n = m;
            m = a;
        }
        if (m.compareTo(BigInteger.ONE) == 0) {
            return BigInteger.ONE;
        }
        BigInteger d = BigInteger.ONE.add(n.multiply(m.subtract(extendedEuclideanAlgorithm(n.mod(m), m)))).divide(m);
        return d;
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        while (b.compareTo(BigInteger.ZERO) != 0) {
            BigInteger tmp = a;
            a = b;
            b = tmp.mod(b);
        }
        return a;
    }

    private BigInteger generatePrime(int bitLength) {

        BigInteger prime = generatePositiveInteger(bitLength);

        boolean isPrime = true;
        while (true) {
            if (!isPrime) {
                prime = prime.add(BigInteger.valueOf(2));
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

    private boolean isPrimeSmallFactorTest(BigInteger prime) {
        int[] primes
                = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
                    47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107,
                    109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181,
                    191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251};

        for (int i = 0; i < primes.length; i++) {
            if (prime.mod(new BigInteger(String.valueOf(primes[i]))).compareTo(BigInteger.ZERO) == 0) {
                return false;
            }
        }
        
        return true;
    }

    private BigInteger generatePositiveInteger(int bitLength) {
        BigInteger prime;

        byte[] bytes = random.nextArray(bitLength / 8 + 1);

        bytes[0] = 0x00;

        int res = bytes[bytes.length - 1] | 0b1;

        bytes[bytes.length - 1] = (byte) res;

        prime = new BigInteger(bytes);

        return prime;
    }

    public boolean isPrimeMillerRabin(BigInteger n, int k) {
        BigInteger two = new BigInteger("2");

        if (n.compareTo(BigInteger.ONE) <= 0 || n.compareTo(new BigInteger("4")) == 0 || n.compareTo(new BigInteger("3")) <= 0) {
            return false;
        }

        BigInteger d = n.subtract(BigInteger.ONE);
        while (d.mod(two) == BigInteger.ZERO) {
            d = d.shiftRight(1);
        }
        for (int i = 0; i < k; i++) {
            if (millerRabin(d, n)) {
                return true;
            }
        }
        return false;
    }

    private boolean millerRabin(BigInteger d, BigInteger n) {
        BigInteger a = randomNumberForMillerRabin(n);
        //BigInteger x = a.modPow(d, n);
        BigInteger x = Utils.powerMod(a, d, n);

        if (x.compareTo(BigInteger.ONE) == 0 || x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
            return true;
        }
        while (d.compareTo(n.subtract(BigInteger.ONE)) != 0) {
            x = x.multiply(x).mod(n);
            d = d.shiftLeft(1);

            if (x.compareTo(BigInteger.ONE) == 0) {
                return false;
            }
            if (x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                return true;
            }
        }
        return false;
    }

    private BigInteger randomNumberForMillerRabin(BigInteger n) {
        BigInteger a;
        while (true) {
            a = generatePositiveInteger(n.bitLength());

            if (a.compareTo(n) >= 0 || a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(n.subtract(new BigInteger("2"))) > 0) {
                continue;
            }
            break;
        }
        return a;
    }
}
