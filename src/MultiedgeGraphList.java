import java.util.*;

public class MultiedgeGraphList implements IGraph {
    // G = (V"noedes", E)
    private Map<String, List<String>> adj;

    public MultiedgeGraphList() {
        adj = new HashMap<>();
    }

    @Override
    public void addNode(String nodeName) {
        adj.put(nodeName,new ArrayList<>());
    }

    @Override
    public void addEdge(String node1Name, String node2Name) {
        if(hasNode(node1Name) && hasNode(node2Name)) {
            adj.get(node1Name).add(node2Name);
            adj.get(node2Name).add(node1Name);
        }
    }

    @Override
    public void removeEdge(String node1Name, String node2Name) {
        adj.get(node1Name).remove(node2Name);
        adj.get(node2Name).remove(node1Name);
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
