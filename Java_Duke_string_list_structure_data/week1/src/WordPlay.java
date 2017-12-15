import edu.duke.*;
import org.omg.CORBA.PUBLIC_MEMBER;

public class WordPlay {
    public boolean isVowel(char ch){
        char[] V = new char[]{'a','e','i','o','u'};
        char c   = Character.toLowerCase(ch);
        for (char x: V){
            if (x==c){
                return true;
            }
        }
        return false;
    }

    public String replaceVowels (String phrase, char ch){
        String result = "";
        for (char x: phrase.toCharArray()){
            if (isVowel(x)){
                x = ch;
            }
            result = result + x;
        }
        return result;
    }

    public String emphasize (String phrase, char ch){
        String result = "";
        int count = 0;
        char ch1  = Character.toLowerCase(ch);
        for (char x: phrase.toCharArray()){
            count++;
            char x1 = Character.toLowerCase(x);
            if (x1 == ch1 && count%2 == 1){
                x = '*';
            } else if (x1 == ch1 && count%2 == 0){
                x = '+';
            }
            result = result + x;
        }
        return result;
    }

    public static void main(String[] arg){
        WordPlay w = new WordPlay();

        System.out.println(w.isVowel('A'));
        System.out.println(w.replaceVowels("BAbcddeeeAAECEE",'!'));
        System.out.println(w.emphasize("Mary Bella Abracadabra",'a'));
    }

}
