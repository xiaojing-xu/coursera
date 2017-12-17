import edu.duke.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;

    public LogAnalyzer(){
        records = new ArrayList<LogEntry>();
    }

    public void readFile (String filename){
        FileResource fr = new FileResource(filename);
        for (String line: fr.lines()){
            LogEntry le = new WebLogParser().parseEntry(line);
            records.add(le);
        }
    }

    public void printAll(){
        for (LogEntry le: records){
            System.out.println(le);
        }
    }

    public void printUniqueStatus(){
        HashSet<Integer> status = new HashSet<Integer>();
        for (LogEntry le: records){
            if (!status.contains(le.getStatusCode())){
                status.add(le.getStatusCode());
            }
        }
        System.out.println(status);
    }

    public int countUniqueIPs(){
        HashSet<String> uniqueip = new HashSet<String>();
        for (LogEntry le: records){
            String ip = le.getIpAddress();
            if (!uniqueip.contains(ip)){
                uniqueip.add(ip);
            }
        }
        System.out.println("There are " + uniqueip.size() + " unique ip");
        return uniqueip.size();
    }

    public HashSet<String> uniqueIPVisitOneDay(String someday){
        HashSet<String> uniqueip = new HashSet<String>();
        for (LogEntry le: records){
            String whole = le.getAccessTime().toString();
            String date = whole.substring(8, 10);
            String month = whole.substring(4, 7);
            if (someday.substring(0,3).equals(month) && someday.substring(4,6).equals(date)){
                String ip =  le.getIpAddress();
                if (!uniqueip.contains(ip)){
                    uniqueip.add(ip);
                }
            }
        }
        System.out.println(" On " + someday + ", there are  " + uniqueip.size() + " unique ip visited");
        return uniqueip;
    }

    public int countUniqueIPsInRange(int low, int high){
        HashSet<String> uniqueip = new HashSet<String>();
        for (LogEntry le: records){
            int whole = le.getStatusCode();
            if (whole >= low && whole <= high){
                if (!uniqueip.contains(le.getIpAddress())){
                    uniqueip.add(le.getIpAddress());
                }
            }
        }
        System.out.println("There are " + uniqueip.size() + " unique ip visit bewteen " + low + " and " + high);
        return uniqueip.size();
    }


    public void printAllHigherThanNum(int num) {
        HashSet<Integer> uniquestatus = new HashSet<Integer>();
        for (LogEntry le:records) {
            int whole = le.getStatusCode();
            if (whole> num) {
                if (!uniquestatus.contains(whole)) uniquestatus.add(whole);
            }
        }
        for (int status: uniquestatus) {
            System.out.println(status+"\t");
        }
    }

    public HashMap<String, Integer> countVisitsPerIP() {
        HashMap<String, Integer> uniqip = new HashMap<String, Integer>();
        for (LogEntry le:records) {
            String ip = le.getIpAddress();
            if (!uniqip.containsKey(ip)) {
                uniqip.put(ip, 1);
            }
            else uniqip.put(ip, uniqip.get(ip)+1);
        }
        return uniqip;
    }

    public int mostNumberVisitsByIP(HashMap<String, Integer> uniqip) {
        int max=0;
        int current;
        for (String ip: uniqip.keySet()) {
            current = uniqip.get(ip);
            if (max < current) max = current;
        }
        return max;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> uniqip) {
        int max = mostNumberVisitsByIP(uniqip);
        ArrayList<String> show = new ArrayList<String>();
        int current;
        for (String ip: uniqip.keySet()) {
            current = uniqip.get(ip);
            if (max == current) show.add(ip);
        }
        return show;
    }

    public HashMap<String, ArrayList<String>> iPsForDays() {
        HashMap<String, ArrayList<String>> eachday = new HashMap<String, ArrayList<String>>();
        for (LogEntry le:records) {
            String whole = le.getAccessTime().toString();
            String date = whole.substring(8, 10);
            String month = whole.substring(4, 7);
            String time = month + " " + date;
            String ip = le.getIpAddress();
            if (!eachday.containsKey(time)) {
                eachday.put(time, new ArrayList<String>());
            }
            eachday.get(time).add(ip);
        }
        return eachday;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> eachday) {
        String mosttime=null;
        int most = 0;
        int current;
        for (String time: eachday.keySet()) {
            current = eachday.get(time).size();
            if (most < current) {
                most = current;
                mosttime = time;
            }
        }
        return mosttime;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> eachday, String day) {
        ArrayList<String> ip = new ArrayList<String>();
        HashMap<String, Integer> eachip = new HashMap<String, Integer>();
        ArrayList<String> mostthatday = new ArrayList<String>();
        int mostnumber = 0;
        int currentnumber;
        for (String time: eachday.keySet()) {
            if (time.equals(day)) {
                ip = eachday.get(time);
            }
        }
        for (int k = 0; k < ip.size(); k++) {
            if (!eachip.containsKey(ip.get(k))) {
                eachip.put(ip.get(k), 1);
            }
            else eachip.put(ip.get(k), eachip.get(ip.get(k))+1);
        }
        mostthatday = iPsMostVisits(eachip);
        return mostthatday;
    }
}
