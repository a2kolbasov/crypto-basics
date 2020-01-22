/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.utils.primesGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class SecRandom {
    private static java.security.SecureRandom random;

    static java.security.SecureRandom getRandom() {
        if (random == null) {
            try {
                random = SecureRandom.getInstanceStrong();
            } catch (NoSuchAlgorithmException e) {
                random = new SecureRandom();
            }
        }

        return random;
    }
}
