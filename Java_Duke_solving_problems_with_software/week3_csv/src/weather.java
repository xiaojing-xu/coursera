import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class weather {

    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldest = null;
        double coldest_temperature = 0;
        for (CSVRecord record: parser){
            if (record.get("TemperatureF")=="-9999"){
            } else {
                Double temperature = Double.parseDouble(record.get("TemperatureF"));
                if (coldest == null | temperature < coldest_temperature){
                    coldest = record;
                    coldest_temperature = temperature;
                }
            }
        }
        System.out.println("the lowest temperature is " + coldest.get("TemperatureF"));
        return coldest;
    }

    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
            if (currentTemp < smallestTemp && currentTemp != -9999) {
                smallestSoFar = currentRow;
            }
        }
        return smallestSoFar;
    }

    public String fileWithColdestTemperature(){
        CSVRecord coldestSoFar = null;
        String filename ="";

        DirectoryResource dr =  new DirectoryResource();

        for (File f: dr.selectedFiles()){
            //System.out.println(f.getPath());
            FileResource fr  = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentRecord = coldestHourInFile(parser);
            coldestSoFar = getSmallestOfTwo(currentRecord, coldestSoFar);
            if (coldestSoFar == currentRecord){
                filename = f.getPath();
            }
        }
        FileResource fr  = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        String tempareture = coldestHourInFile(parser).get("TemperatureF");
        System.out.println("the lowest temperature file is " + filename + " and the temprature is " + tempareture);
        return filename;
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord currentRow : parser) {
            lowestSoFar = getSmallestHumidityOfTwo(currentRow, lowestSoFar);
        }
        System.out.println("Lowest Humiditiy in file is " + lowestSoFar.get("Humidity") + lowestSoFar.get("DateUTC"));
        return lowestSoFar;
    }

    public CSVRecord getSmallestHumidityOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;
        }

        else {
            if (currentRow.get("Humidity").length() != 3){
                double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
                double smallestTemp = Double.parseDouble(smallestSoFar.get("Humidity"));
                if (currentTemp < smallestTemp && currentTemp != -9999) {
                    smallestSoFar = currentRow;
                }
            }
        }
        return smallestSoFar;
    }

    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            lowestSoFar = getSmallestHumidityOfTwo(currentRow, lowestSoFar);
        }
        System.out.println("the lowest humidity in many files is " + lowestSoFar.get("Humidity") + " : "+ lowestSoFar.get("DateUTC"));
        return lowestSoFar;
    }

    public double averageTemperatureInFile(CSVParser parser){
        double sum=0;
        double avarage =0;
        int count = 0;
        for (CSVRecord currentRow : parser) {
            if (currentRow.get("TemperatureF")!="-9999"){
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                sum += currentTemp;
                count++;
            }
        }
        avarage =sum/count;
        System.out.println("the average tempareture is " + avarage);
        return avarage;
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value ){

        double sum=0;
        double avarage =0;
        int count = 0;
        for (CSVRecord currentRow : parser) {
            if (currentRow.get("TemperatureF")!="-9999" && currentRow.get("Humidity").length() != 3){
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                if(currentHumidity>=value){
                    sum += currentTemp;
                    count++;
                }
            }
        }
        avarage =sum/count;
        System.out.println("the average temperature is " + avarage + " with humidity " + value);
        return avarage;
    }

    public static void main (String[] args){
        //DirectoryResource dr =  new DirectoryResource();
        //Iterable<File> files = dr.selectedFiles();

        weather v = new weather();
        v.fileWithColdestTemperature();
        //v.lowestHumidityInManyFiles();

        FileResource fr = new FileResource("/Users/xiaojingxu/Downloads/nc_weather/2013/weather-2013-09-02.csv");

        CSVParser parser = fr.getCSVParser();
        weather w = new weather();
        w.lowestHumidityInFile(parser);

        parser = fr.getCSVParser();
        w.averageTemperatureInFile(parser);

        parser = fr.getCSVParser();
        w.averageTemperatureWithHighHumidityInFile(parser, 80);

        parser = fr.getCSVParser();
        w.coldestHourInFile(parser);
    }
}
