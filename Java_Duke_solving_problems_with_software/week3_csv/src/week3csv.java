import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class week3csv {

    public String countryInfo (CSVParser parser, String country){
        String result = null;
        for (CSVRecord record: parser){
            //System.out.println(record);
            String getCountry = record.get("Country");
            if (getCountry.contains(country)){
                String export = record.get("Exports");
                System.out.println(getCountry + " : " + export);
                result = getCountry + " : " + export;
            }
        }
        if (result == null){
            return ("NOT FOUND");
        } else {
            return (result);
        }
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for (CSVRecord record: parser){
            //System.out.println(record);
            String export = record.get("Exports");
            //System.out.println(export);
            export = export.toLowerCase();
            if (export.contains(exportItem1) && export.contains(exportItem2)){
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public int numbersOfExporters(CSVParser parser, String exportItem){
        int result = 0;
        for (CSVRecord record: parser){
            String export = record.get("Exports");
            export = export.toLowerCase();
            if (export.contains(exportItem)){
                result ++;
            }
        }
        System.out.println(exportItem + " : " + result);
        return result;
    }

    public void bigExports(CSVParser parser, String amount){
        for (CSVRecord record: parser){
            String value = record.get("Value (dollars)");
            if (value.length()>amount.length()){
                String country = record.get("Country");
                System.out.println(country + " : " + value);
            }
        }

    }

    public static void main(String[] args){
        FileResource fr = new FileResource("/Users/xiaojingxu/Downloads/exports/exportdata.csv");
        CSVParser parser = fr.getCSVParser();
        week3csv w3 = new week3csv();
        w3.countryInfo(parser, "Nauru");
        parser = fr.getCSVParser();
        w3.listExportersTwoProducts(parser,"cotton","flowers");
        parser = fr.getCSVParser();
        w3.numbersOfExporters(parser, "cocoa");
        parser = fr.getCSVParser();
        w3.bigExports(parser, "$999,999,999,999");
    }
}
