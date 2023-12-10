import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


public class GenerateGraph{
    public boolean check_edge_exists(Vertex u, Vertex v, Set<Edge> edges){
        for(Edge e: edges){
            if(e.u.x == u.x && e.v.x == v.x) {
                return true;
            }
        }
        return false;
    }
    public void generate_graph(int n, double r, int upperCap) throws FileNotFoundException {
        List<Vertex> vertices = new ArrayList<>();
        Set<Edge> edges = new HashSet<>();

        for(int i = 0; i < n; i++) {
            double x = Math.random();
            double y = Math.random();
            int node = i;
            vertices.add(new Vertex(x, y, node));
        }

        for(Vertex u: vertices) {
            for (Vertex v : vertices) {
                double d = Math.pow((u.x - v.x), 2) + Math.pow((u.y - v.y), 2);
                if ((u != v) && (d <= Math.pow(r, 2))) {
                    double random_int = Math.random(); //random() returns a double between 0 and 1
                    if (random_int < 0.5) {
                        if (!(check_edge_exists(u, v, edges))) {
                            edges.add(new Edge(u, v, (int) (Math.random() * upperCap + 1)));
                        }
                    } else {
                        if (!(check_edge_exists(v, u, edges))) {
                            edges.add(new Edge(v, u, (int) (Math.random() * upperCap + 1)));
                        }
                    }
                }
            }
        }
//        for(Edge edge : edges){
//            System.out.println("u: " + edge.u);
//            System.out.println("v: " + edge.v);
//            System.out.println("cap: " + edge.capacity);
//
//        }
//        System.out.println(vertices);

        Vertex src = vertices.get((int)(Math.random()*n));
        Vertex sink = BFS(src, vertices, edges);

        try (PrintWriter writer = new PrintWriter("generated_graph.csv")) {
            int i=-1;
            writer.println("Node,Coordinates,AdjNodes");
            for (Vertex v : vertices) {
                i++;
                //writer.print("Node "+ i + ": " + v.x + " " + v.y + " -> ");
                StringBuilder adjlist = new StringBuilder();
                for (Edge edge : edges) {
                    if (edge.u == v) {
                        //writer.print("(" + edge.v.x + " " + edge.v.y + " Cap:" + edge.capacity + ") ");
                        adjlist.append("(Node: ").append(edge.v.node).append(" Capacity: ").append(edge.capacity).append("), ");
                    }
                }
                writer.println(v.node + "," + v.x + " " + v.y + "," + adjlist);
            }
            writer.println();
            writer.println("Source: " + src.node);
            writer.println("Sink: " + sink.node);
        }
        return ;

   }

    private Vertex BFS(Vertex src, List<Vertex> vertices, Set<Edge> edges) {
            Map<Vertex, Integer> dist_from_source = new HashMap<>();
            Queue<Vertex> queue = new LinkedList<>();

            for (Vertex v : vertices) {
                dist_from_source.put(v, Integer.MAX_VALUE);
            }

            dist_from_source.put(src, 0);
            queue.add(src);

            while (!queue.isEmpty()) {
                Vertex current = queue.poll();

                for (Edge edge : edges) {
                    if (edge.u == current && dist_from_source.get(edge.v) == Integer.MAX_VALUE) {
                        dist_from_source.put(edge.v, dist_from_source.get(edge.u) + 1);
                        queue.add(edge.v);
                    }
                }
            }

            // Find the farthest node from the source
            int maxDist = 0;
            Vertex sink = src;
            for (Vertex v : vertices) {
                if (dist_from_source.get(v) > maxDist) {
                    maxDist = dist_from_source.get(v);
                    sink = v;
                }
            }

            return sink;
    }

    public static void main(String[] args) throws FileNotFoundException {
        GenerateGraph g = new GenerateGraph();
        g.generate_graph(5, 0.2, 2);

    }

}
