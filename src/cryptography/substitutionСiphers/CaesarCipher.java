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
            int newCharPosition = (charPosition + shift) % alphabet.length;
            shiftedAlphabet.put(alphabet[charPosition], alphabet[newCharPosition]);
        }
    }

    public String encrypt(String message){
        char[] charsFromMessage = message.toCharArray();
        buildShiftedAlphabet(this.shift);

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

}
