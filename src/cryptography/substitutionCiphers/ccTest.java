package cryptography.substitutionCiphers;

import cryptography.substitutionCiphers.CaesarCipher;
import cryptography.substitutionCiphers.FrequencyAnalysis;
import cryptography.utils.Alphabets;
import cryptography.utils.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

public class ccTest {
    public static void main(String[] args) throws IOException {
        String message = FileReader.readAll("глава.txt");

        CaesarCipher cc = new CaesarCipher(Alphabets.RUSSIAN, 2);

        String encryptedMessage = cc.encrypt(message);
        String decrypted = cc.decrypt(encryptedMessage);
        System.out.println(encryptedMessage);
        System.out.println("message == decryptedMessage " + message.equals(decrypted));
        FrequencyAnalysis f = new FrequencyAnalysis(Alphabets.RUSSIAN);
        LinkedList<String> mes, enMes;
        mes= f.toFrequencyMap(FileReader.readAll("война_и_мир.txt"), 1);
        enMes = f.toFrequencyMap(encryptedMessage, 1);

//        System.out.println(mes);
//        System.out.println(enMes);
//        //System.out.println("1234");
        for (Character ch : encryptedMessage.toCharArray()){
            String sCh = String.valueOf(ch);
            int index = enMes.indexOf(sCh);
            System.out.print(
                    index < 0 ? sCh : mes.get(index)
            );
        }
    }
}
