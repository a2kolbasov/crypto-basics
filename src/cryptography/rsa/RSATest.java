/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;

public class RSATest {
    public static void main(String[] args) {
        String message = "Привет, как дела? ☑\n\uD83D\uDD10Нормально!";

        PrivateKey key = RSA.genPrivateKey();
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
