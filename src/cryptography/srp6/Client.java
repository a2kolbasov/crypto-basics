/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.srp6;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Client {
    SecureRandom secureRandom = new SecureRandom();
    // <http://srp.stanford.edu/design.html>
    // <https://habr.com/ru/post/121021/>

    BigInteger N;//    A large safe prime (N = 2q+1, where q is prime)
    //All arithmetic is done modulo N.
    BigInteger g;//    A generator modulo N
    BigInteger k = BigInteger.valueOf(3);//    Multiplier parameter (k = H(N, g) in SRP-6a, k = 3 for legacy SRP-6)
    byte[] s;//    User's salt
    String I;//    Username
    String p;//    Cleartext Password
    //H()  One-way hash function
    //^    (Modular) Exponentiation
    //u    Random scrambling parameter
    //a,b  Secret ephemeral values
    //A,B  Public ephemeral values
    BigInteger x;//    Private key (derived from p and s)
    //v    Password verifier


    UserData signUp(String I, String password){
        s = new byte[20];
        secureRandom.nextBytes(s);

        N = BigInteger.probablePrime(50, secureRandom);

        do {
            BigInteger safePrime = N.multiply(BigInteger.TWO).add(BigInteger.ONE);
            if (safePrime.isProbablePrime(5)){
                N = safePrime;
                break;
            }
            safePrime = safePrime.nextProbablePrime();
        } while (true);
///
        g =
        x = Hash.hash(s, password.getBytes());

        BigInteger v = new BigInteger(x).modPow(new BigInteger(x), N);
        this.userData = new UserData(I, v, s);
        return this.userData;
    }

    SignIn1 signIn1(){
        SignIn1 signIn1 = new SignIn1();
        signIn1.I = this.userData.I;
        BigInteger a = BigInteger.valueOf(secureRandom.nextLong());
        signIn1.A = g.modPow(a, N);
        return signIn1;
    }

    static class UserData{
        BigInteger v;
        String I;
        byte[] s;

        public UserData(String I, BigInteger v, byte[] s) {
            this.v = v;
            this.I = I;
            this.s = s;
        }
    }
    static class SignIn1{
        String I; // username
        BigInteger A; // identifies self
    }
}
