import java.io.*;
import java.util.*;

public class ResultLoader {
    protected List<Record> allRecords = new ArrayList<>();

    public  List<Record>  loadFile(String filename){
        Set<String> seen = new HashSet<String>();
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line = null;
                while((line = reader.readLine()) != null) {
                    Record le = RecordParser.parseEntry(line);
                    allRecords.add(le);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allRecords;
    }
}
