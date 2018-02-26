import edu.duke.FileResource;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.lang.*;

public class Graphload {

    public static void LoadSingleRecord(CapGraph g, Record r){
        g.addVertex(r.recordX);
        g.addEdge(r.recordX, r.recordY, r.coef);
    }

    public static void LoadallRecords(CapGraph g, List<Record> allrecord, Double fdr, Double pvalue){
        for (Record r: allrecord){
            if (r.fdr < fdr && r.pvalue < pvalue){
                LoadSingleRecord(g,r);
            }
        }
    }

    public static void LoadOmicRecords(CapGraph g, List<Record> allrecord, Double fdr, Double pvalue, Boolean acrossOmic){
        for (Record r: allrecord){
            if (r.fdr < fdr && r.pvalue < pvalue && r.acrossOmic() == acrossOmic){
                LoadSingleRecord(g,r);
            }
        }
    }

    public static void LoadSubClassRecords(CapGraph g, List<Record> allrecord, Double fdr, Double pvalue, String classx, String classy){
        for (Record r: allrecord){
            if (r.fdr < fdr && r.pvalue < pvalue && r.classX.contains(classx) && r.classY.contains(classy)){
                LoadSingleRecord(g,r);
            }
        }
    }

    public static void printClass(List<Record> allrecord){
        HashSet<String> classes = new HashSet<>();
        for (Record r: allrecord){
            if(!classes.contains(r.classX)){
                classes.add(r.classX);
            }
        }
        System.out.println(classes);
    }
}
