
import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles2 {

    private HashMap<String, ArrayList<String>> map;

    public WordsInFiles2() {
        map = new HashMap<String, ArrayList<String>>();
    }

    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f.getPath());
        for (String w : fr.words()) {
            if (!map.containsKey(w)) {
                //System.out.println("Adding \"" + w + "\" to hash map");
                map.put(w, new ArrayList<String>());
            }
            String fileName = f.getName();

            if (!map.get(w).contains(fileName)) {
                map.get(w).add(fileName);
            }
        }
    }

    public void buildWordFileMap() {
        map.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    public int maxNumber() {
        int maxNum = 0; // the size of the array list will tell us the number of file appearances
        for (ArrayList arrList : map.values()) {
            int currNumFiles = arrList.size();
            if (currNumFiles > maxNum) {
                maxNum = currNumFiles;
            }
        }

        return maxNum;
    }

    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> output = new ArrayList<String>();
        for (ArrayList arrList : map.values()) {
            int currNumFiles = arrList.size();
            if (currNumFiles == number) {
                output = arrList;
            }
        }
        System.out.println("No array list was found for file appearance of " + number);
        return output;

    }


    private void printsFilesIn(String word) {
        System.out.println("\"" + word + "\" appears in the files: \n");
        if (!map.containsKey(word)) {
            System.out.println("Word not found");
            return;
        }
        ArrayList<String> arrList = map.get(word);
        for (String s : arrList) {
            System.out.println(s);
        }
        System.out.println("\n");
    }

    public static void main (String[] arg) {
        WordsInFiles2 w = new WordsInFiles2();
        w.buildWordFileMap();
        System.out.println("Printing map...");
        for (String s : w.map.keySet()) {
            System.out.println(s + " : size: " + w.map.get(s).size());
        }
        //int number = maxNumber();
        int count = 0;
        //int number = 4;
        int number = 7;
        for (String s : w.map.keySet()) {
            if (w.map.get(s).size() == number) {
                System.out.println("Word(s) with most file appearances:\n" + s);
                w.printsFilesIn(s);
                count++;
            }
        }
        System.out.println("Number of words that appear in " + number + " files " + count);

        //String word2 = "sea";
        String word2 = "tree";
        ArrayList<String> arrList = w.map.get(word2);
        System.out.println("Files where the word " + word2 + " appears in:\n");
        for (String s : arrList) {
            System.out.println(s);
        }
    }
}