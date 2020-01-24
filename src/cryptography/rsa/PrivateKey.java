/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;

import java.math.BigInteger;

public class PrivateKey {
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

    /**
     * Возвращает только открытые параметры ключа
     * @return открытый ключ
     */
    public PublicKey getPublicKey() {
        return new PublicKey(e, n);
    }
}
