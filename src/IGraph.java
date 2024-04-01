public interface IGraph {
    // G = (V"noedes", E)
     void addNode(String nodeName);

    void addEdge(String node1Name, String node2Name);

    void removeEdge(String node1Name, String node2Name);

    int getNodeCount();

    int getEdgesCount();

    int getEdgesCount(String nodeName);

    boolean hasNode(String nodeName);

    boolean hasEdge(String node1Name, String node2Name);
}
