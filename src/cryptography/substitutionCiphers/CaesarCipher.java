package cryptography.substitutionCiphers;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

public class CaesarCipher {
    private final int shift;
    private final String alphabet;

    public CaesarCipher(int shift, String alphabet){
        this.alphabet = alphabet;
        this.shift = shift;
    }

    private char getShiftedChar(char shiftingCharacter, int shift){
        int charIndex = this.alphabet.indexOf(shiftingCharacter);

        // Если символа нет в алфавите, то не сдвигать
        if (charIndex == -1)
            return shiftingCharacter;

        return this.alphabet.charAt(
                (charIndex +
                        shift +
                        this.alphabet.length() // На случай отрицательного сдвига
                ) % this.alphabet.length()
        );
    }

    private String encrypt(int shift, String message){
        final char[] encryptedMessage = new char[message.length()];
        char character, shiftedCharacter;

        for (int charPosition = 0; charPosition < message.length(); charPosition++){
            character = message.charAt(charPosition);
            boolean isUpperCase = Character.isUpperCase(character);

            shiftedCharacter = getShiftedChar(
                    Character.toLowerCase(character), shift);

            encryptedMessage[charPosition] =
                    isUpperCase ? Character.toUpperCase(shiftedCharacter) : shiftedCharacter;
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
