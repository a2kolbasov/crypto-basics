/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.rsa;

import java.math.BigInteger;

public class PublicKey {
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
