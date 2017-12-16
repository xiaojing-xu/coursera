import edu.duke.*;

import java.util.*;

public class CodonCount {
    private HashMap<String, Integer> freqs;

    public CodonCount(String dna, Integer start){
        freqs = new HashMap<String, Integer>();
        int dna_length = dna.length();
        if ( dna_length < 4){
            freqs.put(dna,1);
        }

        for (int i = start; i < dna_length - 3;){
            String s = dna.substring(i, i+3);

            if (!freqs.keySet().contains(s)){
                freqs.put(s, 1);
            } else {
                int count = freqs.get(s);
                count++;
                freqs.put(s, count);
            }
            i = i+3;
        }
    }

    public String getMostCommonCodon(){
        List<String> result = new LinkedList<String>();
        Integer max   = 0;
        for (String s: freqs.keySet()){
            if (freqs.get(s) > max){
                max = freqs.get(s);
            }
        }

        for (String s: freqs.keySet()){
            if (freqs.get(s) == max){
                result.add(s);
            }
        }

        System.out.println("The max codon is : " + result + " with freqs of : " + max);
        return result.toString();
    }

    public void printCodonCounts(int start, int end){
        for (String s: freqs.keySet()){
            if (freqs.get(s)>= start && freqs.get(s) <= end){
                System.out.println(s + " : " + freqs.get(s));
            }
        }
    }

    public static void main(String[] args) {
        //String s = "CGTTCAAGTTCAA";
        FileResource fr = new FileResource("/Users/xiaojingxu/Desktop/dnaMystery2.txt");
        String s = fr.asString().trim().toUpperCase();
        CodonCount c = new CodonCount(s, 2);
        System.out.println("The unique codon size is " + c.freqs.size());
        c.getMostCommonCodon();
        c.printCodonCounts(7,7);
    }

}
