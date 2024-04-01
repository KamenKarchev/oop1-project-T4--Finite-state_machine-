public interface IGraph {
    // G = (V"noedes", E)

    boolean addNode(String nodeName);

    boolean addEdge(String node1Name, String node2Name);

    boolean removeEdge(String node1Name, String node2Name);
    boolean removeNode(String nodeName);

    int getNodeCount();

    int getEdgesCount();

    int getEdgesCount(String nodeName);

    boolean hasNode(String nodeName);

    boolean hasEdge(String node1Name, String node2Name);
}
