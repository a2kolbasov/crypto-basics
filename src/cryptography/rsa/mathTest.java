package cryptography.rsa;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import java.math.BigInteger;

public class mathTest {
    public static void main(String[] args) {
        BigInteger нод = RSA.НОД(BigInteger.valueOf(462), BigInteger.valueOf(1071));
        System.out.println(нод);
    }
}
