package graph;

import util.GraphLoader;

import java.util.HashMap;
import java.util.HashSet;

public class main {
    public static void main(String[] args) {
         CapGraph cg    = new CapGraph();
         GraphLoader.loadGraph(cg, "/Users/xiaojingxu/Downloads/SocialNetworks/data/scc/test_8.txt");
         //System.out.println(cg.exportGraph());
         //HashMap<Integer, HashSet<Integer>> result = cg.getEgonet(0).exportGraph();
         //System.out.println(cg.myEdges.get(0));
         //cg.getEgonet(0);
        cg.getSCCs();
    }
}
