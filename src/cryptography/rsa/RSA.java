/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;
// Используется из-за одинакового кода
import cryptography.dh.DH;

import java.math.BigInteger;
import java.util.Base64;

/*
<https://habr.com/ru/post/119637/>
"Хватит использовать RSA" <https://habr.com/ru/company/virgilsecurity/blog/459370/>
RSA для встраиваемых систем (про длинную арифметику) <https://habr.com/ru/post/243425/>
Уязвимости простой реализации <https://habr.com/ru/post/99376/>
<https://habr.com/ru/post/61400/>
*/

public class RSA {

    public static PrivateKey genPrivateKey(DH.PG pg){
        // n = p * q
        BigInteger n = pg.p.multiply(pg.g);
        // fi = (p - 1) * (q - 1)
        BigInteger fi =
                pg.p.subtract(BigInteger.ONE)
                        .multiply(
                                pg.g.subtract(BigInteger.ONE));

        // Public exponent, 1 < e < fi && coprime with fi
        // может быть фиксированной <https://tools.ietf.org/html/rfc2313>, <https://www.ietf.org/rfc/rfc4871.txt>
        BigInteger e = BigInteger.valueOf(65537);

        // d * e (mod fi) == 1  <==>  d мультипликативно обратно e (mod fi)
        BigInteger d = e.modInverse(fi);
        assert d.multiply(e).mod(fi).compareTo(BigInteger.ONE) == 0;

        return new PrivateKey(e, n, d);
    }

    static String encrypt(String message, PublicKey key){
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

    static String decrypt(String encryptedMessage, PrivateKey key){
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

    public static class PublicKey {
        public final BigInteger e, n;
        /**
         * Data class для открытого ключа
         * @param e открытая экспонента
         * @param n модуль (n = p * q)
         */
        public PublicKey(BigInteger e, BigInteger n) {
            this.e = e;
            this.n = n;
        }
    }

    public static class PrivateKey {
        public final BigInteger e, n, d;
        /**
         * Data class для закрытого ключа
         * @param d секретная экспонента
         * @param e открытая экспонента
         * @param n модуль (n = p * q)
         */
        public PrivateKey(BigInteger e, BigInteger n, BigInteger d) {
            this.e = e;
            this.n = n;
            this.d = d;
        }
        public PublicKey getPublicKey() {
            return new PublicKey(e, n);
        }
    }
}
