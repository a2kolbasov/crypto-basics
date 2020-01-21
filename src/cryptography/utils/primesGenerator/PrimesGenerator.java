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
    public static long getRandomPrime(){
        SecureRandom secureRandom = new SecureRandom();
        long randomLong = secureRandom.nextLong();
        // randomLong % 2 != 0
        randomLong |= 0b1;
        do {
            // TODO : SafePrime (p = 2 * q + 1)
            if (PrimalityTest.isProbablePrime(randomLong, 5))
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
}
