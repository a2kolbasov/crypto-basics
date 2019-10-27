package cryptography.substitutionCiphers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

public class FrequencyAnalysis {
    private final HashSet<Character> alphabet = new HashSet<>();

    public FrequencyAnalysis(String alphabet){
        for (Character character : alphabet.toCharArray())
            this.alphabet.add(character);
    }

    public Map<String, Integer> toFrequencyMap(String text, int gramSize){
        Map<String, Integer> frequency = new HashMap<>();

        if (gramSize < 1)
            return null;
        text = text.toLowerCase();

        for (int position = 0; position < text.length() - gramSize + 1; position++){
            String gram = text.substring(position, position + gramSize);

            if (! containsInAlphabet(gram))
                continue;

            Integer sum = frequency.getOrDefault(gram, 0);
            frequency.put(gram, sum + 1);
        }
        return frequency;
    }

    private boolean containsInAlphabet(String gram){
        for (Character character : gram.toCharArray()){
            if (! this.alphabet.contains(character))
                return false;
        }
        return true;
    }
}
