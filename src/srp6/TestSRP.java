package srp6;

import cryptography.utils.primesGenerator.PrimesGenerator;

import java.math.BigInteger;

public class TestSRP {
    private BigInteger N = PrimesGenerator.getSafePrime();
    private BigInteger g = PrimesGenerator.getFirstPrimitiveRoot(N);

    private BigInteger k = BigInteger.valueOf(3);
    private Server server = new Server(N, g, k);

    public static void main(String[] args) {
        String user = "my login", password = "qwerty";
        TestSRP test = new TestSRP();
        test.signUp(user, password);
        test.signIn(user, password);
    }

    private void signUp(String login, String password) {
        System.out.println("Sign Up");

        Client client = new Client(N, g, k, login, password);

        client.signUp();
        String s = client.get_s();
        BigInteger v = client.get_v();
        try {
            server.signUp(login, s, v);
        } catch (IllegalAccessException e) {
            System.err.println("This username is unavailable");
        }
    }

    private void signIn(String login, String password) {
        Client client = new Client(N, g, k, login, password);

        BigInteger A = client.gen_A();
        try {
            server.set_A(A);
        } catch (IllegalAccessException e) {
            System.err.println("Error! A is 0");
            return;
        }

        try {
            String s = server.getClient_s(login);
            BigInteger B = server.gen_B();
            client.set_s_B(s, B);
        } catch (IllegalAccessException e) {
            System.err.println("This username does not exist");
            return;
        }

        try {
            client.gen_u();
            server.gen_u();
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return;
        }

        client.genSessionKey();
        server.genSessionKey();
        BigInteger server_R = server.test_M(client.gen_M());
        if (client.compare_R(server_R))
            System.out.println("Connected");
        else
            System.out.println("Incorrect password");
    }
}