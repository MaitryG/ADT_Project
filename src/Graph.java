import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    List<Vertex> vertices = new ArrayList<>();
    Set<Edge> edges = new HashSet<>();


    public Graph(List<Vertex> vertices, Set<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }
}
