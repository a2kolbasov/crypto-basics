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
    //private static SecureRandom secureRandom = new SecureRandom();
    public static long getRandomPrime(){
        SecureRandom secureRandom = new SecureRandom();
        long randomLong = secureRandom.nextLong();
        // randomLong % 2 != 0
        randomLong |= 0b1;
        do {
            // TODO : SafePrime (p = 2 * q + 1)
            if (MillerRabinTest.isProbablePrime(randomLong, 5))
                return randomLong;
            // randomLong % 2 != 0 ==> +2
            randomLong += 2;
        } while (true);
    }


    public static BigInteger getBigPrime(){
        // TODO: регулировка длинны (младший и старший биты)
        return new BigInteger(10, new SecureRandom()) // 50
                .nextProbablePrime();
    }

    public static BigInteger getSafePrime() {
        BigInteger prime = getBigPrime(), safePrime;
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
