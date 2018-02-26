import edu.duke.FileResource;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Record {
    protected String recordX;
    protected String recordY;
    protected double coef;
    protected double fdr;
    protected double pvalue;
    protected int    samplesize;

    protected String classX;
    protected String classY;

    @Override
    public String toString() {
        return recordX  + " : " + recordY + " : " + coef + " : " + pvalue + " : "+ samplesize + " : " + pvalue + " : " + classX + " : " + classY;
    }


    public boolean acrossOmic(){
        if (!classX.equals(classY)){
            return true;
        }
        return false;
    }


    public boolean isGenetic(){
        if (classX == "polygenic" || classY == "polygenic"){
            return true;
        }
        return false;
    }

    public boolean isLifestyle(){
        if (classX.contains("life") || classY.contains("life")){
            return true;
        }
        return false;
    }

    public boolean isDisease(){
        if (classX.contains("health") || classY.contains("health")){
            return true;
        }
        return false;
    }

    public boolean isClinical(){
        if (classX.contains("clinic") || classY.contains("clinic")){
            return true;
        }
        return false;
    }
}
