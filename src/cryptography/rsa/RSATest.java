/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;

import cryptography.DiffieHellman.DiffieHellman;

import java.math.BigInteger;

class RSATest {
    public static void main(String[] args) {
        RSA rsa = new RSA();
        String message = "привет";
        RSA.Key key = rsa.genKey(DiffieHellman.genPG());

        BigInteger encrypted = rsa.encrypt(message, key);

        System.out.println(
                encrypted
        );

        System.out.println(
                rsa.decrypt(encrypted, key)
        );
    }
}
