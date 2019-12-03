package srp6;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Client {
    private BigInteger N;   // big prime number, modulus
    private BigInteger g;   // generator
    private BigInteger k;   // parameter
    private BigInteger x;   // client's private key
    private BigInteger v;   // verifier
    private BigInteger a;   // client's secret value
    private BigInteger A;   // client's public key
    private BigInteger B;   // server's public key
    private BigInteger u;   // scrambler
    private BigInteger K;   // hash for session key
    private BigInteger M_C; // proof of session key
    private String I;       // login
    private String p;       // password
    private String s;       // salt

    public Client(BigInteger N, BigInteger g, BigInteger k, String I, String p) {
        this.N = N;
        this.g = g;
        this.k = k;
        this.I = I;
        this.p = p;
    }

    public void signUp() {
        // salt
        s = genSalt();
        // x = H(s, p)
        x = Hash.hash(s, p);
        // v = g^x mod N
        v = g.modPow(x, N);
    }

    private String genSalt() {
        final int size = 10;
        final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
        final SecureRandom RANDOM = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public BigInteger gen_A() {
        a = new BigInteger(1024, new SecureRandom());
        // A = g^a mod N
        A = g.modPow(a, N);
        return A;
    }

    public void set_s_B(String s, BigInteger B) {
        this.s = s;
        this.B = B;
    }

    public void gen_u() throws IllegalAccessException {
        // u = H(A, B)
        u = Hash.hash(A, B);
        // u != 0
        assert u.compareTo(BigInteger.ZERO) != 0;
    }

    public void genSessionKey() {
        // x = H(s, p)
        x = Hash.hash(s, p);
        // S = (B - K*(g^x mod N))^(a+u*x)) mod N
        BigInteger S = (B.subtract(k.multiply(g.modPow(x, N)))).modPow(a.add(u.multiply(x)), N);
        // K = H(S)
        K = Hash.hash(S);
    }

    public BigInteger gen_M() {
        // M = H(H(N) xor H(g), H(I), s, A, B, K)
        M_C = Hash.hash(Hash.hash(N).xor(Hash.hash(g)), Hash.hash(I), s, A, B, K);
        return M_C;
    }

    public boolean compare_R(BigInteger R_S) {
        // R = H(A, M, K)
        BigInteger R_C = Hash.hash(A, M_C, K);
        return R_C.equals(R_S);
    }

    public String get_s() {
        return s;
    }

    public BigInteger get_v() {
        return v;
    }
}
