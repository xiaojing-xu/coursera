import edu.duke.*;
import org.omg.CORBA.PUBLIC_MEMBER;

public class CaesarCipher {
    public String encrypt(String input, int key){
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetLower = alphabet.toLowerCase();
        String shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
        String shiftedLower = shiftedAlphabet.toLowerCase();
        for(int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            int idxLower = alphabetLower.indexOf(currChar);
            if(idx != -1){
                char newChar = shiftedAlphabet.charAt(idx);
                encrypted.setCharAt(i, newChar);
            }
            else if (idxLower != -1) {
                char newCharLower = shiftedLower.charAt(idxLower);
                encrypted.setCharAt(i, newCharLower);
            }
        }
        return encrypted.toString();
    }

    public String encryptTwoKeys(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetLower = alphabet.toLowerCase();
        String shiftedUpperKey1 = alphabet.substring(key1) + alphabet.substring(0,key1);
        String shiftedLowerKey1 = shiftedUpperKey1.toLowerCase();

        String shiftedUpperKey2 = alphabet.substring(key2) + alphabet.substring(0,key2);
        String shiftedLowerKey2 = shiftedUpperKey2.toLowerCase();
        for(int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(currChar);
            int idxLower = alphabetLower.indexOf(currChar);
            if(idx != -1){
                if(i % 2 == 0){
                    char newChar = shiftedUpperKey1.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                } else {
                    char newChar2 = shiftedUpperKey2.charAt(idx);
                    encrypted.setCharAt(i, newChar2);
                }
            } else if (idxLower != -1) {
                if (i % 2 == 0) {
                    char newCharLower = shiftedLowerKey1.charAt(idxLower);
                    encrypted.setCharAt(i, newCharLower);
                } else {
                    char newCharLower2 = shiftedLowerKey2.charAt(idxLower);
                    encrypted.setCharAt(i, newCharLower2);
                }
            }
        }
        return encrypted.toString();
    }


    public static void main(String[] arg){
        CaesarCipher cc = new CaesarCipher();
        System.out.println(cc.encrypt("Can you imagine life WITHOUT the internet AND computers in your pocket?", 15));
        System.out.println(cc.encryptTwoKeys("Can you imagine life WITHOUT the internet AND computers in your pocket?", 21, 8));
    }
}
