package srp6;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private BigInteger N;   // big prime number, modulus
    private BigInteger g;   // generator
    private BigInteger k;   // parameter
    private BigInteger v;   // verifier
    private BigInteger A;   // client's public key
    private BigInteger b;   // server's secret value
    private BigInteger B;   // server's public key
    private BigInteger u;   // scrambler
    private BigInteger K;   // hash for session key
    private String I;       // login
    private String s;       // salt
    private Map<String, Pair<String, BigInteger>> database = new HashMap<>();

    public Server(BigInteger N, BigInteger g, BigInteger k) {
        this.N = N;
        this.g = g;
        this.k = k;
    }

    public void signUp(String I, String s, BigInteger v) throws IllegalAccessException {
        if (!database.containsKey(I)) {
            database.put(I, new Pair<>(s, v));
        } else
            throw new IllegalAccessException("User already exist");
    }

    public void set_A(BigInteger A) throws IllegalAccessException {
        // A != 0
        if (A.equals(BigInteger.ZERO))
            throw new IllegalAccessException();
        else
            this.A = A;
    }

    public BigInteger gen_B() {
        b = new BigInteger(1024, new SecureRandom());
        // B = (k*v + g^b mod N) mod N
        B = (k.multiply(v).add(g.modPow(b, N))).mod(N);
        return B;
    }

    public void gen_u() throws IllegalAccessException {
        // u = H(A, B)
        u = Hash.hash(A.toByteArray(), B.toByteArray());
        // u != 0
        if (u.equals(BigInteger.ZERO))
            throw new IllegalAccessException("u == 0");
    }

    public String getClient_s(String I) throws IllegalAccessException {
        if (database.containsKey(I)) {
            this.I = I;
            s = database.get(this.I).first;
            v = database.get(this.I).second;
            return s;
        } else
            throw new IllegalAccessException("Can't find user");
    }

    public void genSessionKey() {
        // S = (A*(v^u mod N))^b mod N
        BigInteger S = A.multiply(v.modPow(u, N)).modPow(b, N);
        // K = H(S)
        K = Hash.hash(S.toByteArray());
        System.err.println("Key: " + K);
    }

    public BigInteger test_M(BigInteger M_C) {
        // M = H(H(N) xor H(g), H(I), s, A, B, K)
        BigInteger M_S = Hash.hash(
                Hash.hash(N.toByteArray())
                        .xor(Hash.hash(g.toByteArray())).toByteArray(),
                Hash.hash(I.getBytes()).toByteArray(),
                s.getBytes(),
                A.toByteArray(),
                B.toByteArray(),
                K.toByteArray()
        );
        // R = H(A, M, K)
        if (M_S.equals(M_C))
            return Hash.hash(A.toByteArray(), M_S.toByteArray(), K.toByteArray());
        else
            return BigInteger.ZERO;
    }

    private class Pair<A, B> {
        A first;
        B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }
}

