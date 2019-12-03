package srp6;

import java.math.BigInteger;
import java.util.Scanner;

public class TestSRP {
    // N generated using "openssl dhparam -text 1024 -2",
    // where g equals 2
    private String N_hex = "00:f5:0a:08:ad:ff:22:47:58:12:37:db:d3:e8:fc:" +
            "84:4a:f9:50:fb:7c:34:fc:c1:73:29:fa:15:e1:ee:" +
            "15:ae:a9:3f:bb:23:bb:d3:88:22:cc:44:cb:a3:28:" +
            "dc:db:19:b2:fa:b3:72:f9:f9:8a:05:f7:16:4a:39:" +
            "90:19:d4:ee:ae:cd:c7:38:ee:19:22:61:0f:00:91:" +
            "6b:0c:23:18:e5:05:fb:7d:34:4d:fe:22:7e:92:59:" +
            "3d:6f:c1:91:d0:1f:37:b8:41:65:2f:76:54:8f:ab:" +
            "e0:19:93:b3:a6:13:c8:c3:50:2a:5d:13:52:61:8c:" +
            "6c:e2:90:14:41:66:8b:61:5b";
    private BigInteger N = new BigInteger(N_hex.replace(":", ""), 16);
    private BigInteger g = BigInteger.TWO;
    private BigInteger k = BigInteger.valueOf(3);
    private Server server = new Server(N, g, k);
    private Scanner in;
    private String qq;

    public TestSRP(Scanner in) {
        this.in = in;
    }

    public static void main(String[] args) {
        TestSRP main = new TestSRP(new Scanner(System.in));
        main.signUp();
        main.signIn();
    }



    private void signUp() {
        System.out.println("Enter login: ");
        String login = in.next();

        System.out.println("Enter password: ");
        String password = in.next();

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

    private void signIn() {
        System.out.println("Enter login: ");
        String login = in.next();

        System.out.println("Enter password: ");
        String password = in.next();

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