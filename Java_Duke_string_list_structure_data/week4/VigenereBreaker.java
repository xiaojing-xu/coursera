import java.util.HashMap;
import java.util.HashSet;

import edu.duke.FileResource;


public class VigenereBreaker {

    private HashMap<String, HashSet<String>> dicts;
    private HashMap<String, Character> commonChars;

    public VigenereBreaker() {
        dicts = new HashMap<String, HashSet<String>>();
        commonChars = new HashMap<String, Character>();

        // read all dictionaries
        readDict("Danish");
        readDict("Dutch");
        readDict("English");
        readDict("French");
        readDict("German");
        readDict("Italian");
        readDict("Portuguese");
        readDict("Italian");
        readDict("Spanish");

        // find most common characters in dictionaries
        for (String langName : dicts.keySet()) {
            commonChars.put(langName, mostCommonCharIn(dicts.get(langName)));
        }

    }

    private void readDict(String lang) {
        String path = "/Users/xiaojingxu/Downloads/VigenereProgram/dictionaries/"+lang;
        dicts.put(lang, readDictionary(new FileResource(path)));

    }


    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder();
        char[] ca = message.toCharArray();
        for (int i=whichSlice;i<ca.length;i+=totalSlices) sb.append(ca[i]);

        return sb.toString();
    }


    public String translateKey(int[] key) {
        final String ABT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();

        for (int i=0;i<key.length;i++) sb.append(ABT.charAt(key[i]));

        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);

        for (int i=0; i<klength;i++) {
            String slice = sliceString(encrypted, i, klength);
            key[i] = cc.getKey(slice);
        }

        return key;
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> set = new HashSet<String>();

        for (String word : fr.words()) set.add(word.toLowerCase());

        return set;
    }



    public int countWords(String message, HashSet<String> dict) {

        int count = 0;
        message = message.toLowerCase();

        String[] words = message.split("\\W");
        for (String word : words) if (dict.contains(word)) count++;
        return count;
    }


    public String breakForLanguage(String encrypted, String langName) {

        HashMap<int[], Integer> keys = new HashMap<int[], Integer>();
        HashSet<String> dict = dicts.get(langName);
        char commonChar = commonChars.get(langName);

        for (int i=1;i<100;i++) {
            int[] key = tryKeyLength(encrypted, i, commonChar);
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            int cnt = countWords(decrypted, dict);
            keys.put(key, cnt);

        }

        int maxCoutn = 0;
        int[] foundKey = null;

        for (int[] key : keys.keySet()) {
            if (maxCoutn < keys.get(key)) {
                maxCoutn = keys.get(key);
                foundKey = key;
            }
        }

        System.out.println("Language:");
        System.out.println(langName);

        System.out.println("Key length:");
        System.out.println(foundKey.length);


        VigenereCipher vc = new VigenereCipher(foundKey);
        System.out.println("Decrypted word count:");
        System.out.println(countWords(vc.decrypt(encrypted), dict));

        System.out.println("##########################");
        return vc.decrypt(encrypted);
    }


    public char mostCommonCharIn(HashSet<String> dict) {
        HashMap<Character, Integer> charCounts = new HashMap<Character, Integer>();

        // count the characters counts in dictionary
        for (String word : dict) {
            for (char c : word.toLowerCase().toCharArray()) {
                if (!charCounts.containsKey(c)) charCounts.put(c, 1);
                else charCounts.put(c, charCounts.get(c)+1);
            }
        }

        int maxFreq = 0;
        char mostCommon = 'a';

        for (char c : charCounts.keySet()) {

            if (charCounts.get(c) > maxFreq) {
                maxFreq = charCounts.get(c);
                mostCommon = c;
            }

        }

        return mostCommon;
    }


    public String breakForAllLanguages(String enctrypted) {

        for (String langName : dicts.keySet()) {

            breakForLanguage(enctrypted,langName);

        }

        return "";
    }


    public void breakVigenere () {
        String input = new FileResource().asString();
        breakForAllLanguages(input);

        String dec = breakForLanguage(input, "German");
        System.out.println(dec);

//        HashSet<String> dict = readDictionary(new FileResource());
//        La chambre ? coucher de Juliette.
//
//        String dec = breakForLanguage(input, dict);
// //
//        int[] key38 = tryKeyLength(input, 38, 'e');
//        String dec38 = new VigenereCipher(key38).decrypt(input);
//        System.out.println("Decrypted with key 38");
//        System.out.println(countWords(dec38, dict));
    }
}
