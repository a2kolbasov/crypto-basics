package cryptography.utils.primesGenerator;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import java.math.BigInteger;
import java.security.SecureRandom;

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
        BigInteger prime =
                new BigInteger(50, new SecureRandom())
                        .nextProbablePrime();
        return prime;
    }
}
