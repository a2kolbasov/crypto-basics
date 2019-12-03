/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.substitutionCiphers;

import java.util.*;

// todo: Поиск наименьшей разности

public class FrequencyAnalysis {
    // Позволяет отбросить сторонние символы, знаки, буквы из других языков
    private final HashSet<Character> alphabet = new HashSet<>();

    public FrequencyAnalysis(String alphabet){
        for (Character character : alphabet.toCharArray())
            this.alphabet.add(character);
    }

    public Map<String, Integer> getGramFrequency(String text, int gramSize){
        // <gram, frequency>
        Map<String, Integer> frequencyMap = new HashMap<>();

        if (gramSize < 1)
            return Collections.emptyMap();

        text = text.toLowerCase();

        for (int position = 0; position < text.length() - gramSize + 1; position++){
            String gram = text.substring(position, position + gramSize);

            if (! isContainsInAlphabet(gram))
                continue;

            Integer frequency = frequencyMap.getOrDefault(gram, 0);
            frequencyMap.put(gram, frequency + 1);
        }
        return frequencyMap;
    }

    public List<String> getSortedByFrequencyGramList(String text, int gramSize) {
        if (gramSize < 1)
            return Collections.emptyList();

        Map<String, Integer> frequencyMap =  getGramFrequency(text, gramSize);

        // Нужен для сортировки по Integer
        LinkedList<Map.Entry<String, Integer>>
                list = new LinkedList<>(frequencyMap.entrySet());
        list.sort(
                (x, y) -> y.getValue().compareTo( x.getValue() ));

        // Итоговый List
        LinkedList<String> result = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : list)
            result.add(entry.getKey());
        return result;
    }

    public String decryptText(String encryptedText, String referenceText, int gramSize) {
//        if (gramSize < 1)
//            return "";

        // <Encrypted_gram, decrypted_gram>
        Map<Character, Character> charMatch = new HashMap<>();

        // Сопостовляем зашифрованные и расшифрованные символы
        while (gramSize > 0 && charMatch.size() < alphabet.size()) {
            Iterator<String> encryptedGrams = getSortedByFrequencyGramList(encryptedText, gramSize).iterator();
            Iterator<String> referenceGrams = getSortedByFrequencyGramList(referenceText, gramSize).iterator();

            while (encryptedGrams.hasNext() && referenceGrams.hasNext()) {
                char[] encryptedCharsFromGram = encryptedGrams.next().toCharArray();
                char[] referenceCharsFromGram = referenceGrams.next().toCharArray();
                int point = 0;
                for (char ch : encryptedCharsFromGram)
                    // Если ещё не было такойже расшифрованной буквы
                    if (! charMatch.containsValue(referenceCharsFromGram[point]))
                        // И если не было такой же зашифрованной
                        charMatch.putIfAbsent(ch, referenceCharsFromGram[point++]);
            }
            gramSize--;
        }

        // Расшифровываем текст
        char[] replacement = encryptedText.toCharArray();
        for (int position = 0; position < replacement.length; position++) {
            boolean isUpper = Character.isUpperCase(replacement[position]);

            Character replace = charMatch.getOrDefault(
                    // Если нет расшифровки, то вернуть не меняя
                    Character.toLowerCase(replacement[position]),
                    replacement[position]
            );
            replacement[position] = isUpper ? Character.toUpperCase(replace) : replace;
        }
        return String.valueOf(replacement);
    }

    private boolean isContainsInAlphabet(String gram){
        for (Character character : gram.toCharArray()){
            if (! this.alphabet.contains(character))
                return false;
        }
        return true;
    }
}
