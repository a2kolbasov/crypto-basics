package cryptography.srp6;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.SecureRandom;
/*
 * Copyright © 2019 Alexander Kolbasov
 */

public class Client {
    // <http://srp.stanford.edu/design.html>
    // <https://habr.com/ru/post/121021/>

    void signUp(String password){
        SecureRandom secureRandom = new SecureRandom();
        byte[] s = new byte[20];
        secureRandom.nextBytes(s);

        BigInteger N = BigInteger.probablePrime(50, secureRandom);
        do {
            BigInteger safePrime = N.multiply(BigInteger.TWO).add(BigInteger.ONE);
            if (safePrime.isProbablePrime(5)){
                N = safePrime;
                break;
            }
            safePrime = safePrime.nextProbablePrime();
        } while (true);

        byte[] x = Hash.hash(s, password.getBytes());

        BigInteger v = new BigInteger(x).modPow(new BigInteger(x), N);
    }

    void signIn(){
    }

    static class UserData{
        BigInteger v;
        String s, I;
    }
}
