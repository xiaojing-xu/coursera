import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import edu.duke.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;

public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    private Hashtable<String,Integer> word_freq;
    private HashSet<String> words;

    public WordFrequencies(FileResource fr){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
        word_freq = new Hashtable<String, Integer>();
        words   = new HashSet<String>();

        for (String w: fr.words()){
            String word = purifyWord(w);
            //System.out.println(word);
            if (word != ""){
                if (!words.contains(word)){
                    words.add(word);
                    word_freq.put(word,1);
                } else {
                    int count = word_freq.get(word);
                    count++;
                    word_freq.put(word,count);
                }
            }
        }
    }

    public String purifyWord(String word){
        //System.out.println(word);
        String w = word.toLowerCase();
        //System.out.println(w);
        //String result = "";
        //for (char c: w.toCharArray()){
            //if (Character.isLetter(c)){
                //result = result + c;
            //}
        //}
        //return  result;
        return w;
    }

    public void findUique(){
        for (String w: word_freq.keySet()){
            System.out.println(w + " : "+ word_freq.get(w));
        }
        System.out.println("There are " + words.size() + " unique words");
    }
    public void findIndexOfMax(){
        int max = Collections.max(word_freq.values());
        String max_word = "";
        for (String s: word_freq.keySet()){
            if (word_freq.get(s)==max){
                max_word = s;
            }
        }
        System.out.println("The max is : " + max_word +  " : with frequencies of " + max);
    }

    public static void main(String[] args) {
        FileResource fr = new FileResource("/Users/xiaojingxu/Desktop/errors.txt");
        WordFrequencies w = new WordFrequencies(fr);
        w.findUique();
        w.findIndexOfMax();
    }
}
