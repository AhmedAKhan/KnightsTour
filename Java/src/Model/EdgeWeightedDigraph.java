package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public class EdgeWeightedDigraph {

    private final int V;                  // number of vertices
    private int E;                      // number of edges
    private ArrayList<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<DirectedEdge>();
    }

    public int V() {
        return V;
    }
    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }
    public ArrayList<DirectedEdge> adj(int v) {
        return adj[v];
    }
    public int lengthOfAdj() {
        return adj.length;
    }
    public int legnthOfAtAt(int v) {
        return adj[v].size();
    }
    public void removeEdge(DirectedEdge edge) {
        for (int v = 0; v < V; v++) {
//            System.out.println("adj[v]: " + adj[v]);
//            for (Module.DirectedEdge e : adj[v])
            for (int j = 0; j < adj[v].size(); j++)
                if (adj[v].get(j) == edge) adj[v].remove(edge);
        }
    }
    public Iterable<DirectedEdge> edges() {
        Collection<DirectedEdge> bag = new ArrayList<DirectedEdge>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge e : adj[v])
                bag.add(e);
        return bag;
    }

    @Override public String toString(){
        String s = "";
        for(int i = 0; i < adj.length; i++)
        {s += i+": "+adj[i].toString(); s += "\n"; }
        return s;
    }

    public static void main(String[] args){

        //makes an empty graph and makes edges
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(5);
        DirectedEdge a = new DirectedEdge(0,1,1);
        DirectedEdge b = new DirectedEdge(1,2,1);
        DirectedEdge c = new DirectedEdge(2,3,1);
        DirectedEdge d = new DirectedEdge(3,4,1);
        DirectedEdge e = new DirectedEdge(4,1,1);
        DirectedEdge f = new DirectedEdge(4,2,1);

        //adds the edges to the graph
        g.addEdge(a);
        g.addEdge(b);
        g.addEdge(c);
        g.addEdge(d);
        g.addEdge(e);
        g.addEdge(f);

        //prings the graph out
        System.out.println("g: " + g.toString());

        //gets the shortest path
        DijkstraSP path = new DijkstraSP(g, 0);
        Stack<DirectedEdge> it = path.pathTo(4);
        //prints the shortest path
        while(!it.isEmpty())
            System.out.println(it.pop());


    }
}