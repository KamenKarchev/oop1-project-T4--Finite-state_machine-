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

    public boolean hasEdge(Character letter, String node1Name, String node2Name){
        return denominations.get(node1Name.concat(node2Name)).contains(letter);
    }

    public boolean addEdge(Character letter,String node1Name, String node2Name){
        String concat = node1Name.concat(node2Name);
        if (super.addEdge(node1Name,node2Name)) {
            List<Character> tempList = new ArrayList<>();
            tempList.add(letter);
            denominations.put(concat, tempList);
            return true;
        }
        else if(!denominations.get(concat).contains(letter)) {
            denominations.get(concat).add(letter);
        }
        return false;
    }

    public boolean removeEdge(Character letter,String node1Name, String node2Name){
        List<Character> tempList = denominations.get(node1Name.concat(node2Name));
        if (tempList != null) {
            if (tempList.size() == 1) {
                if(tempList.remove(letter)){
                    denominations.remove(node1Name.concat(node2Name));
                    return removeEdge(node1Name,node2Name);
                }
                else {
                    return false;
                }
            }
            else {
                return tempList.remove(letter);
            }
        }
        return false;
    }
}
