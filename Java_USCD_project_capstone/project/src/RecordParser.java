import edu.duke.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.*;
import java.text.*;


public class RecordParser {
    public static Record parseEntry(String line){
        Record r = new Record();
        String[] singeLine = line.split(",");
        if (singeLine.length == 8 ){
            r.coef   = Double.parseDouble(singeLine[0]);
            r.pvalue = Double.parseDouble(singeLine[3]);
            r.samplesize = Integer.parseInt(singeLine[6]);
            r.fdr      = Double.parseDouble(singeLine[7]);

            String x = singeLine[4];
            String y = singeLine[5];

            if (x.contains("assessment_")){
                x = x.replace("assessment_", "");
            }

            if (y.contains("assessment_")){
                y = y.replace("assessment_", "");
            }

            r.classX  = x.split("_")[0];
            r.classY  = y.split("_")[0];

            r.recordX = x;
            r.recordY = y;
        }
        return r;
    }
}
