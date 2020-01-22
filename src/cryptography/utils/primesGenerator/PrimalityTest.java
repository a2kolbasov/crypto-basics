/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

public class PrimalityTest{

    public static boolean isProbablePrime(BigInteger number, int numberOfRounds){
        return number.isProbablePrime(numberOfRounds);
//        return preTest(number) && MillerRabinTest(number, numberOfRounds);
    }

    ////////////////////////////////
    // Всё далее как пример реализации
    ////////////////////////////////

    private static boolean preTest(BigInteger number) {
        // Число не является простым если отрицательно, равно 0 или 1
        if (number.signum() < 1 || number.equals(ONE))
            return false;

        if (number.equals(TWO))
            return true;

        // Составное, если кратно 2 (младший бит != 1)
        return number.testBit(0);
    }

    private static boolean MillerRabinTest(BigInteger p, int rounds) {
        // (p-1) = 2**b * m, b - наибольшее число делений (p-1) на 2, m - оставшаяся нечётная часть, p - проверяемое число
        BigInteger numMinusOne = p.subtract(ONE);
        // (/2) <=> (>> 1)
        int b = numMinusOne.getLowestSetBit();
        BigInteger m = numMinusOne.shiftRight(b);

        for (int round = 0; round < rounds; round++) {
            // Свидетель простоты (1 < a < p)
            BigInteger a;
            do {
                a = new BigInteger(p.bitLength(), SecRandom.getRandom()).mod(p);
            } while (a.compareTo(ONE) <= 0);

            // Должно выполниться хотя бы 1 из условий:
            // 1
            BigInteger z = a.modPow(m, p);
            if (z.equals(ONE))
                continue;
            // 2
            int j = 1;
            while (! z.equals(numMinusOne)) {
                if (j++ >= b) // todo: Должно ли j != 1 (mod p)
                    return false;
                z = z.modPow(TWO, p);
            }
        }

        return true;
    }
}
