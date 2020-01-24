/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;

import cryptography.utils.primesGenerator.PrimesGenerator;

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
    /**
     * Генерация закрытого ключа
     * @return сгенерированный ключ
     */
    public static PrivateKey genPrivateKey(){
        BigInteger
                p = PrimesGenerator.getRandomPrime(),
                q = PrimesGenerator.getRandomPrime();
        // n = p * q
        BigInteger n = p.multiply(q);
        // fi = (p - 1) * (q - 1)
        BigInteger fi =
                p.subtract(BigInteger.ONE)
                        .multiply(
                                q.subtract(BigInteger.ONE));

        // Public exponent, 1 < e < fi && coprime with fi
        // может быть фиксированной <https://tools.ietf.org/html/rfc2313>, <https://www.ietf.org/rfc/rfc4871.txt>
        BigInteger e = BigInteger.valueOf(65537);
        // Private exponent, d * e (mod fi) == 1  <==>  d мультипликативно обратно e (mod fi)
        BigInteger d = e.modInverse(fi);

        return new PrivateKey(e, n, d);
    }

    public static String encrypt(String message, PublicKey key){
        Base64.Encoder encoder = Base64.getMimeEncoder();
        StringBuilder encrypted = new StringBuilder();
        // Посимвольное шифрование
        for (char chr : message.toCharArray()){
            BigInteger chrNum = BigInteger.valueOf(chr);
            encrypted.append(
                    encoder.encodeToString(
                            chrNum.modPow(key.e, key.n)
                                    .toByteArray()));
            encrypted.append('&');
        }
        return encrypted.toString();
    }

    public static String decrypt(String encryptedMessage, PrivateKey key){
        Base64.Decoder decoder = Base64.getMimeDecoder();
        StringBuilder decrypted = new StringBuilder();

        String[] messages = encryptedMessage.split("[&]");
        for (String message : messages){
            decrypted.append((char)
                    new BigInteger(decoder.decode(message)).modPow(key.d, key.n).intValue()
            );
        }
        return decrypted.toString();
    }
}
