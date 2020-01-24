/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.substitutionCiphers;

import java.util.*;

// Можно добавить поиск наименьшей разности

public class FrequencyAnalysis {
    // Позволяет отбросить сторонние символы, знаки, буквы из других языков
    private final HashSet<Character> alphabet = new HashSet<>();

    /**
     * Частотный анализ текста
     * @param alphabet все символы, не перечисленные здесь, будут полностью игнорироваться
     */
    public FrequencyAnalysis(String alphabet){
        for (Character character : alphabet.toCharArray())
            this.alphabet.add(character);
    }

    /**
     * Вычисляет частоту употребления каждой n-граммы в тексте
     * @param text исследуемый текст
     * @param gramSize размер n-граммы (1 - буква, 2 - биграмма...)
     * @return {@code Map}, где ключ - грамма, значение - кол-во её употреблений
     */
    public Map<String, Integer> getGramFrequency(String text, int gramSize){
        if (gramSize < 1)
            return Collections.emptyMap();

        // <gram, frequency>
        Map<String, Integer> frequencyMap = new HashMap<>();

        text = text.toLowerCase();

        for (int position = 0; position < text.length() - gramSize + 1; position++){
            // Берём n-грамму
            String gram = text.substring(position, position + gramSize);

            // Отсеиваем ошибочные n-граммы (знаки препинания и т.д.)
            if (! isContainsInAlphabet(gram))
                continue;

            // Инкремент счётчика частоты n-граммы
            Integer frequency = frequencyMap.getOrDefault(gram, 0);
            frequencyMap.put(gram, frequency + 1);
        }
        return frequencyMap;
    }

    /**
     * Сортирует n-граммы в частотной {@code Map} по убыванию частоты.
     * @see #getGramFrequency(String, int)
     * @param frequencyMap {@code Map}, где ключ - грамма, значение - кол-во её употреблений
     * @return {@code List} с отсортированными n-граммами
     */
    public List<String> sortGramsByFrequency(Map<String, Integer> frequencyMap) {
        // Будем работать с клоном
        frequencyMap = Map.copyOf(frequencyMap);

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

    /**
     * Расшифровывает зашифрованный с помощью шифра подстановки текст с помощью частотного анализа.
     * Текст расшифровывается не полностью, всё зависит от частоты букв в зашифрованном и в эталонном тексте.
     * Также на вход подаётся размер n-грамм. 1 -
     * @param encryptedText текст, который нужно расшифровать
     * @param referenceText эталонный текст на таком же языке, что и расшифровываемый
     * @param gramSize размер n-граммы
     * @return расшифрованный текст
     */
    public String decryptText(String encryptedText, String referenceText, int gramSize) {
        if (gramSize < 1)
            return "";

        // <Encrypted_gram, decrypted_gram>
        Map<Character, Character> charMatch = new HashMap<>();

        // Сопостовляем зашифрованные и расшифрованные символы

        // Не все буквы могут попасть в n-граммы, поэтому после первого прохода мы имеще (n-1)-граммы и т.д.
        // Ищем сопостовления пока все буквы не найдены (charMatch.size == alphabet.size)
        while (gramSize > 0 && charMatch.size() < alphabet.size()) {
            Iterator<String>
                    encryptedGrams = sortGramsByFrequency(getGramFrequency(encryptedText, gramSize)).iterator(),
                    referenceGrams = sortGramsByFrequency(getGramFrequency(referenceText, gramSize)).iterator();

            while (encryptedGrams.hasNext() && referenceGrams.hasNext()) {
                char[] encryptedCharsFromGram = encryptedGrams.next().toCharArray();
                char[] referenceCharsFromGram = referenceGrams.next().toCharArray();
                int point = 0;
                for (char ch : encryptedCharsFromGram)
                    // Если ещё не было такой же расшифрованной буквы
                    if (! charMatch.containsValue(referenceCharsFromGram[point]))
                        // И если не было такой же зашифрованной, то добавить соответствие
                        charMatch.putIfAbsent(ch, referenceCharsFromGram[point++]);
            }
            // Переходим к (n-1)-граммам
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

    /**
     * Проверка правильности захваченной n-граммы
     * Например, если {@code alphabet == "abc"}, а {@code gram = "ca"}, то вернёт {@code true},
     * а если {@code gram == "a,"} - вернёт {@code false}.
     * Так можно отсеить иностранные символы и знаки препинания, мешающие частотному анализу.
     * @param gram исследуемая n-грамма
     * @return результат проверки
     */
    private boolean isContainsInAlphabet(String gram){
        for (Character character : gram.toCharArray()){
            if (! this.alphabet.contains(character))
                return false;
        }
        return true;
    }
}
