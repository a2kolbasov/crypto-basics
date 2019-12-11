/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.math.BigInteger;
import java.util.Random;

public class MillerRabinTest{
    public static boolean isProbablePrime(BigInteger number, int numberOfRounds){
        // TODO : Реализовать алгоритм
        return number.isProbablePrime(numberOfRounds);
    }

    public static boolean isProbablePrime(long number, int numberOfRounds){
        return isProbablePrime(
                BigInteger.valueOf(number), numberOfRounds);
    }

    @SuppressWarnings("Need to test")
    static boolean newTest (BigInteger number, int numberOfRounds) {
        // if (number < 1 || number % 2 == 0)
        if (number.compareTo(BigInteger.ONE) < 0 || number.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            return false;
        // if (number == 1 || number == 2)
        if (number.compareTo(BigInteger.TWO) <= 0)
            return true;

        // представим n − 1 в виде (2^s)·t, где t нечётно
        // это можно сделать последовательным делением n - 1 на 2
        BigInteger t = number.subtract(BigInteger.ONE);
        BigInteger s = BigInteger.ZERO;
        while (t.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            // t /= 2;
            t = t.divide(BigInteger.TWO);
            // s += 1;
            s = s.add(BigInteger.ONE);
        }

        for (int i = 0; i < numberOfRounds; i++) {
            // выберем случайное целое число a в отрезке [2, n − 2]
            BigInteger a = new BigInteger(
                    number.bitLength(), new Random())
                    .mod(number.subtract(
                            BigInteger.valueOf(4)))
                    .add(BigInteger.TWO);
            // x ← a^t mod n, вычислим с помощью возведения в степень по модулю
            BigInteger x = a.modPow(t, number);
            // если x == 1 или x == n − 1, то перейти на следующую итерацию цикла
            if (x.equals(BigInteger.ONE) || x.equals(number.subtract(BigInteger.ONE)))
                continue;
            // повторить s − 1 раз
            for (BigInteger r = BigInteger.ONE; r.compareTo(s) < 0; r = r.add(BigInteger.ONE)) {
                //for (int r = 1; r < s; r++) {
                // x ← x^2 mod n
                x = x.modPow(BigInteger.TWO, number);
                // если x == 1, то вернуть "составное"
                if (x.equals(BigInteger.ONE))
                    return false;
                // если x == n − 1, то перейти на следующую итерацию внешнего цикла
                if (x.equals(number.subtract(BigInteger.ONE)))
                    break;
            }
            if (! x.equals(number.subtract(BigInteger.ONE)))
                return false;
        }
        return true;
    }
}
