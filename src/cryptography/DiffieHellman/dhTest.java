package cryptography.DiffieHellman;

import java.math.BigInteger;

public class dhTest {
    public static void main(String[] args) {
        DiffieHellman dh1 = new DiffieHellman(), dh2 = new DiffieHellman();

        BigInteger A,B;
        DiffieHellman.PG pg = dh1.genPG();
        A = dh1.initAndGet_A(pg);
        B = dh2.initAndGet_A(pg);

        BigInteger K1, K2;
        K1 = dh1.calculateK(B);
        K2 = dh2.calculateK(A);

        System.out.printf("A:%s, B:%s\nK1:%s\nK2:%s\n\nK is correct - %s", A,B,K1,K2,K1.compareTo(K2) == 0);
    }
}
