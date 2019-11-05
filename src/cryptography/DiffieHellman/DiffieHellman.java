package cryptography.DiffieHellman;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import cryptography.utils.primesGenerator.PrimesGenerator;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    public static class PG{
        public final long p,g;

        public PG(long p, long g) {
            this.p = p;
            this.g = g;
        }
    }

    // TODO : BigInteger
    private long p,g,a, A, K;

    public long initAndGet_a(PG pg){
        //p = pg.p;
        //g = pg.g;
        p = 7; g = 2;

        a = gen_a();
        System.out.printf("p " + p + " g " + g + " a " + a);

        // A = g^a % p
        A = ((long) Math.pow(g, a)) % p;
        System.out.println(" A = " + A);
        return A;
    }

    public long calculateK(long B){
        K = ((long) Math.pow(B, a));
        System.out.println("K = " + K);
        K = K % p;
        System.out.println("K = " + K);

        return K;
    }

    public PG genPG(){
        // Временно // TODO : через PrimesGen
        p = PrimesGenerator.getRandomPrime();
        g = PrimesGenerator.getRandomPrime();
        // TODO : Проверка, что g -- первообразный корень
        return new PG(p, g);
    }

    boolean isPrimitiveRoot(BigInteger number, BigInteger modulo){
        // TODO
        throw null;
    }
    long gen_a(){
        return PrimesGenerator.getRandomPrime();
    }
}
