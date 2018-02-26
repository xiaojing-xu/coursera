import edu.duke.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.*;
import java.text.*;

public class MapNode implements Comparable<MapNode>{
    protected String current;
    protected double distanceFromStart;
    protected HashSet<Edge> edges;
    protected String prev;

    public MapNode(CapGraph g, String current){
        this.current = current;
        this.distanceFromStart = Double.POSITIVE_INFINITY;
        this.prev  = null;
        if (g.myEdges.get(current) == null || g.myEdges.get(current).size() < 1){
            this.edges = new HashSet<Edge>();
        } else {
           this.edges = g.myEdges.get(current);
        }
    }

    public static List<MapNode> convertGraphToMapNode (CapGraph g){
        List<MapNode> result = new LinkedList<>();
        if (g.myVertexs == null || g.myVertexs.size() < 1){
            return result;
        }

        for (String current: g.myVertexs){
            MapNode m = new MapNode(g,current);
            result.add(m);
        }
        return result;
    }


    public int compareTo(MapNode o){
        if (o == null){
            return 1;
        }

        if (this.distanceFromStart == o.distanceFromStart){
            return 0;
        } else if (this.distanceFromStart > o.distanceFromStart){
            return 1;
        } else {
            return -1;
        }
    }
}
