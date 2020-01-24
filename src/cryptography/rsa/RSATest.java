/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;

import cryptography.dh.DH;

public class RSATest {
    public static void main(String[] args) {
//        RSA rsa = new RSA();

        String message = "Привет, как дела? ☑\n\uD83D\uDD10Нормально!";
//        while (true) { // fixme
//            RSA.Key key = rsa.genKey(DiffieHellman.genPG());
//        }

        RSA.PrivateKey key = RSA.genPrivateKey(DH.genPG());
        String encrypted = RSA.encrypt(message, key.getPublicKey());

        System.out.println(
                "encrypted:\n" +
                        encrypted
        );

        System.out.println(
                "decrypted:\n" +
                        RSA.decrypt(encrypted, key)
        );
    }
}
// TODO : вопрос про static
