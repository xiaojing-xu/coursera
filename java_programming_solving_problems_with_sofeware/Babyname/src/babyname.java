import edu.duke.*;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.ArrayList;

public class babyname {
    public void totalBirths(FileResource fr){
        int total = 0;
        int boys  = 0;
        int girls = 0;
        ArrayList<String> boys_name = new ArrayList<String>();
        ArrayList<String> girl_name = new ArrayList<String>();
        for (CSVRecord record: fr.getCSVParser(false)){
            int num = Integer.parseInt(record.get(2));
            total += num;
            if (record.get(1).contains("F") ){
                girls +=num;
                girl_name.add(record.get(0));
            } else if (record.get(1).contains("M")) {
                boys +=num;
                boys_name.add(record.get(0));
            }
        }
        System.out.println("total born is " + total + " including " + girls + " girls and " + boys + " boys");
        System.out.println("girl name size is " + girl_name.size() + ", boys name size is " + boys_name.size() );
    }


    public int getRank(Integer year, String name, String gender, FileResource fr){
        int rank = 0;
        boolean find = false;
        for (CSVRecord rd: fr.getCSVParser(false)){
            if (rd.get(1).contains(gender)){
                rank++;
                if (rd.get(0).equals(name)){
                    find = true;
                    break;
                }
            }
        }

        if (!find){
            rank = -1;
        }
        System.out.println(name + " in " + gender + " in " + year + " ranks " + rank);
        return rank;
    }

    public String getName(Integer year, Integer rank, String gender, FileResource fr){
        boolean find = false;
        int r = 0;
        String name = "";
        for (CSVRecord rd: fr.getCSVParser(false)){
            if (rd.get(1).contains(gender)){
                r++;
                if (r == rank){
                    find = true;
                    name = rd.get(0);
                    break;
                }
            }
        }

        if (find == false){
            name = "NO NAME";
        }
        System.out.println("Found that " + name + " in " + gender + " in " + year + " ranks " + rank);
        return name;
    }

    public void whatIsNameYear (String name, Integer year1,Integer year2, String gender, FileResource fr1, FileResource fr2){
        int rank1 = getRank(year1, name,  gender, fr1);
        String name2 = getName(year2, rank1, gender, fr2);
        System.out.println(name + "ranks " + rank1 + " in " + year1 + " , but in " + year2 + " should be " + name2);
    }

    public void yearOfHighestRank(String name, String gender, DirectoryResource dr){
        int highest_rank = Integer.MAX_VALUE;
        int highest_year = -1;
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f.getPath());
            String numberOnly= f.getName().replaceAll("[^0-9]", "");
            int year = Integer.parseInt(numberOnly);
            int current_rank = getRank(year, name, gender, fr);
            if (current_rank != -1 && current_rank < highest_rank){
                highest_rank = current_rank;
                highest_year = year;
            }
        }

        System.out.println(name + " in " + gender + " ranks highest as " + highest_rank + " in " + highest_year );
    }

    public void getAverageRank(String name, String gender, DirectoryResource dr){
        ArrayList<Integer> ranks = new ArrayList<Integer>();
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f.getPath());
            String numberOnly= f.getName().replaceAll("[^0-9]", "");
            int year = Integer.parseInt(numberOnly);
            int current_rank = getRank(year, name, gender, fr);
            ranks.add(current_rank);
        }
        if (ranks.size()>0){
            double average = ranks.stream().mapToInt(Integer::intValue).sum()/ranks.size();
            System.out.println(name + " in average ranks " + average);
        }
    }

    public int getTotalBirthsRankedHigher(Integer year, String name, String gender, FileResource fr){
        int result = 0;
        int rank_shouldbe = getRank(year,name,gender,fr);
        int rank = 0;
        for (CSVRecord rd: fr.getCSVParser(false)){
            if (rd.get(1).contains(gender)){
                rank++;
                if (rank < rank_shouldbe){
                    int num = Integer.parseInt(rd.get(2));
                    result += num;
                }
            }
        }
        System.out.println("There are " + result + " " + gender + " born ranked higher than " + name + " in year " + year);
        return result;
    }

    public static void main(String[] arg){
        //FileResource fr = new FileResource("/Users/xiaojingxu/Downloads/testing/yob2013short.csv");
        FileResource fr = new FileResource("/Users/xiaojingxu/Downloads/us_babynames/us_babynames_by_year/yob1990.csv");
        babyname by = new babyname();
        by.totalBirths(fr);
        //by.getRank(1971,"Frank","M",fr);
        by.getName(1982,350, "F", fr);
        by.getName(1982,450, "M", fr);
        by.getTotalBirthsRankedHigher(1990, "Drew", "M", fr);
        by.getTotalBirthsRankedHigher(1990, "Emily", "F", fr);

        FileResource fr1 = new FileResource("/Users/xiaojingxu/Downloads/us_babynames/us_babynames_by_year/yob1972.csv");
        FileResource fr2 = new FileResource("/Users/xiaojingxu/Downloads/us_babynames/us_babynames_by_year/yob2014.csv");
        by.whatIsNameYear("Susan", 1974, 2014, "F", fr1, fr2);
        //by.whatIsNameYear("Owen", 1974, 2014, "M", fr1, fr2);

        DirectoryResource dr = new DirectoryResource();
        //by.yearOfHighestRank("Mich", "M", dr);
        //by.yearOfHighestRank("Genevieve", "F", dr);
        by.getAverageRank("Susan", "F", dr);
        by.getAverageRank("Robert", "M", dr);
    }
}
