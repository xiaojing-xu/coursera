import edu.duke.*;

import java.util.Date;

public class LogEntry {
    private String ipAddress;
    private Date   accessTime;
    private String request;
    private int    statusCode;
    private int    bytesRetured;

    public LogEntry(String ip, Date time, String req, int status, int bytes){
        ipAddress   = ip;
        accessTime  = time;
        request     = req;
        statusCode  = status;
        bytesRetured= bytes;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    public Date getAccessTime() {
        return accessTime;
    }
    public String getRequest() {
        return request;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public int getBytesReturned() {
        return bytesRetured;
    }

    public String toString(){
        return ipAddress + " " + accessTime + " " + request + " " + statusCode + " " + bytesRetured;
    }

}
