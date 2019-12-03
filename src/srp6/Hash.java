package srp6;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

class Hash {
    static BigInteger hash(byte[]... byteArrays) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            for (byte[] bytes : byteArrays)
                sha.update(bytes);

//            return new BigInteger(bytesToHexString(sha.digest()), 16);
            byte[] hash = sha.digest();
            return new BigInteger(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }
}
