import edu.duke.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.HashMap;
import java.util.*;
import java.text.*;

public class Path {
    public static List<String> Dijkstra (String from, String to, CapGraph graph){
        // Initialize
        PriorityQueue<MapNode> pq = new PriorityQueue<>();
        HashSet<MapNode> visit    = new HashSet<>();
        HashMap<String, MapNode> mapnodes = new HashMap<String, MapNode>();


        // Set start point and end point
        MapNode Start = new MapNode(graph,from);
        Start.distanceFromStart = 0;
        pq.add(Start);
        visit.add(Start);
        mapnodes.put(from,Start);


        while(!pq.isEmpty()){
            MapNode curr = pq.poll();
            if (!visit.contains(curr)){
                visit.add(curr);
                pq.add(curr);
            }

            if (curr.current.equals(to)){
                break;
            }

            if (curr.edges == null || curr.edges.size() < 1){
                break;
            }

            for (Edge me: curr.edges){
                MapNode next = new MapNode(graph, me.to);
                if (!visit.contains(next)){
                    double distance = curr.distanceFromStart + me.weight;
                    if (distance < next.distanceFromStart){
                        next.distanceFromStart = distance;
                        next.prev = curr.current;
                        pq.add(next);
                    }
                    mapnodes.put(next.current,next);
                }
            }
        }

        if (mapnodes.get(to).prev == null){
            System.out.println("No path has been found");
            return null;
        }

        List<String> path = new LinkedList<>();
        path.add(to);

        String gp = mapnodes.get(to).prev;
        while (!gp.equals(from)){
            path.add(gp);
            gp = mapnodes.get(gp).prev;
        }
        path.add(Start.current);
        Collections.reverse(path);
        System.out.println("##############");
        System.out.println(path);
        return path;
    }
}
