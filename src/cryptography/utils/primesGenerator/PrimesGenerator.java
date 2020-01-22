/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
/*
Уязвимость в заранее заданных числах <https://habr.com/ru/post/312634/>
<https://habr.com/ru/post/356870/>
<https://habr.com/ru/post/100950/>
<https://habr.com/ru/post/412779/>
 */

public class PrimesGenerator {
    public static BigInteger getRandomPrime(){
        // TODO: регулировка длинны (младший и старший биты)
        return new BigInteger(15, new SecureRandom()) // Безопаснее от 50, но медленно
                .nextProbablePrime();
    }

    // (safe_prime - 1) / 2 должно быть случайным простым числом
    public static BigInteger getRandomSafePrime() {
        BigInteger prime = getRandomPrime(), safePrime;
        do {
            safePrime = prime.multiply(BigInteger.TWO).add(BigInteger.ONE);
            if (safePrime.isProbablePrime(5))
                return safePrime;
            else
                prime = prime.nextProbablePrime();
        } while (true);
    }

    public static BigInteger getFirstPrimitiveRoot(final BigInteger mod) {
        BigInteger root = BigInteger.TWO;
        while (! isPrimitiveRoot(root, mod))
            root = root.add(BigInteger.ONE);
        return root;
    }

    private static boolean isPrimitiveRoot(final BigInteger number, final BigInteger modulo) {
        HashSet<BigInteger> set = new HashSet<>();
        // while (pow < modulo)
        for (BigInteger pow = BigInteger.ONE; pow.compareTo(modulo) < 0; pow = pow.add(BigInteger.ONE)) {
            BigInteger remainder = number.modPow(pow, modulo);
            if (set.contains(remainder))
                return false;
            set.add(remainder);
        }
        return true;
    }
}
