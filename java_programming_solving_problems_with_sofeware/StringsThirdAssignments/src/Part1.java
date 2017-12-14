import edu.duke.*;

import java.lang.management.ManagementFactory;

public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int curindex = dna.indexOf(stopCodon, startIndex+3);
        while (curindex != -1){
            int diff = curindex - startIndex;
            if (diff % 3 == 0){
                return curindex;
            } else {
                curindex = dna.indexOf(stopCodon, curindex+3);
            }
        }
        return dna.length();
    }

    public void testFindStopCodon(){
        System.out.println(findStopCodon("AATGCTAACTAGCTGACTAAT",1,"TGA"));
        System.out.println(findStopCodon("CTGCCTGCATAATGAATGAAATAA",15,"TAA"));
    }


    public String findGene(String dna){
        int len = dna.length();
        int startIndex = dna.indexOf("ATG");
        int start = startIndex;

        while (true){
            if (startIndex == -1 | startIndex == len-5){
                return "";
            }
            int taa = findStopCodon(dna, startIndex, "TAA");
            int tag = findStopCodon(dna, startIndex, "TAG");
            int tga = findStopCodon(dna, startIndex, "TGA");

            int temp = Math.min(taa, tag);
            int minIndex = Math.min(temp,tga);
            if (minIndex == len){
                dna = dna.substring(startIndex+3);
                startIndex = dna.indexOf("ATG");
            }
            else {
                return dna.substring(startIndex,minIndex+3);
            }
        }
    }

    public StorageResource getAllGenes(String dna){
        StorageResource sr = new StorageResource();
        while (true){
            String gene = findGene(dna);
            if(gene==""){
                break;
            }
            int index = dna.indexOf(gene);
            sr.add(gene);
            index = index + gene.length();
            dna   = dna.substring(index);
        }
        System.out.println("There are a total of " + sr.size() + " genes");
        return sr;
    }

    public double cgRatio(String dna){
        int countC = 0;
        int countG = 0;
        int index = 0;
        int start = 0;
        while(true){
            index = dna.indexOf("C", start);
            if(index == -1){
                break;
            }
            countC++;
            start = index+1;
        }
        start = 0;
        while(true){
            index = dna.indexOf("G", start);
            if(index == -1){
                break;
            }
            countG++;
            start = index+1;
        }
        double cgR = ((double)(countC+countG))/dna.length();
        //System.out.println("The CG ratio is " + cgR);
        return cgR;
    }

    public int countCTG(String dna){
        int start = 0;
        int count = 0;
        int index = 0;
        while(true){
            index = dna.indexOf("CTG", start);
            if(index == -1){
                break;
            }
            count++;
            start = index+1;
        }
        System.out.println("The CTG count is " + count);
        return count;
    }

    public void processGenes(StorageResource sr){
        int count9 = 0;
        int countcg35 = 0;
        int len = 0;
        String max = "";
        int count60 = 0;
        for(String dna:sr.data()){
            dna = dna.toUpperCase();
            if(dna.length()>9){
                //System.out.println("The dna longer than 9 is "+dna);
                count9++;
            }
            if(cgRatio(dna)>0.35){
                //System.out.println("The string higher than 0.35 is "+dna);
                countcg35++;
            }
            if(dna.length()>len){
                max = dna;
                len = dna.length();
            }
            if(dna.length()>60){
                //System.out.println("The string longer than 60 is "+dna);
                count60++;
            }
        }
        System.out.println("The number of strings in sr that are longer than 9 characters is "+count9);
        System.out.println("The number of strings in sr that are higher than 0.35 is "+countcg35);
        System.out.println("The length of the longest gene in sr is "+len);
        System.out.println("The number of genes longer than 60 is "+count60);
    }

    public String mystery(String dna) {
        int pos = dna.indexOf("T");
        int count = 0;
        int startPos = 0;
        String newDna = "";
        if (pos == -1) {
            return dna;
        }
        while (count < 3) {
            count += 1;
            newDna = newDna + dna.substring(startPos,pos);
            startPos = pos+1;
            pos = dna.indexOf("T", startPos);
            if (pos == -1) {
                break;
            }
        }
        newDna = newDna + dna.substring(startPos);
        System.out.println(newDna);
        return newDna;
    }

    public static void main(String[] args){
        FileResource fr = new FileResource("/Users/xiaojingxu/Downloads/dna/GRch38dnapart.fa");
        String dna = fr.asString();
        Part1 p1 = new Part1();
        StorageResource sr = p1.getAllGenes(dna);
        //p1.cgRatio(dna);
        //p1.findGene(dna);
        //p1.getAllGenes(dna);
        p1.processGenes(sr);
        //p1.countCTG(dna);
        //p1.mystery("ATATATCGCTGCTGGTCTGTCA");
    }

}
