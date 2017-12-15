/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.*;
import java.util.function.Consumer;

import basicgraph.Graph;
import geography.GeographicPoint;
import util.GraphLoader;

import javax.xml.stream.Location;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private HashMap<GeographicPoint, MapNode> mapNodes;
	private int numberOfEdges = 0;
	/** 
	 * Create a new empty MapGraph 
	 */

	public MapGraph()
	{
		//TODO: Add your member variables here in WEEK 3
		this.mapNodes = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3

		return mapNodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return mapNodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numberOfEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if (location == null || mapNodes.containsKey(location)){
			return false;
		}

		this.mapNodes.put(location, new MapNode(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3

		if (mapNodes.containsKey(from) && mapNodes.containsKey(to)){
			MapEdge newEdge = new MapEdge(from, to, roadName, roadType, length);
			this.mapNodes.get(from).addEdge(newEdge);
			numberOfEdges +=1;
		} else {
			throw new IllegalArgumentException("From or to not on the map");
		}

	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		// Hook for visualization.  See writeup.
		// nodeSearched.accept(next.getLocation());

		class PointWithPrev {
			public MapNode node;
			public PointWithPrev prev;
		}

		Queue<PointWithPrev> queueNodes = new LinkedList<PointWithPrev>();
		Set<GeographicPoint> visited = new HashSet<>();

		PointWithPrev current = new PointWithPrev();
		current.node = mapNodes.get(start);
		current.prev = null;
		queueNodes.add(current);

		PointWithPrev result = null;

		while (!queueNodes.isEmpty()) {
			current = queueNodes.poll();

			if(!visited.contains(current.node.getPoint())) {
				visited.add(current.node.getPoint());

				if(pointEquals(current.node.getPoint(), goal)) {
					result = current;
					break;
				}

				for(MapEdge edge:current.node.getEdges()) {
					GeographicPoint end = edge.getEnd();

					if(!visited.contains(end)) {
						PointWithPrev next = new PointWithPrev();
						next.node = mapNodes.get(end);
						next.prev = current;
						queueNodes.add(next);
					}
				}
			}
		}

		if(result == null) {
			return null;
		}

		nodeSearched.accept(goal);
		List<GeographicPoint> path = new ArrayList<>();
		while (result != null) {
			path.add(result.node.getPoint());
			result = result.prev;
		}

		Collections.reverse(path);
		return path;
	}

	private boolean pointEquals(GeographicPoint point1, GeographicPoint point2) {
		return (point1.getX() == point2.getX() && point1.getY() == point2.getY());
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		// Initialize
		PriorityQueue<MapNode> pq = new PriorityQueue<MapNode>();
		HashSet<MapNode> visit    = new HashSet<MapNode>();

		// Set start point and end point
		MapNode Start  = mapNodes.get(start);
		Start.distanceFromStart = 0;
		MapNode Goal  = mapNodes.get(goal);
		pq.add(Start);
		visit.add(Start);

		while(!pq.isEmpty()){
			MapNode curr = pq.poll();
			if (! visit.contains(curr)){
				visit.add(curr);
				pq.add(curr);
			}

			if (pointEquals(curr.getPoint(), goal)){
				break;
			}

			for (MapEdge me: curr.getEdges()){
				MapNode next = mapNodes.get(me.getEnd());
				if (!visit.contains(next)){
					double distance = curr.distanceFromStart + me.getRoadlength();
					if (distance < next.distanceFromStart){
						next.distanceFromStart = distance;
						next.prev = curr.getPoint();
						pq.add(next);
					}
				}
			}
		}

		if (Goal.prev == null){
			return null;
		}

		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint gp = goal;
		while (!pointEquals(start,gp)){
			path.add(gp);
			gp = mapNodes.get(mapNodes.get(gp).prev).getPoint();
		}
		path.add(start);
		Collections.reverse(path);

		return path;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		// Initialize
		PriorityQueue<MapNode> pq = new PriorityQueue<MapNode>();
		HashSet<MapNode> visit    = new HashSet<MapNode>();

		// Set start point and end point
		MapNode Start  = mapNodes.get(start);
		Start.distanceFromStart = 0;
		MapNode Goal  = mapNodes.get(goal);
		pq.add(Start);
		visit.add(Start);

		//
		while(!pq.isEmpty()){
			MapNode curr = pq.poll();
			if (!visit.contains(curr)){
				visit.add(curr);
				pq.add(curr);
			}

			if (pointEquals(curr.getPoint(), goal)){
				break;
			}

			for (MapEdge me: curr.getEdges()){
				MapNode next = mapNodes.get(me.getEnd());
				if (! visit.contains(next)){
					double g_dis    = curr.distanceFromStart + me.getRoadlength();
					double h_dis    = curr.getPoint().distance(goal);
					double f_dis    = g_dis + h_dis;
					if (f_dis < next.distanceFromStart){
						next.distanceFromStart = g_dis;
						next.prev = curr.getPoint();
						pq.add(next);
					}
				}
			}

		}

		if (Goal.prev == null){
			return null;
		}

		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint gp = goal;
		while (!pointEquals(start,gp)){
			path.add(gp);
			gp = mapNodes.get(mapNodes.get(gp).prev).getPoint();
		}

		path.add(start);
		Collections.reverse(path);
		return path;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */

		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);

		
		
		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
	}
}
