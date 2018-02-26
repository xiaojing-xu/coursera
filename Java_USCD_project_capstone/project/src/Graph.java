import edu.duke.FileResource;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.*;

public interface Graph {
    public void addVertex(String s);
    public void addEdge(String from, String to, double weight);
    public Graph getEgonetFrom(String s);
    public List<Graph> getSCCs();
}
