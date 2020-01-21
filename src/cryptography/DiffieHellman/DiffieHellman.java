package cryptography.DiffieHellman;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import cryptography.utils.primesGenerator.PrimesGenerator;

import java.math.BigInteger;

public class DiffieHellman {
    public static class PG{
        public BigInteger p,g;

        public PG(BigInteger p, BigInteger g) {
            this.p = p;
            this.g = g;
        }
    }

    // TODO : BigInteger
    private BigInteger p,g,a, A, K;

    public BigInteger initAndGet_A(PG pg){
        p = pg.p;
        g = pg.g;

        a = gen_a();
        System.out.println("p " + p + " g " + g + " a " + a);

        // A = g^a mod p
        A = g.modPow(a, p);
        //A = ((long) Math.pow(g, a)) % p;
        System.out.println(" A = " + A);
        return A;
    }

    public BigInteger calculateK(BigInteger B){
        // K = B^a mod p
        // K = ((long) Math.pow(B, a)) % p;
        K = B.modPow(a, p);
        System.out.println("K = " + K);
        return K;
    }

    public PG genPG(){
        p = PrimesGenerator.getBigPrime();
        // Временно
        do {
            g = PrimesGenerator.getBigPrime();
        } while (g.compareTo(p) < 0);// while g < p //(g.max(p).equals(g));
        // TODO : Проверка, что g -- первообразный корень
        return new PG(p, g);
    }

    /*boolean isPrimitiveRoot(BigInteger number, BigInteger modulo){
        // TODO
        throw null;
    }*/
    BigInteger gen_a(){
        return PrimesGenerator.getBigPrime();
    }
}
