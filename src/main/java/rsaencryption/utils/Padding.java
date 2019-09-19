package rsaencryption.utils;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Class that is used to add padding into plaintext messages.
 */
public class Padding {

    /**
     * Adds OAEP padding to the message. OAEP also hashes the message.
     *
     * Didn't get SHA-256 to work, ignore this.
     *
     * @param message String to be padded.
     * @return OAEP Padded message.
     */
    public static String oaep(String message) {
        int g = 256;
        byte[] r = new byte[2048 / 8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(r);
        String paddedMessage = pad(message, g);
        byte[] messagebytes = paddedMessage.getBytes();
        byte[] gr = SHA256(r);
        byte[] x = new byte[messagebytes.length];
        for (int i = 0; i < messagebytes.length; i++) {
            x[i] = (byte) (messagebytes[i] ^ gr[i]);
        }
        byte[] hmessagebytesgr = SHA256(x);
        byte[] messagebytesgrrh = new byte[messagebytes.length];
        for (int i = 0; i < messagebytes.length; i++) {
            messagebytesgrrh[i] = (byte) (r[i] ^ hmessagebytesgr[i]);
        }
        return new String(messagebytesgrrh);
    }

    private static String pad(String message, int g) {
        int k1 = (g - message.getBytes().length * 8);
        String zeros = "";
        for (int i = 0; i < k1 / 8; i++) {
            zeros += "0";
        }
        String message2 = message + zeros;
        System.out.println("bitSize: " + k1 + "  Zeros bitsize: " + zeros.getBytes().length * 8 + "  Message bitSize: " + message.getBytes().length * 8);
        System.out.println("message with padding: \n" + message2);
        return message2;
    }

    private static byte[] SHA256(byte[] bytes) {
        int[] h0 = {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};

        int[] k = initK();

        byte[][] chunks = bytesIntoChunks(bytes, bytes.length * 8 / 512, 512 / 8);

        for (int i = 0; i < chunks.length; i++) {
            int[] w = new int[64];
            for (int j = 0; j < 16; j++) {
                w[j] = byteArrayToInt(chunks[i], j);
            }

            for (int j = 16; j < 64; j++) {
                int s0 = (rotateRight(w[j - 15], 7)) ^ (rotateRight(w[j - 15], 18)) ^ (w[j - 15] >>> 3);
                int s1 = (rotateRight(w[j - 2], 17)) ^ (rotateRight(w[j - 2], 19)) ^ (w[j - 2] >>> 10);
                w[j] = w[j - 16] + s0 + w[j - 7] + s1;
            }
            doMoreHash(h0, k, w);
        }

        byte[] hash = createHashBytes(h0);

        return hash;
    }

    private static byte[] createHashBytes(int[] h0) {
        byte[] h0bytes = intToByteArray(h0[0]);
        byte[] h1bytes = intToByteArray(h0[1]);
        byte[] h2bytes = intToByteArray(h0[2]);
        byte[] h3bytes = intToByteArray(h0[3]);
        byte[] h4bytes = intToByteArray(h0[4]);
        byte[] h5bytes = intToByteArray(h0[5]);
        byte[] h6bytes = intToByteArray(h0[6]);
        byte[] h7bytes = intToByteArray(h0[7]);

        byte[] hash = new byte[32];

        for (int i = 0; i < 4; i++) {
            hash[0 + i] = h0bytes[i];
            hash[4 + i] = h1bytes[i];
            hash[8 + i] = h2bytes[i];
            hash[12 + i] = h3bytes[i];
            hash[16 + i] = h4bytes[i];
            hash[20 + i] = h5bytes[i];
            hash[24 + i] = h6bytes[i];
            hash[28 + i] = h7bytes[i];
        }
        return hash;
    }

    private static void doMoreHash(int[] h0, int[] k, int[] w) {
        int a = h0[0];
        int b = h0[1];
        int c = h0[2];
        int d = h0[3];
        int e = h0[4];
        int f = h0[5];
        int g = h0[6];
        int h = h0[7];

        hashRotation(a, b, c, d, e, f, g, h, k, w);

        h0[0] += a;
        h0[1] += b;
        h0[2] += c;
        h0[3] += d;
        h0[4] += e;
        h0[5] += f;
        h0[6] += g;
        h0[7] += h;
    }

    private static void hashRotation(int a, int b, int c, int d, int e, int f, int g, int h, int[] k, int[] w) {
        for (int j = 0; j < 64; j++) {
            int S1 = rotateRight(e, 6) ^ rotateRight(e, 11) ^ rotateRight(e, 25);
            int ch = (e & f) | ((~e) & g);
            int temp1 = h + S1 + ch + k[j] + w[j];
            int S0 = rotateRight(a, 2) ^ rotateRight(a, 13) ^ rotateRight(a, 22);
            int maj = (a & b) | (a & c) | (b & c);
            int temp2 = S0 + maj;

            h = g;
            g = f;
            f = e;
            e = d + temp1;
            d = c;
            c = b;
            b = a;
            a = temp1 + temp2;
        }
    }

    private static int[] initK() {
        int[] k = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};
        return k;
    }

    private static byte[][] bytesIntoChunks(byte[] bytes, int firstBytes, int secondBytes) {
        byte[][] chunks = new byte[firstBytes][secondBytes];
        for (int i = 0; i < chunks.length; i++) {
            for (int j = 0; j < chunks[i].length; j++) {
                chunks[i][j] = bytes[i * 512 / 8 + j];
            }
        }
        return chunks;
    }

    private static byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    private static int byteArrayToInt(byte[] b, int startingIndex) {
        int value = 0;
        for (int i = startingIndex; i < startingIndex + 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i] & 0x000000FF) << shift;
        }
        return value;
    }

    private static int rotateRight(int word, int bitsAmount) {
        int tot_bits = 32;
        int c = (word >> bitsAmount) | (word << (tot_bits - bitsAmount));
        return c;
    }

    private static byte[] H(byte[] bytes) {
        return bytes;
    }

}
