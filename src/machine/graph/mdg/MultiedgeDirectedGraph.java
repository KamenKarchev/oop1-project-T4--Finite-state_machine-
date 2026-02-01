package graph.mdg;

import exceptions.graph.GraphException;
import exceptions.graph.NodeNotFoundException;
import graph.base.Graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MultiedgeDirectedGraph implements Graph<MDGNode, MDGEdge> {
    protected HashMap<String, MDGNode> nodes = new HashMap<>();
    @Override
    public List<MDGNode> getNodes() {
        return nodes.values().stream().toList();
    }

    @Override
    public void putNode(MDGNode node) throws GraphException {
        nodes.put(node.getName(),node);
    }

    @Override
    public void removeNode(MDGNode node) throws GraphException {
        nodes.remove(node.getName());
    }

    @Override
    public boolean hasNode(MDGNode node) throws GraphException {
        return nodes.get(node.getName()) != null;
    }

    @Override
    public void addEdge(MDGNode from, MDGEdge edge) throws GraphException {
        nodes.get(from.getName()).addEdge(edge);
    }

    @Override
    public void removeEdge(MDGNode from, MDGEdge edge) throws GraphException {
        nodes.get(from.getName()).removeEdge(edge);
    }

    @Override
    public boolean hasEdge(MDGNode from, MDGEdge edge) throws GraphException {
        return nodes.get(from.getName()).hasEdge(edge);
    }

    @Override
    public int getNodeCount() {
        return nodes.size();
    }

    @Override
    public int getEdgesCount() {
        return nodes.values().stream().mapToInt(MDGNode::getEdgesCount).sum();
    }

    @Override
    public int getEdgesCount(MDGNode node) throws GraphException {
        return nodes.get(node.getName()).getEdgesCount();
    }

    @Override
    public void cut(MDGNode node1, MDGNode node2) throws GraphException {
        if (node1 == null || node2 == null) {
            throw new GraphException("cut nodes cannot be null");
        }

        MDGNode n1 = nodes.get(node1.getName());
        if (n1 == null) {
            throw new NodeNotFoundException("Node not found: " + node1.getName());
        }

        MDGNode n2 = nodes.get(node2.getName());
        if (n2 == null) {
            // node2 is alien -> insert
            putNode(node2);
            n2 = node2;
        }

        if (n1.equals(n2)) {
            return;
        }

        // 1) move outgoing edges from n1 -> n2 (rewrite from, and rewrite self-loops to n2)
        for (MDGEdge e : List.copyOf(n1.getEdges())) {
            MDGNode to = e.getTo().equals(n1) ? n2 : e.getTo();
            n2.addEdge(new MDGEdge(n2, to, e.getLabel()));
        }

        // 2) rewire incoming edges pointing to n1 to point to n2
        for (MDGNode u : nodes.values()) {
            for (MDGEdge e : List.copyOf(u.getEdges())) {
                if (e.getTo().equals(n1)) {
                    u.removeEdge(e);
                    u.addEdge(new MDGEdge(u, n2, e.getLabel()));
                }
            }
        }

        // 3) remove n1
        removeNode(n1);
    }
}
