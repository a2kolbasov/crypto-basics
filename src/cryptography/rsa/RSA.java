package cryptography.rsa;

import cryptography.DiffieHellman.DiffieHellman;

import java.math.BigInteger;

/*
<https://habr.com/ru/post/119637/>
<https://habr.com/ru/company/virgilsecurity/blog/459370/>
RSA для встраиваемых систем (про длинную арифметику) <https://habr.com/ru/post/243425/>
Уязвимости простой реализации <https://habr.com/ru/post/99376/>
<https://habr.com/ru/post/61400/>
*/

public class RSA {
    Key genKey(DiffieHellman.PG pg){
        BigInteger n = pg.p.multiply(pg.g);
        BigInteger fi =
                pg.p.subtract(BigInteger.ONE)
                .multiply(
                        pg.g.subtract(BigInteger.ONE)
                );
        BigInteger e = BigInteger.valueOf(17);

        BigInteger d =
                BigInteger.ONE.divide(e).mod(fi);
        //e.pow(-1).mod(fi); // Negative exponent
        System.out.println("d: " + d);

        return new Key(e, n, d);
    }

    BigInteger encrypt(String message, Key key){
        BigInteger[] arr = new BigInteger[message.length()];
        BigInteger bi = new BigInteger(message.getBytes());
        System.out.println(bi);
        BigInteger encrypted = bi.modPow(key.d, key.n);
        return encrypted;
        //return null;
    }

    String decrypt(BigInteger encrypted, Key key){
        BigInteger decrypted = encrypted.modPow(key.d, key.n);
        return new String(decrypted.toByteArray());
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
