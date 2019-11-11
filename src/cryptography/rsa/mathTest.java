package cryptography.rsa;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import cryptography.DiffieHellman.DiffieHellman;

import java.math.BigInteger;

public class mathTest {
    public static void main(String[] args) {
//        BigInteger нод = RSA.НОД(BigInteger.valueOf(462), BigInteger.valueOf(1071));
//        System.out.println(нод);

        BigInteger b;
        BigInteger e = BigInteger.valueOf(65537);
        RSA.Key key = RSA.genKey(DiffieHellman.genPG());
        String m = "💰";
        StringBuilder sb = new StringBuilder();

        for (char c : m.toCharArray()){
            b = BigInteger.valueOf(c);
            BigInteger encrypted = b.modPow(key.e, key.n);
            BigInteger decrypted = encrypted.modPow(key.d, key.n);
            System.out.printf("char: %c (%d), en: %s, d: %s\n", c, (int) c, encrypted, decrypted);
            sb.append(encrypted.toString(16));
            sb.append(' ');
        }
        System.out.println(sb);

        String[] sa = sb.toString().split(" ");
        sb = new StringBuilder();
        for (String s :sa){
            System.out.println(s);
            BigInteger decrypted = new BigInteger(s, 16);
            System.out.println(decrypted);
            decrypted = decrypted.modPow(key.d, key.n);
            System.out.println(decrypted);
            sb.append(
                    (char) decrypted.intValueExact()
            );
        }
        System.out.println("Итог: " + sb);
    }
}
