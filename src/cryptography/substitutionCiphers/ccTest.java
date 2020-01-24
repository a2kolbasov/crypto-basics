/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.substitutionCiphers;

import cryptography.utils.Alphabets;
import cryptography.utils.FileReader;

import java.io.IOException;

public class ccTest {
    public static void main(String[] args) throws IOException {
        /*
         * Проверка работы шифра Цезаря
         */
        String message = FileReader.read("глава.txt");
        CaesarCipher cc = new CaesarCipher(Alphabets.RUSSIAN, 3);

        String encryptedMessage = cc.encrypt(message);
        String decrypted = cc.decrypt(encryptedMessage);

        assert message.equals(decrypted);
        System.out.println("Encrypted:\n" + encryptedMessage);
        System.out.println("Decrypted:\n" + decrypted);

        /*
         * Частотный анализ
         */
        FrequencyAnalysis analysis = new FrequencyAnalysis(Alphabets.RUSSIAN);
        String referenceText = FileReader.read("война_и_мир.txt");
        System.out.println("Frequency analysis:\n" +
                analysis.decryptText(encryptedMessage, referenceText, 1)
        );
    }
}
