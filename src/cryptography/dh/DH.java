/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.dh;

import cryptography.utils.primesGenerator.PrimesGenerator;

import java.math.BigInteger;

/**
 * Реализация протокола Диффи - Хеллмана для согласования секретного ключа.
 *
 *  Одна из сторон генерирует открытые параметры 'p' и 'g' и передаёт их другой стороне.
 *  Каждая из сторон генерирует секретный ключ, открытый ключ (через 'p', 'g'),
 * посылает другой стороне свой открытый ключ. На основе открытого ключа собеседника и ('p','g')
 * вычисляется общий секретный ключ.
 */
public class DH {

    private BigInteger privateKey;
    // открытые параметры
    private final PG pg;

    /**
     * Data class для храненеия открытых параметров 'p' и 'g'
     * {@code p} - открытое простое число
     * {@code g} - первообразный корень (mod p)
     */
    public static class PG{
        public final BigInteger p, g;
        public PG(BigInteger p, BigInteger g) {
            this.p = p;
            this.g = g;
        }
    }

    /**
     * Генерация открытых параметров 'p' и 'g' для совместного использования со второй стороной
     * @return объёкт класса {@code PG} с заполненными параметрами {@code p} и {@code g}
     */
    public static PG genPG(){
        final BigInteger p, g;
        p = PrimesGenerator.getRandomSafePrime();
        g = PrimesGenerator.getFirstPrimitiveRoot(p);
        return new PG(p, g);
    }

    /**
     * Реализация протокола Диффи - Хеллмана
     * @see #genPG()
     */
    public DH(PG pg){
        this.pg = pg;
    }

    /**
     * Генерирует публичный ключ для передачи второй стороне
     * @return публичный ключ
     */
    public BigInteger genPublicKey(){
        privateKey = genPrivateKey();
        // A = g^a mod p
        return pg.g.modPow(privateKey, pg.p);
    }

    /**
     * Генерирует общий секретный ключ, одинаковый у обоих пользователей
     * @param anotherUserPublicKey публичный ключ другого пользователя
     * @return общий секретный ключ
     */
    public BigInteger genSharedSecretKey(BigInteger anotherUserPublicKey){
        // K = B^a mod p
        return anotherUserPublicKey.modPow(privateKey, pg.p);
    }

    private BigInteger genPrivateKey(){
        // Можно использовать случайное натуральное число, не обязательно простое
        return PrimesGenerator.getRandomPrime();
    }

    public PG getPG() {
        return pg;
    }
}
