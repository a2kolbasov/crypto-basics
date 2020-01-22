/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.math.BigInteger;

public class PrimalityTest{
    public static boolean isProbablePrime(BigInteger number, int numberOfRounds){
        // TODO : Реализовать алгоритм
        return number.isProbablePrime(numberOfRounds);
    }
}
