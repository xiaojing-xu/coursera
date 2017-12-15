package roadgraph;

import geography.GeographicPoint;

import java.util.Comparator;

public class MapEdge implements Comparable<MapEdge> {
    private GeographicPoint start;
    private GeographicPoint end;
    private String streetname;
    private String roadType;
    private double roadlength;

    public MapEdge(GeographicPoint start,
                   GeographicPoint end,
                   String streetname,
                   String roadType,
                   double length) {
        this.start = start;
        this.end = end;
        this.streetname = streetname;
        this.roadType = roadType;
        this.roadlength = length;
    }

    @Override
    public int compareTo(MapEdge o){
        if (o == null){
            return 1;
        }

        if (this.roadlength == o.roadlength){
            return 0;
        } else if (this.roadlength > o.roadlength) {
            return -1;
        } else {
            return 1;
        }
    }

    public GeographicPoint getStart() {
        return start;
    }

    public GeographicPoint getEnd() {
        return end;
    }

    public String getStreetname() {
        return streetname;
    }

    public double getRoadlength() {
        return roadlength;
    }

}
