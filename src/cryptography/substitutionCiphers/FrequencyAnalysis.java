package cryptography.substitutionCiphers;

import java.util.*;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

// Поиск наименьшей разности

public class FrequencyAnalysis {
    private final HashSet<Character> alphabet = new HashSet<>();

    public FrequencyAnalysis(String alphabet){
        for (Character character : alphabet.toCharArray())
            this.alphabet.add(character);
    }

    public LinkedList<String> toFrequencyMap(String text, int gramSize){
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

        LinkedList<Map.Entry<String, Integer>> ll = new LinkedList<>(frequency.entrySet());
        Collections.sort(ll,(x,y) -> y.getValue().compareTo(x.getValue()));

        System.out.println(ll);

        LinkedList<String> l = new LinkedList<>();
        for (Map.Entry<String, Integer> e : ll)
            l.add(e.getKey());
        return l;
    }

    private boolean containsInAlphabet(String gram){
        for (Character character : gram.toCharArray()){
            if (! this.alphabet.contains(character))
                return false;
        }
        return true;
    }
}
