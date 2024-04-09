import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiedgeDirectedGraph implements IGraph {
    // G = (V"noedes", E)
    private Map<String, List<String>> adj;

    public MultiedgeDirectedGraph() {
        adj = new HashMap<>();
    }

    @Override
    public boolean addNode(String nodeName) {
        if (!hasNode(nodeName)) {
            adj.put(nodeName, new ArrayList<>());
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(String node1Name, String node2Name) {
        if (!hasEdge(node1Name,node2Name)) {
            adj.get(node1Name).add(node2Name);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(String node1Name, String node2Name) {
        return adj.get(node1Name).remove(node2Name);
    }

    @Override
    public boolean removeNode(String nodeName) {
        if (hasNode(nodeName)) {
            adj.remove(nodeName);
            adj.forEach((k, v) -> v.remove(nodeName));
            return true;
        }
        return false;
    }

    @Override
    public int getNodeCount() {
        return adj.size();
    }

    @Override
    public int getEdgesCount() {
        int sum = 0;
        for (int i = 0; i < adj.size(); i++) {
            sum += adj.get(i).size();
        }
        return sum;
    }

    @Override
    public int getEdgesCount(String nodeName) {
        return adj.get(nodeName).size();
    }

    @Override
    public boolean hasNode(String nodeName) {
        return adj.containsKey(nodeName);
    }

    @Override
    public boolean hasEdge(String node1Name, String node2Name) {
        return adj.get(node1Name).contains(node2Name);
    }
}
