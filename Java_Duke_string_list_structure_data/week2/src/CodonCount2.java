import edu.duke.*;

import java.util.*;

public class CodonCount2{
    private HashMap<String, Integer> codonCount;

    public CodonCount2(int start, String dna) {
        codonCount = new HashMap<String, Integer>();
        buildCodonMap(start,dna);
    }

    public void buildCodonMap(int start, String dna) {
        codonCount.clear();
        int c = 0; //# of codons
        int i = 0; // # of iterations

        c = (dna.length() - start)/3;
        String curr;
        while (i<= c-1) {
            curr = dna.substring(i*3+start, i*3+start+3);
            if (!codonCount.containsKey(curr)) {
                codonCount.put(curr, 1);
            }
            else {
                codonCount.put(curr, codonCount.get(curr)+1);
            }
            i += 1;
        }

    }

    public String getMostCommonCodon() {
        System.out.println("Size of codonCount is " + codonCount.size());
        int max = -999;
        String maxKeyStr = "error";
        for (String s : codonCount.keySet()) {
            int codonVal = codonCount.get(s);
            if (codonVal > max) {
                max = codonVal;
                maxKeyStr = s;
            }
        }

        return maxKeyStr;
    }

    public void printCodonCounts(int start, int end) {
        System.out.println("Printing codons along with their counts that are between " + start + " and " + end);
        for (String s : codonCount.keySet()) {
            int codCount = codonCount.get(s);
            if (codCount >= start && codCount <= end) {
                System.out.println(s + " : " + codCount);
            }
        }
    }

    public void tester() {
        FileResource fr = new FileResource();

        String dnaUpper = fr.asString().toUpperCase().trim();

        buildCodonMap(0, dnaUpper);
        System.out.println("Reading frame starting with 0 results in " + codonCount.size() + " unique codons");
        String mostCommon = getMostCommonCodon();
        System.out.println("Most common codon is " + mostCommon + " with count " + codonCount.get(mostCommon));
        System.out.println("Counts of codons between 1 and 5 inclusive are: ");
        printCodonCounts(6, 8);


        buildCodonMap(1, dnaUpper);
        System.out.println("Reading frame starting with 1 results in " + codonCount.size() + " unique codons");
        mostCommon = getMostCommonCodon();
        System.out.println("Most common codon is " + mostCommon + " with count " + codonCount.get(mostCommon));
        System.out.println("Counts of codons between 1 and 5 inclusive are: ");
        printCodonCounts(1, 20);

        buildCodonMap(2, dnaUpper);
        System.out.println("Reading frame starting with 2 results in " + codonCount.size() + " unique codons");
        mostCommon = getMostCommonCodon();
        System.out.println("Most common codon is " + mostCommon + " with count " + codonCount.get(mostCommon));
        System.out.println("Counts of codons between 1 and 5 inclusive are: ");
        printCodonCounts(1, 20);



    }

    public int findStopIndex(String dna, int index) {
        String dnaLower = dna.toLowerCase();
        int stop1 = dnaLower.indexOf("tga", index);
        if (stop1 == -1 || (stop1-index) % 3 != 0) {
            stop1 = dna.length();
        }
        int stop2 = dnaLower.indexOf("taa", index);
        if (stop2 == -1 || (stop2-index) % 3 != 0) {
            stop2 = dna.length();
        }
        int stop3 = dnaLower.indexOf("tag", index);
        if (stop3 == -1 || (stop3-index) % 3 != 0) {
            stop3 = dna.length();
        }
        return Math.min(stop1, Math.min(stop2,stop3));
    }

    public StorageResource storeAll(String dna, int start) {
        StorageResource storeGenes = new StorageResource();
        while (true) {
            String dnaLower = dna.toLowerCase();
            int tag = dnaLower.indexOf("atg",start);
            if (tag == -1) {
                break;
            }
            int end = findStopIndex(dna, tag+3);

            if (end != dna.length()) {
                //System.out.println("Gene found:");
                //System.out.println(dna.substring(tag, end+3));
                storeGenes.add(dna.substring(tag, end+3));
                start = end + 3;
            }
            else {
                start = start + 3;
            }
        }
        return storeGenes;
    }

    public static void main(String[] args) {
        FileResource fr = new FileResource("/Users/xiaojingxu/Desktop/dnaMystery2.txt");
        String s = fr.asString().trim().toUpperCase();
        CodonCount2 c = new CodonCount2(2,s);
        System.out.println(c.getMostCommonCodon() + c.codonCount.get(c.getMostCommonCodon()));
    }
}
