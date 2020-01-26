/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.math.BigInteger;
/*
Уязвимость в заранее заданных числах <https://habr.com/ru/post/312634/>
<https://habr.com/ru/post/356870/>
<https://habr.com/ru/post/100950/>
<https://habr.com/ru/post/412779/>
 */

public class PrimesGenerator {
    private static final int
            NUM_BITS = 25, // Безопаснее от 50, но медленно
            ROUNDS = 5;

    public static BigInteger getRandomPrime(){
        return new BigInteger(NUM_BITS, SecRandom.getRandom())
                .nextProbablePrime();
    }

    // (safe_prime - 1) / 2 должно быть случайным простым числом
    public static BigInteger getRandomSafePrime() {
        BigInteger
                prime = getRandomPrime(),
                safePrime;
        do {
            safePrime = prime.multiply(BigInteger.TWO).add(BigInteger.ONE);
            if (PrimalityTest.isProbablePrime(safePrime, ROUNDS))
                return safePrime;
            else
                prime = prime.nextProbablePrime();
        } while (true);
    }

    /**
     * Находит минимальный первообразный корень числа по модулю {@code mod}.
     * Используется упрощённый алгоритм, подходящий только для простых чисел.
     * @param mod модуль (обязательно простое число!)
     * @return первообразный корень по модулю {@code mod}
     */
    public static BigInteger getFirstPrimitiveRoot(final BigInteger mod) {
        BigInteger root = BigInteger.TWO;
        while (! PrimitiveRootSearcher.isPrimitiveRoot(root, mod))
//        while (! isPrimitiveRoot(root, mod))
            root = root.add(BigInteger.ONE);
        return root;
    }

    private static boolean isPrimitiveRoot(final BigInteger root, final BigInteger modulo) {
        // Функция Эйлера
        final BigInteger fi = modulo.subtract(BigInteger.ONE);
        // while (pow < modulo)
        for (BigInteger pow = BigInteger.ONE; pow.compareTo(modulo) < 0; pow = pow.add(BigInteger.ONE)) {
            BigInteger remainder = root.modPow(pow, modulo);
            if (remainder.equals(BigInteger.ONE) && !pow.equals(fi))
                return false;
        }
        return true;
    }

    ////////////////////////////////
    // Пример генерации
    ////////////////////////////////
    private static BigInteger yetOneRandomPrimeGenerator() {
        BigInteger candidate = new BigInteger(NUM_BITS, SecRandom.getRandom());
        // Простое число не кратно 2 ==> младший бит = 1
        candidate = candidate.or(BigInteger.ONE);

        while (! PrimalityTest.isProbablePrime(candidate, ROUNDS))
            // Не кратно 2
            candidate = candidate.add(BigInteger.TWO);
        return candidate;
    }
}
