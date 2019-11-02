package cryptography.utils.primesGenerator;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import java.security.SecureRandom;

public class PrimesGenerator {
    public static long getRandomPrime(){
        do {
            SecureRandom secureRandom = new SecureRandom();
            long randomLong = secureRandom.nextLong();
            if (MillerRabinTest.isProbablePrime(randomLong, 5))
                return randomLong;
        } while (true);
    }
}
