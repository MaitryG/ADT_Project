import java.io.*;
import java.util.*;

class FordFulkerson {
    public static void main(String[] args) throws IOException {
        GenerateGraph g = new GenerateGraph();
        g.generate_graph(5, 0.2, 5);

        List<Vertex> vertices = new ArrayList<>();
        Set<Edge> edges = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("generated_graph.csv"))) {
            String line;
            boolean isAdjacencyList = false;
            boolean skip=true;
            while ((line = reader.readLine()) != null) {
//                if (line.isEmpty()) {
//                    isAdjacencyList = true;
//                    continue;
//                }
                //Skip first column line
                if(skip){
                    skip = false;
                    continue;
                }

                if(line.startsWith("Source:")){
                    int sourceNode = Integer.parseInt(line.substring(8).trim());
                    continue;
                }
                if (line.startsWith("Sink:")) {
                    int sinkNode = Integer.parseInt(line.substring(6).trim());
                    continue;
                }


                if (!isAdjacencyList) {
                    String[] data = line.split(",");
                    int node = Integer.parseInt(data[0]);
                    String[] coordinates = data[1].split(" ");
                    double x = Double.parseDouble(coordinates[0]);
                    double y = Double.parseDouble(coordinates[1]);
                    vertices.add(new Vertex(x, y, node));
                }else{
                    String[] data = line.split(",");
                    int node = Integer.parseInt(data[0]);
                    System.out.println(node);
                    for (int i = 2; i < data.length; i++) {
                        String[] adjData = data[i].trim().split(" ");
                        int adjNode = Integer.parseInt(adjData[1]);
                        int capacity = Integer.parseInt(adjData[3]);
                        edges.add(new Edge(findVertex(node, vertices), findVertex(adjNode, vertices), capacity));
                    }
                }

            }

        }

        System.out.println("Vertices:");
        for (Vertex v : vertices) {
            System.out.println("Node: " + v.node + ", X: " + v.x + ", Y: " + v.y);
        }

        System.out.println("\nEdges:");
        for (Edge e : edges) {
            if(e.v!=null)
                System.out.println("From: " + e.u.node + " To: " + e.v.node + " Capacity: " + e.capacity);
        }
    }

    private static Vertex findVertex(int node, List<Vertex> vertices) {
        for (Vertex v : vertices) {
            if (v.node == node) {
                return v;
            }
        }
        return null;
    }
}