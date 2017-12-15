import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import edu.duke.*;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class WordLengths {
    public void countWordLengths(FileResource resource, int[] counts){
        int max_size = counts.length - 1;
        Hashtable<Integer, List<String>> words = new Hashtable<Integer, List<String>>();
        HashSet<Integer> words_size = new HashSet<Integer>();
        for (String s: resource.words()){
            int size = s.length();
            if (size>0){
                if (!Character.isLetter(s.charAt(0))){
                    size --;
                }
                if (size>0 && !Character.isLetter(s.charAt(size-1))){
                    size --;
                }

                if (size >= max_size){
                    size = max_size;
                }

                counts[size]++;

                List<String> ls = new LinkedList<String>();
                ls.add(s);

                if (!words_size.contains(size)){
                    words_size.add(size);
                    words.put(size,ls);
                } else {
                    words.get(size).add(s);
                }
            }
        }

        for (int i = 0; i < counts.length; i++){
            System.out.println(i + " size has words "+ " total of " + counts[i]);
        }
    }

    public static void main(String[] args){
        FileResource fr = new FileResource("/Users/xiaojingxu/Desktop/manywords.txt");
        WordLengths w = new WordLengths();
        int[] counts = new int[20];
        w.countWordLengths(fr, counts);
    }

}
