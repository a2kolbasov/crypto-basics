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
    void genKey(DiffieHellman.PG pg){
        BigInteger n = pg.p.multiply(pg.g);
        BigInteger fi =
                pg.p.subtract(BigInteger.ONE)
                .multiply(
                        pg.g.subtract(BigInteger.ONE)
                );

    }
}
