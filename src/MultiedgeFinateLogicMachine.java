import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiedgeFinateLogicMachine extends MultiedgeDirectedGraph{
    private Map<String, List<Character>> denominations;

    public MultiedgeFinateLogicMachine() {
        super();
        denominations = new HashMap<>();
    }

    public boolean hasEdge(String letter, String node1Name, String node2Name){
        return true;
    }

    public void addEdge(Character letter,String node1Name, String node2Name){
        List<Character> tempList = new ArrayList<>();
        tempList.add(letter);
        denominations.put(node1Name.concat(node2Name),tempList);
        super.addEdge(node1Name,node2Name);
    }

    public void removeEdge(String letter,String node1Name, String node2Name){

    }
}
