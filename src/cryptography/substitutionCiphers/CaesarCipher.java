/*
 * Copyright © 2019 Alexander Kolbasov
 */

package cryptography.substitutionCiphers;

public class CaesarCipher {
    private final int shift;
    private final String alphabet;

    public CaesarCipher(String alphabet, int shift){
        this.alphabet = alphabet;
        this.shift = shift;
    }

    private char getShiftedChar(char shiftingChar, int shift){
        // TODO : А не вернуться ли к Map<Char, Int> ?
        int charIndex = alphabet.indexOf(shiftingChar);

        // Если символа нет в алфавите, то не сдвигать
        if (charIndex == -1)
            return shiftingChar;

        int resultIndex =
                (charIndex + shift) % alphabet.length();
        if (resultIndex < 0)
            resultIndex += alphabet.length();

        return alphabet.charAt(resultIndex);
    }

    private String encrypt(int shift, String message){
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
        return encrypt(this.shift, message);
    }

    public String decrypt(String encryptedMessage){
        return encrypt(this.shift * -1, encryptedMessage);
    }
}
