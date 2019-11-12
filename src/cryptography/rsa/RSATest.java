package cryptography.rsa;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import cryptography.DiffieHellman.DiffieHellman;

public class RSATest {
    public static void main(String[] args) {
        RSA rsa = new RSA();

        String message = "Привет, как дела? ☑\n🔐";
//        while (true) {
//            RSA.Key key = rsa.genKey(DiffieHellman.genPG());
//        }

        RSA.Key key = RSA.genKey(DiffieHellman.genPG());
        String encrypted = rsa.encrypt(message, key);

        System.out.println(
                "encrypted:\n" +
                encrypted
        );

        System.out.println(
                "decrypted:\n" +
                rsa.decrypt(encrypted, key)
        );
    }
}
// TODO : вопрос про static
