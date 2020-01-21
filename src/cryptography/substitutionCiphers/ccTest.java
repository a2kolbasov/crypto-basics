/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.substitutionCiphers;

import cryptography.utils.Alphabets;
import cryptography.utils.FileReader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ccTest {
    public static void main(String[] args) throws IOException {
        /*
         * Проверка работы шифра Цезаря
         */
        String message = FileReader.readAll("глава.txt");
        CaesarCipher cc = new CaesarCipher(Alphabets.RUSSIAN, 3);

        String encryptedMessage = cc.encrypt(message);
        String decrypted = cc.decrypt(encryptedMessage);

        assert message.equals(decrypted);
        System.out.println(encryptedMessage);
        System.out.println(decrypted);

        FrequencyAnalysis analysis = new FrequencyAnalysis(Alphabets.RUSSIAN);

        /*
         * Частотный анализ
         */
        String referenceText = FileReader.readAll("война_и_мир.txt");
        System.out.println(
                analysis.decryptText(encryptedMessage, referenceText, 1)
        );
    }
}
