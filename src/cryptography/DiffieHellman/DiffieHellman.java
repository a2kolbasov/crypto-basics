package cryptography.DiffieHellman;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

import cryptography.utils.primesGenerator.PrimesGenerator;
import jdk.jfr.Experimental;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    private BigInteger a, A, K;
    private final BigInteger p, g;

    public static class PG{
        public BigInteger p, g;
        public PG(BigInteger p, BigInteger g) {
            this.p = p;
            this.g = g;
        }
    }

    public static PG genPG(){
        BigInteger p, g;
        // TODO : (p-1)/2 должно быть случайным простым числом (Safe prime)
        p = PrimesGenerator.getBigPrime();
        // Временно
        // TODO : Проверка, что g -- первообразный корень
        do {
            g = PrimesGenerator.getBigPrime();
        } while (p.compareTo(g) <= 0); // while p <= g
        return new PG(p, g);
    }

    public DiffieHellman(BigInteger p, BigInteger g) {
        this.p = p;
        this.g = g;
    }
    public DiffieHellman(PG pg){
        this.p = pg.p;
        this.g = pg.g;
    }
    public BigInteger calculate_A(){
        // TODO : в конструктор или двойное вычисление
        a = gen_a();
        // A = g^a mod p
        A = g.modPow(a, p);
        return A;
    }

    public BigInteger calculate_K(BigInteger B){
        if (K != null)
            return K;
        // K = B^a mod p
        K = B.modPow(a, p);
        return K;
    }

//    boolean isPrimitiveRoot(BigInteger number, BigInteger modulo){
//        // TODO
//        throw new RuntimeException();
//    }

    private BigInteger gen_a(){
        // TODO : случайное натуральное число
        return PrimesGenerator.getBigPrime();
    }

    @Override
    public String toString(){
        return String.format("p: %s, g: %s, A: %s, K: %s", p, g, A, K);
    }
}
