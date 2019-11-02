package cryptography.utils.primesGenerator;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import java.math.BigInteger;

public class MillerRabinTest{
    public static boolean isProbablePrime(BigInteger number, int numberOfRounds){
        // TODO : Реализовать алгоритм
        return number.isProbablePrime(numberOfRounds);
    }

    public static boolean isProbablePrime(long number, int numberOfRounds){
        return isProbablePrime(
                BigInteger.valueOf(number), numberOfRounds);
    }
}
