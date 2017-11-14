package roadgraph;

import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapNode implements Comparable<MapNode>{
    private GeographicPoint point;
    private List<MapEdge> edges;
    public double distanceFromStart;
    public GeographicPoint prev ;


    public MapNode(GeographicPoint point){
        this.point = point;
        this.edges = new ArrayList<>();
        this.distanceFromStart = Double.POSITIVE_INFINITY;
        this.prev = null;
    }

    public GeographicPoint getPoint() {
        return point;
    }

    public void addEdge(MapEdge mapEdge){
        this.edges.add(mapEdge);
    }

    public List<MapEdge> getEdges() {
        return edges;
    }

    @Override
    public int compareTo(MapNode o){
        if (o == null){
            return 1;
        }

        if (this.distanceFromStart == o.distanceFromStart){
            return 0;
        } else if (this.distanceFromStart > o.distanceFromStart) {
            return 1;
        } else {
            return -1;
        }
    }
}
