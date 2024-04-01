import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class EdgeDenomination {
    private Map<String, List<Character>> denominations;

    public EdgeDenomination() {
        this.denominations = new HashMap<>();
    }

    abstract boolean hasEdge(String letter,String node1Name, String node2Name);

    abstract void addEdge(String letter,String node1Name, String node2Name);

    abstract void removeEdge(String letter,String node1Name, String node2Name);
}
