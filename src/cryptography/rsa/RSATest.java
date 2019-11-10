package cryptography.rsa;

import cryptography.DiffieHellman.DiffieHellman;

import java.math.BigInteger;

class RSATest {
    public static void main(String[] args) {
        RSA rsa = new RSA();
        String message = "привет";
        while (true) {
            RSA.Key key = rsa.genKey(DiffieHellman.genPG());
        }
//        BigInteger encrypted = rsa.encrypt(message, key);
//
//        System.out.println(
//                "encrypted: " +
//                encrypted
//        );
//
//        System.out.println(
//                "decrypted: " +
//                rsa.decrypt(encrypted, key)
//        );
    }
}
