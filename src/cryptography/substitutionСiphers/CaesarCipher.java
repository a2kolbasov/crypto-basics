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
        this.buildShiftedAlphabet();
    }
    public CaesarCipher(int shift){
        // С русским алфавитом
        this.alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        this.shift = shift;
        this.buildShiftedAlphabet();
    }

    private void buildShiftedAlphabet(){
        shiftedAlphabet = new HashMap<>();

        for (int i = 0; i < alphabet.length; i++){
            int charPosition = (i + shift) % alphabet.length;
            shiftedAlphabet.put(alphabet[i], alphabet[charPosition]);
        }
    }

    public String encrypt(String message){
        char[] chars = message.toCharArray();
        //buildShiftedAlphabet(); // Если задавать сдвиг не в конструкторе
        //System.out.println(shiftedAlphabet);

        for (int i = 0; i < chars.length; i++){
            try {
                chars[i] = shiftedAlphabet.get(Character.toLowerCase(chars[i]));
            } catch (NullPointerException e){
                // Если символа нет в алфавите
                //System.err.printf("char %c hasn't in the alphabet", chars[i]);
            }
        }
        return String.valueOf(chars);
    }
}
