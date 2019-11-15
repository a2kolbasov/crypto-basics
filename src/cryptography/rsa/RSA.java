package cryptography.rsa;

import cryptography.DiffieHellman.DiffieHellman;

import java.math.BigInteger;
import java.util.Base64;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

/*
<https://habr.com/ru/post/119637/>
"Хватит использовать RSA" <https://habr.com/ru/company/virgilsecurity/blog/459370/>
RSA для встраиваемых систем (про длинную арифметику) <https://habr.com/ru/post/243425/>
Уязвимости простой реализации <https://habr.com/ru/post/99376/>
<https://habr.com/ru/post/61400/>
*/

public class RSA {
    static Key genKey(DiffieHellman.PG pg){
        // n = p * q
        BigInteger n = pg.p.multiply(pg.g);
        // fi = (p - 1) * (q - 1)
        BigInteger fi =
                pg.p.subtract(BigInteger.ONE)
                    .multiply(
                        pg.g.subtract(BigInteger.ONE));

        // public exponent. 1 < e < fi && coprime with fi
        BigInteger e = BigInteger.valueOf(65537); // TODO : а если не взаимопростое ?
        assert НОД(e, fi).compareTo(BigInteger.ONE) == 0 : String.format("\ne: %s\nfi: %s\nнод (== 1): %s",e,fi,НОД(e,fi));

        // TODO : свой алгоритм поиска d
        // d * e (mod fi) == 1  <==>  d мультипликативно обратно e (mod fi)
        BigInteger d = e.modInverse(fi);
        assert d.multiply(e).mod(fi).compareTo(BigInteger.ONE) == 0;

        return new Key(e, n, d);
    }

    static BigInteger НОД(BigInteger a, BigInteger b) {
        // A > B needed
        if (a.compareTo(b) < 0)
            return НОД(b, a);

        //System.out.printf("A: %s, B: %s\n", a, b);
        if (b.compareTo(BigInteger.ZERO) == 0)
            return a;
        BigInteger mod = a.mod(b);
        return НОД(b, mod);
    }

    String encrypt(String message, Key key){
        Base64.Encoder encoder = Base64.getMimeEncoder();
        StringBuilder encrypted = new StringBuilder();

        for (char c : message.toCharArray()){
            BigInteger cNum = BigInteger.valueOf(c);
            encrypted.append(
                    encoder.encodeToString(
                            cNum.modPow(key.e, key.n)
                                    .toByteArray()));
            encrypted.append("\n");
        }
        return encrypted.toString();
    }

    static String decrypt(String encryptedMessage, Key key){
        Base64.Decoder decoder = Base64.getMimeDecoder();
        StringBuilder decrypted = new StringBuilder();

        String[] messages = encryptedMessage.split("\n");
        for (String message : messages){
            decrypted.append((char)
                    new BigInteger(decoder.decode(message)).modPow(key.d, key.n).intValue()
            );
        }
        return decrypted.toString();
    }

    public static class Key{
        public BigInteger e, n, d;

        public Key(BigInteger e, BigInteger n, BigInteger d) {
            this.e = e;
            this.n = n;
            this.d = d;
        }
    }
}
