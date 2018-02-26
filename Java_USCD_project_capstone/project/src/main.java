public class main {
    public static void main(String[] args) {
        //Graph        g  = new Graph();
        ResultLoader rl = new ResultLoader();
        rl.loadFile("/Users/xiaojingxu/Downloads/project/result.csv");

        // class includes [pss, clinical, personality, polygenic, digestion, health, satisfaction, vitals, lifestyle]

        CapGraph g = new CapGraph();
        //Graphload.printClass(rl.allRecords);
        Graphload.LoadOmicRecords(g, rl.allRecords, 0.05, 0.05, true);
        //g.printCapGraph();

        //Graphload.LoadSubClassRecords(g, rl.allRecords,0.05, 0.05, "clinical","health");
        //g.printCapGraph();

        //CapGraph g1 = g.getEgonetFrom("lifestyle_breakfast_enum");
        //g1.printCapGraph();

        //Path.Dijkstra("lifestyle_smoke_in_past_enum", "personality_mess_enum", g1);

        //System.out.println("********** ############### ************** ###################");
        //g.getSCCs();
    }
}