import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import edu.duke.*;

import java.util.HashSet;
import java.util.Hashtable;

public class CharactersInPlay {
    private HashSet<String> characters;
    private Hashtable<String, Integer> character_freqs;

    public CharactersInPlay(FileResource fr){
        characters = new HashSet<String >();
        character_freqs = new Hashtable<String, Integer>();

        for (String l : fr.lines()){
            String r = "";
            for (char c: l.toCharArray()){
                if (c != '.'){
                    r = r + c;
                }
                if (c == '.'){
                    break;
                }
            }

            if (!characters.contains(r) && r !=""){
                characters.add(r);
                character_freqs.put(r, 1);
            } else if (r !="") {
                Integer count = character_freqs.get(r);
                count++;
                character_freqs.put(r, count);
            }
        }
    }

    public void charactersWithNumParts(Integer num1, Integer num2){
        for (String s:character_freqs.keySet()){
            Integer count = character_freqs.get(s);
            if (count >= num1 && count <= num2){
                System.out.println(s + ":" + character_freqs.get(s));
            }
        }
    }

    public static void main(String[] args) {
        //FileResource fr = new FileResource("/Users/xiaojingxu/Desktop/likeit.txt");
        FileResource fr = new FileResource("/Users/xiaojingxu/Desktop/errors.txt");
        CharactersInPlay c = new CharactersInPlay(fr);
        c.charactersWithNumParts(70,90000000);
        c.charactersWithNumParts(10,15);
    }
}
