/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.substitutionCiphers;

import java.util.HashMap;

public class CaesarCipher {
    private final int shift;
    private final String alphabet;
    // Используется для быстрого поиска позиции символа в алфавите
    private final HashMap<Character, Integer> alphabetMap = new HashMap<>();

    public CaesarCipher(String alphabet, int shift){
        this.alphabet = alphabet;
        this.shift = shift;

        for (int i = 0; i< alphabet.length(); i++)
            this.alphabetMap.put(alphabet.charAt(i), i);
    }

    private char getShiftedChar(char shiftingChar, int shift){
        int charIndex = alphabetMap.getOrDefault(shiftingChar, -1);

        // Если символа нет в алфавите, то не сдвигать
        if (charIndex == -1)
            return shiftingChar;

        int resultIndex =
                (charIndex + shift) % alphabet.length();
        if (resultIndex < 0)
            resultIndex += alphabet.length();

//        assert resultIndex == Math.floorMod(charIndex + shift, alphabet.length());
        return alphabet.charAt(resultIndex);
    }

    private String encrypt(final String message, int shift){
        final char[] encryptedMessage = new char[message.length()];
        char character, shiftedCharacter;

        for (int charPosition = 0; charPosition < message.length(); charPosition++){
            character = message.charAt(charPosition);
            boolean isUpper = Character.isUpperCase(character);

            shiftedCharacter = getShiftedChar(
                    Character.toLowerCase(character), shift);

            encryptedMessage[charPosition] =
                    isUpper ? Character.toUpperCase(shiftedCharacter) : shiftedCharacter;
        }
        return String.valueOf(encryptedMessage);
    }

    public String encrypt(String message){
        return encrypt(message, shift);
    }

    public String decrypt(String encryptedMessage){
        return encrypt(encryptedMessage, shift * -1);
    }
}
