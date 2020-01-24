/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.dh;

import java.math.BigInteger;

public class DHTest {
    public static void main(String[] args) {
        DH dhA, dhB;
        BigInteger A, B, K1, K2;

        DH.PG pg = DH.genPG();
        dhA = new DH(pg);
        A = dhA.genPublicKey();
        dhB = new DH(pg);
        B = dhB.genPublicKey();

        K1 = dhA.genSharedSecretKey(B);
        K2 = dhB.genSharedSecretKey(A);

        System.out.printf("dhA: %s\ndhB: %s\nK1 == K2: %b", dhA, dhB, K1.compareTo(K2) == 0);
    }
}
