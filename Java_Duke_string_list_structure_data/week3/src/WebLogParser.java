import edu.duke.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.*;
import java.text.*;

public class WebLogParser {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:kk:mm:ss Z", Locale.US);

    public  String munchTo(StringBuilder sb, String delim) {
        int x = sb.indexOf(delim);
        if (x == -1) {
            x = sb.length();
        }
        String ans = sb.substring(0,x);
        sb.delete(0, x + delim.length());
        return ans;
    }

    public  LogEntry parseEntry(String line) {
        //110.76.104.12 - - [30/Sep/2015:07:47:11 -0400] "GET //favicon.ico HTTP/1.1" 200 3426
        StringBuilder sb = new StringBuilder(line);
        String ip = munchTo(sb, " ");
        munchTo(sb, " "); //ignore -
        munchTo(sb, " ["); //ignore -, and eat the leading [
        String dateStr = munchTo(sb, "] \""); //]-space is intentional: eat both
        Date date = parseDate(dateStr);
        String request = munchTo(sb, "\" "); // quote-space is intentional: eat both
        String statusStr = munchTo(sb, " ");
        int status = Integer.parseInt(statusStr);
        String byteStr = munchTo(sb, " ");
        int bytes = Integer.parseInt(byteStr);
        return new LogEntry(ip, date, request, status, bytes);
    }

    public Date parseDate(String dateStr) {
        ParsePosition pp = new ParsePosition(0);
        return  dateFormat.parse(dateStr, pp);
    }
}
