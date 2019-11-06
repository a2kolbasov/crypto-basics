package cryptography.rsa;

import cryptography.DiffieHellman.DiffieHellman;

import java.math.BigInteger;

public class RSA {
    void genKey(DiffieHellman.PG pg){
        BigInteger n = pg.p.multiply(pg.g);
        BigInteger fi =
                pg.p.subtract(BigInteger.ONE)
                .multiply(
                        pg.g.subtract(BigInteger.ONE)
                );

    }
}
