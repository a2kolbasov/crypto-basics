package srp6;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

class Hash {
    private static String bytesToHexString(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        System.err.println(hexString.toString());
        return hexString.toString();
    }

    static BigInteger hash(Object... input) {
        try {
            System.err.println(
                    Arrays.toString(Security.getProviders())
            );

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            for (Object i : input) {
                if (i instanceof String) {
                    sha.update(((String) i).getBytes());
                } else if (i instanceof BigInteger) {
                    sha.update(((BigInteger) i).toString(10).getBytes());
                } else if (i instanceof byte[]) {
                    sha.update((byte[]) i);
                } else throw new IllegalArgumentException();
            }
            return new BigInteger(bytesToHexString(sha.digest()), 16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }
}
