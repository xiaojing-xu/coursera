import edu.duke.*;
import org.apache.commons.csv.CSVRecord;
import java.io.File;
import java.util.*;

import java.util.ArrayList;

public class WordsInFiles {
    private HashMap<String, ArrayList<String>> word_and_filename;

    public WordsInFiles(DirectoryResource dr){
        word_and_filename = new HashMap<String, ArrayList<String>>();

        for (File f: dr.selectedFiles()){
            String filename = f.getName();
            FileResource fr = new FileResource(f);
            for (String s: fr.words()){
                String s1 = s.toLowerCase();
                //System.out.println(s1);
                if (s1.length()==1 && !Character.isLetter(s1.charAt(0))){
                    s1 = "";
                } else if (s1.length()>1 && !Character.isLetter(s1.charAt(0))){
                    s1 = s1.substring(1,s1.length());
                }
                s1 = s1.replaceAll("[^A-Za-z]","");
                if(!word_and_filename.keySet().contains(s1) & s1 !=""){
                    ArrayList<String> l = new ArrayList<String>();
                    l.add(filename);
                    word_and_filename.put(s1,l);
                } else if (s1 !=""){
                    word_and_filename.get(s1).add(filename);
                }
            }
        }
        System.out.println("There are a total of " + word_and_filename.size() + " unique words");
    }

    public int maxNumber(){
        int max = 0;
        for (String s: word_and_filename.keySet()){
            if (word_and_filename.get(s).toArray().length > max){
                max = word_and_filename.get(s).toArray().length;
            }
        }

        ArrayList<String> max_word = new ArrayList<String>();
        for (String s: word_and_filename.keySet()){
            if (word_and_filename.get(s).toArray().length == max){
                max_word.add(s);
            }
        }
        System.out.println("The most word is : " + max_word + " : with : " + max + " : times");
        return max;
    }

    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> result = new ArrayList<String>();
        for (String s: word_and_filename.keySet()){
            if (word_and_filename.get(s).toArray().length == number){
                result.add(s);
            }
        }
        System.out.println(result);
        System.out.println("There are " + result.size() +" words appeared " + number + " times");
        return result;
    }

    public void printFilesin(String word){
        ArrayList<String> files  = word_and_filename.get(word);
        Set<String> unique_files = new HashSet<String>(files);
        System.out.println(unique_files);
    }

    public static void main(String[] args) {
        DirectoryResource dr = new DirectoryResource();
        WordsInFiles w = new WordsInFiles(dr);
        w.maxNumber();
        w.wordsInNumFiles(4);
        w.wordsInNumFiles(7);
        w.printFilesin("laid");
        w.printFilesin("tree");
    }
}
