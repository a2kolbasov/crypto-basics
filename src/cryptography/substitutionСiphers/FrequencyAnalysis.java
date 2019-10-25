package cryptography.substitutionСiphers;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

public class FrequencyAnalysis {
    public static Map getFrequencyTable(String text, int gramSize, String alphabet){
        Map<String, Integer> frequency = new HashMap<>();

        if (gramSize < 1)
            return null;
        text = text.toLowerCase();

        for (int position = 0; position < text.length() - gramSize + 1; position++){
            String gram = text.substring(position, position + gramSize);
            if (! containsInAlphabet(alphabet, gram))
                continue;

            Integer sum = frequency.get(gram);

            if (sum == null)
                sum = 0;

            frequency.put(gram, sum + 1);
        }
        return frequency;
    }

    private static boolean containsInAlphabet(String alphabet, String gram){
        for (char character : gram.toCharArray()){
            if (alphabet.indexOf(character) == -1)
                return false;
        }
        return true;
    }
}
