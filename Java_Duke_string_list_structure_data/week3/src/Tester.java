import edu.duke.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.*;
import java.text.*;

public class Tester {
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        la.printAll();
    }

    public void testUniqueIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("/Users/xiaojingxu/Desktop/weblog2_log");
        //int number = la.countUniqueIPs();
        //System.out.println("The number of unique IPs is "+number+"\t");
        //la.printAllHigherThanNum(400);
        //la.uniqueIPVisitOneDay("Sep 27");
        //la.countUniqueIPsInRange(200,299);
        //System.out.println(la.mostNumberVisitsByIP(la.countVisitsPerIP()));
        //System.out.println(la.iPsMostVisits(la.countVisitsPerIP()));
        //System.out.println(la.dayWithMostIPVisits(la.iPsForDays()));
        System.out.println(la.iPsWithMostVisitsOnDay(la.iPsForDays(), "Sep 30"));
    }

    public void testuniqueIPVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashSet<String> uniqueip = la.uniqueIPVisitOneDay("Sep 27");
        System.out.println("The number of unique IPs on Sep 27 is "+uniqueip.size()+"\t");
    }

    public void testcountUniqueIPsInRange() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        int uniqueip = la.countUniqueIPsInRange(400, 499);
        System.out.println("The number of unique IPs is "+uniqueip+"\t");
    }

    public void printAllHigherThanNum() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        la.printAllHigherThanNum(400);
    }

    public void testmostNumberVisitsByIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> uniqip = la.countVisitsPerIP();
        int most = la.mostNumberVisitsByIP(uniqip);
        System.out.println(most);
    }

    public void testiPsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> uniqip = la.countVisitsPerIP();
        ArrayList<String> result = la.iPsMostVisits(uniqip);
        System.out.println(result.get(0));
    }

    public void testiPsForDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> eachday = la.iPsForDays();
        for (String time: eachday.keySet()) {
            if (time.equals("Sep 30")) System.out.println(eachday.get(time).size());
        }
    }

    public void testdayWithMostIPVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> eachday = la.iPsForDays();
        String mosttime = la.dayWithMostIPVisits(eachday);
        System.out.println(mosttime);
    }

    public void testiPsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> eachday = la.iPsForDays();
        ArrayList<String> result = la.iPsWithMostVisitsOnDay(eachday, "Sep 30");
        for (int k = 0; k < result.size(); k++) {
            System.out.println(result.get(k));
        }
    }

    public static void main(String[] args) {
        Tester t = new Tester();
        t.testUniqueIP();
    }
}
