import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.*;
import java.lang.*;

public class CapGraph implements Graph {
    protected HashSet<String> myVertexs = new HashSet<String>();
    protected HashMap<String, HashSet<Edge>> myEdges = new HashMap<String, HashSet<Edge>>();

    @Override
    public void addVertex(String s){
        if (!myVertexs.contains(s)){
            myVertexs.add(s);
        }
    }

    @Override
    public void addEdge (String from, String to, double weight){
        Edge e = new Edge();
        e.to = to;
        e.weight = Math.abs(weight);
        if (!myEdges.keySet().contains(from)){
            HashSet<Edge> E = new HashSet<>();
            E.add(e);
            myEdges.put(from,E);
        } else {
            myEdges.get(from).add(e);
        }
    }

    public void printCapGraph(){
        System.out.println("######### vertexs ############");
        for (String s: myVertexs){
            System.out.println(s);
        }
        System.out.println("#########  Edges ############");
        for (String s: myEdges.keySet()){
            System.out.println("FFFFFFFF from " + s);
            for (Edge e: myEdges.get(s)){
                System.out.println(e.to + " : " + e.weight);
            }
            System.out.println("********");
        }
    }

    @Override
    public CapGraph getEgonetFrom(String center){
        CapGraph g = new CapGraph();
        if (!myVertexs.contains(center)){
            System.out.println("Center from input not found");
            return g;
        }

        g.addVertex(center);
        HashSet<Edge> directConectEdge = myEdges.get(center);
        HashSet<String> directTo       = new HashSet<String>();
        if (directConectEdge !=null){
            for (Edge i: directConectEdge){
                directTo.add(i.to);
                g.addEdge(center, i.to, i.weight);
            }
        }

        if (directConectEdge !=null){
            for (Edge i: directConectEdge){
                if (myEdges.get(i.to) != null) {
                    for (Edge j : myEdges.get(i.to)){
                        if (directTo.contains(j.to)){
                            g.addVertex(i.to);
                            g.addEdge(i.to, j.to, j.weight);
                        }
                    }
                }
            }
        }
        return g;
    }


    @Override
    public List<Graph> getSCCs(){
        List<Graph> result = new LinkedList<>();

        Stack<String> finished_step1 = DFS(this, myVertexs);

        CapGraph transpose = transposeGraph(this);

        result.addAll(dfsSCC(transpose, finished_step1));

        for (Graph cg: result) {
            CapGraph c = (CapGraph) cg;
            System.out.println(c.myVertexs);
        }
        System.out.println("************************");
        return result;
    }

    public Stack<String> DFS(CapGraph G, HashSet<String> myVertexs){
        HashSet<String> visited   = new HashSet<String>();
        Stack<String>  finished   = new Stack<String>();

        Stack<String>  vertices   = new Stack<String>();
        vertices.addAll(myVertexs);

        while (!vertices.isEmpty()){
            String v = vertices.pop();
            List<String> sccList = new ArrayList<String>();

            if (!visited.contains(v)){
                sccList.add(v);
                DFSVisit(G, v, visited,finished);
            }
        }
        return finished;
    }

    public void DFSVisit(CapGraph G, String v, HashSet<String> visited, Stack<String> finished){
        visited.add(v);
        if (G.myEdges.get(v) != null){
            for (Edge neighbor : G.myEdges.get(v)){
                if (! visited.contains(neighbor.to)){
                    DFSVisit(G, neighbor.to, visited,finished);
                }
            }
        }
        finished.push(v);
        return;
    }

    public CapGraph transposeGraph(CapGraph G){
        CapGraph transpose = new CapGraph();
        transpose.myVertexs = G.myVertexs;
        for (String from : G.myEdges.keySet()){
            for (Edge e : G.myEdges.get(from)){
                Edge et = new Edge();
                et.to   = from;
                et.weight = e.weight;
                if (!transpose.myEdges.keySet().contains(e.to)){
                    HashSet<Edge> edge = new HashSet<>();
                    edge.add(et);
                    transpose.myEdges.put(e.to,edge);
                } else {
                    transpose.myEdges.get(e.to).add(et);
                }
            }
        }
        return transpose;
    }

    public List<Graph> dfsSCC (CapGraph g, Stack<String> vertices){
        HashSet<String> visit = new HashSet<>();
        List<Graph> sccList    = new ArrayList<>();

        while (!vertices.isEmpty()){
            String v = vertices.pop();

            if (!visit.contains(v)){
                CapGraph  scc = new CapGraph();
                scc.addVertex(v);
                dfsVisitSCC(g, v, visit, scc);
                sccList.add(scc);
            }
        }
        return sccList;
    }

    private void dfsVisitSCC(CapGraph g, String v, HashSet<String> visit, CapGraph scc){
        visit.add(v);
        //System.out.println(g.myEdges.get(v));
        //System.out.println("****###");
        if (g.myEdges.get(v) !=null){
            for (Edge e: g.myEdges.get(v)){
                if (!visit.contains(e.to)){
                    dfsVisitSCC(g, e.to, visit,scc);
                }
            }
        }
        scc.addVertex(v);
        return;
    }
}
