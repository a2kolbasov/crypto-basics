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
        BigInteger a,b,c;
        a = BigInteger.valueOf(7);

        b= BigInteger.valueOf(10);

        c = a.modInverse(b);
        System.out.println(
                c
        );

        int q,w,e;

        q = 3; w = 10;
        System.out.println(RSA.НОД(a, b));
    }
}
// TODO : Вопрос по модульной арифметике, **(-1), расширенный алгоритм Евклида
// https://ru.wikipedia.org/wiki/%D0%A1%D1%80%D0%B0%D0%B2%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5_%D0%BF%D0%BE_%D0%BC%D0%BE%D0%B4%D1%83%D0%BB%D1%8E
// -> Решение сравнений
