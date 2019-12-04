/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.DiffieHellman;

import java.math.BigInteger;

public class dhTest {
    public static void main(String[] args) {
        DiffieHellman dhA, dhB;
        BigInteger A, B, K1, K2;

        DiffieHellman.PG pg = DiffieHellman.genPG();
        dhA = new DiffieHellman(pg);
        A = dhA.calculate_A();
        dhB = new DiffieHellman(pg);
        B = dhB.calculate_A();

        K1 = dhA.calculate_K(B);
        K2 = dhB.calculate_K(A);

        System.out.printf("dhA: %s\ndhB: %s\nK1 == K2: %b", dhA, dhB, K1.compareTo(K2) == 0);
    }
}
