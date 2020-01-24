/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.tests;

import cryptography.dh.DH;

import java.math.BigInteger;

public class DHTest {
    public static void main(String[] args) {
        DH dhA, dhB;
        BigInteger A, B, K1, K2;

        DH.PG pg = DH.genPG();
        System.out.printf("p: %s, g: %s\n", pg.p, pg.g);

        dhA = new DH(pg);
        A = dhA.genPublicKey();

        dhB = new DH(pg);
        B = dhB.genPublicKey();

        K1 = dhA.genSharedSecretKey(B);
        K2 = dhB.genSharedSecretKey(A);

        System.out.printf("A: %s\nB: %s\nK: %s, K1 == K2 - %b", A, B, K1, K1.compareTo(K2) == 0);
    }
}
