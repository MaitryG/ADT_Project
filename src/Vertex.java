import java.util.ArrayList;
import java.util.List;

public class Vertex{
    public double x,y;

    public List<Edge> edges;

    public int node;
    Vertex(double x, double y, int node){
        this.x = x;
        this.y = y;
        this.node = node;
        this.edges = new ArrayList<>();

    }
}