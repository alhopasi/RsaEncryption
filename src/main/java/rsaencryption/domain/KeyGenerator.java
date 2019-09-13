package rsaencryption.domain;

import java.math.BigInteger;
import java.util.Random;
import javafx.util.Pair;

/**
 * Key generator for RSA public and private keys.
 */
public class KeyGenerator {

    /**
     * Generates the public and private keys.
     * @param k = bit-size of the key.
     * @return pair, where key is the PublicKey and value is the PrivateKey.
     */
    public Pair<PublicKey, PrivateKey> generateKeys(int k) {
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
        Pair<PublicKey, PrivateKey> keys = new Pair<>(new PublicKey(n, e), new PrivateKey(n, d));
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
            //System.out.println("tmp: " + tmp + "  a: " + a + "  b: " + b);
            a = b;
            b = tmp.mod(b);
        }
        return a;
    }

    private BigInteger generatePrime(int bitLength) {
        return new BigInteger(bitLength, 13, new Random());
    }
    
    /*private BigInteger generatePrime(int bitLength) {

        // Tässä menee jokin pieleen bitLengthin kanssa, muuten tekee kyllä alkuluvun.
        
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[bitLength / 8];
        random.nextBytes(bytes);

        int res = bytes[bytes.length - 1] | 0b1;

        bytes[bytes.length - 1] = (byte) res;

        BigInteger prime = new BigInteger(bytes);

        // if negative, turn into positive:
        if (!prime.testBit(prime.bitLength() - 1)) {
            prime = prime.add(BigInteger.ONE.shiftLeft(prime.bitLength()));
        }

        // Check if prime, increase by 2 if not until is.
        boolean isPrime = true;
        while (true) {
            if (!isPrime) {
                prime = prime.add(BigInteger.valueOf(2));
                isPrime = true;
            }
            if (!prime.isProbablePrime(5)) {
                isPrime = false;
                continue;
            }
            
            if (!isPrime(prime)) {
                isPrime = false;
                continue;
            } else {
                System.out.println("PRIME FOUND: " + prime);
                System.out.println("BitLength: " + prime.bitLength());
                return prime;
            }
        }
    }

    private boolean isPrime(BigInteger n) {
        BigInteger two = new BigInteger("2");
        BigInteger three = new BigInteger("3");
        BigInteger six = new BigInteger("6");

        if (n.compareTo(BigInteger.ONE) <= 0) {
            return false;
        } else if (n.compareTo(three) <= 3) {
            return true;
        } else if (n.mod(three) == three) {
            return false;
        }

        BigInteger i = new BigInteger("5");
        while (i.multiply(i).compareTo(n) <= 0) {
            if (n.mod(i) == BigInteger.ZERO || n.mod(i.add(two)) == BigInteger.ZERO) {
                return false;
            }
            i = i.add(six);
        }
        return true;
    }
     */
}
