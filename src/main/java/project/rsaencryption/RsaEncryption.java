
package project.rsaencryption;

public class RsaEncryption {
    
    public static void main(String[] args) {
        
        int bitLength = 2048;
        bitLength = 200;
        
        KeyGenerator keyGen = new KeyGenerator();
        keyGen.generateKeys(bitLength);
    }
}
