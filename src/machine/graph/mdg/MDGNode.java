package graph.mdg;

import exceptions.graph.InvalidNodeNameException;
import graph.mdg.MDGEdge;
import graph.base.Node;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MDGNode implements Node<MDGEdge> {
    private final String name;
    private final Set<MDGEdge> edges;

    private MDGNode(String name, Set<MDGEdge> edges) {
        this.name = name;
        this.edges = edges;
    }

    public MDGNode(String name) throws InvalidNodeNameException {
        this.name = normalizeName(name);
        this.edges = new HashSet<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<MDGEdge> getEdges() {
        return edges;
    }

    public boolean addEdge(MDGEdge edge) {
        return edges.add(edge);
    }

    public boolean removeEdge(MDGEdge edge) {
        return edges.remove(edge);
    }

    public boolean hasEdgeTo(MDGNode to) {
        if (to == null) return false;
        for (MDGEdge e : edges) {
            if (e.getTo().equals(to)) return true;
        }
        return false;
    }

    public boolean hasEdgeWith(Character label) {
        for (MDGEdge e : edges) {
            if (Objects.equals(e.getLabel(), label)) return true;
        }
        return false;
    }

    public boolean hasEdge(MDGEdge edge) {
        if (edge == null) return false;
        return edges.contains(edge);
    }

    public int removeEdges(MDGNode to) {
        if (to == null) return 0;
        int before = edges.size();
        edges.removeIf(e -> e.getTo().equals(to));
        return before - edges.size();
    }

    public int removeEdges(Character label) {
        int before = edges.size();
        edges.removeIf(e -> Objects.equals(e.getLabel(), label));
        return before - edges.size();
    }

    public int removeEdges(MDGNode to, Character label) {
        if (to == null) return 0;
        int before = edges.size();
        edges.removeIf(e -> e.getTo().equals(to) && Objects.equals(e.getLabel(), label));
        return before - edges.size();
    }

    public int getEdgesCount() {
        return edges.size();
    }

    private static String normalizeName(String name) throws InvalidNodeNameException {
        if (name == null) {
            throw new InvalidNodeNameException("Node name cannot be null");
        }
        String normalized = name.trim();
        if (normalized.isEmpty()) {
            throw new InvalidNodeNameException("Node name cannot be empty/blank");
        }
        return normalized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MDGNode mdgNode)) return false;
        return name.equals(mdgNode.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
