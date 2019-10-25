package cryptography.substitutionСiphers;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright © 2019 Alexander Kolbasov
 */

public class CaesarCipher {
    public final int shift;
    public final char[] alphabet;

    // <originalChar, shiftedChar>
    private Map<Character, Character> shiftedAlphabet;

    public CaesarCipher(int shift, char[] alphabet){
        this.alphabet = alphabet;
        this.shift = shift;
    }

    private void buildShiftedAlphabet(int shift){
        shiftedAlphabet = new HashMap<>();

        for (int charPosition = 0; charPosition < alphabet.length; charPosition++){
            int newCharPosition =
                    (charPosition + shift +
                            alphabet.length // Если сдвиг отрицательный
                    ) % alphabet.length;
            shiftedAlphabet.put(alphabet[charPosition], alphabet[newCharPosition]);
        }
    }

    private String encrypt(int shift, String message){
        char[] charsFromMessage = message.toCharArray();
        buildShiftedAlphabet(shift);

        for (int i = 0; i < charsFromMessage.length; i++){
            try {
                boolean isUpperCase = Character.isUpperCase(charsFromMessage[i]);
                Character character = shiftedAlphabet.get(Character.toLowerCase(charsFromMessage[i]));
                charsFromMessage[i] = isUpperCase ? Character.toUpperCase(character) : character;
            } catch (NullPointerException e){
                // Если символа нет в алфавите
                //System.err.printf("char %c hasn't in the alphabet", chars[i]);
            }
        }
        return String.valueOf(charsFromMessage);
    }

    public String encrypt(String message){
        return encrypt(this.shift, message);
    }

    public String decrypt(String encryptedMessage){
        return encrypt(this.shift * -1, encryptedMessage);
    }
}
