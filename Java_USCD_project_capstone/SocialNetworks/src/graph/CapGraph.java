/**
 * 
 */
package graph;

import java.util.*;

/**
 * @author xiaojing here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {
	// XX: create private field to storge the vertex and edges
	protected HashSet<Integer> myVertexs = new HashSet<>();
	protected HashMap<Integer,HashSet<Integer>> myEdges = new HashMap<Integer,HashSet<Integer>>();

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!myVertexs.contains(num)){
			myVertexs.add(num);
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (!myEdges.keySet().contains(from)){
			HashSet<Integer> To = new HashSet<>();
			To.add(to);
			myEdges.put(from, To);
		} else {
			myEdges.get(from).add(to);
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		CapGraph g = new CapGraph();
		if (!myVertexs.contains(center)){
			return g;
		}

		g.addVertex(center);
		HashSet<Integer> directConectVertex = myEdges.get(center);
		//System.out.println(directConectVertex);
		for (Integer i : directConectVertex){
			g.addVertex(i);
			g.addEdge(center,i);
			for (Integer j: myEdges.get(i)){
				if (directConectVertex.contains(j)){
					g.addEdge(i,j);
				}
			}
		}

		//g.printCapGraph();
		//System.out.println(g.myVertexs);
		return g;
	}

	public void printCapGraph(CapGraph cg){
		for (Integer i : myEdges.keySet()){
			System.out.println(i + " : " + myEdges.get(i));
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		List<Graph> result = new LinkedList<Graph>();

		Stack<Integer> finished_step1 = DFS(this, myVertexs);

		CapGraph transpose = transposeGraph(this);

		result.addAll(dfsSCC(transpose, finished_step1));

		for (Graph cg: result) {
			CapGraph c = (CapGraph) cg;
			System.out.println(c.myVertexs);
		}
		System.out.println("************************");
		return result;
	}


	public Stack<Integer> DFS(CapGraph G, HashSet<Integer> myVertexs){
		HashSet<Integer> visited   = new HashSet<Integer>();
		Stack<Integer>  finished   = new Stack<Integer>();

		Stack<Integer>  vertices   = new Stack<Integer>();
		vertices.addAll(myVertexs);

		while (!vertices.isEmpty()){
			Integer v = vertices.pop();
			List<Integer> sccList = new ArrayList<Integer>();

			if (!visited.contains(v)){
				sccList.add(v);
				DFSVisit(G, v, visited,finished);
			}
		}
		return finished;
	}

	public void DFSVisit(CapGraph G, Integer v, HashSet<Integer> visited, Stack<Integer> finished){
		visited.add(v);
		if (G.myEdges.get(v) != null){
			for (Integer neighbor : G.myEdges.get(v)){
				if (! visited.contains(neighbor)){
					DFSVisit(G, neighbor, visited,finished);
				}
			}
		}
		finished.push(v);
		return;
	}


	public CapGraph transposeGraph(CapGraph G){
		CapGraph transpose = new CapGraph();
		transpose.myVertexs = G.myVertexs;
		for (Integer from : G.myEdges.keySet()){
			for (Integer to : G.myEdges.get(from)){
				if (!transpose.myEdges.keySet().contains(to)){
					HashSet<Integer> To = new HashSet<>();
					To.add(from);
					transpose.myEdges.put(to,To);
				} else {
					transpose.myEdges.get(to).add(from);
				}
			}
		}
		return transpose;
	}


	public List<Graph> dfsSCC (CapGraph g, Stack<Integer> vertices){
		HashSet<Integer> visit = new HashSet<>();
		List<Graph> sccList    = new ArrayList<>();

		while (!vertices.isEmpty()){
			Integer v = vertices.pop();

			if (!visit.contains(v)){
				CapGraph  scc = new CapGraph();
				scc.addVertex(v);
				dfsVisitSCC(g, v, visit, scc);
				sccList.add(scc);
			}
		}
		return sccList;
	}

	private void dfsVisitSCC(CapGraph g, Integer v, HashSet<Integer> visit, CapGraph scc){
		visit.add(v);
		//System.out.println(g.myEdges.get(v));
		//System.out.println("****###");
		if (g.myEdges.get(v) !=null){
			for (Integer n: g.myEdges.get(v)){
				if (!visit.contains(n)){
					dfsVisitSCC(g, n, visit,scc);
				}
			}
		}
		scc.addVertex(v);
		return;
	}


	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		HashMap<Integer, HashSet<Integer>> result = new HashMap<Integer, HashSet<Integer>>();
		result.putAll(myEdges);

		for (Integer i : myVertexs){
			if (!myEdges.keySet().contains(i)){
				HashSet<Integer> To_null = new HashSet<>();
				result.put(i, To_null);
			}
		}
		return result;
	}
}
