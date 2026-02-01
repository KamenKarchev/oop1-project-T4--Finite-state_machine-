package graph.base;

import exceptions.graph.GraphException;

import java.util.Collection;
import java.util.List;

public interface Graph<N extends Node<E>, E extends Edge<N>> {
    List<N> getNodes();

    void putNode(N node) throws GraphException;

    void removeNode(N node) throws GraphException;

    boolean hasNode(N node) throws GraphException;

    void addEdge(N from, E edge) throws GraphException;

    void removeEdge(N from, E edge) throws GraphException;

    boolean hasEdge(N from, E edge) throws GraphException;

    void cut(N node1, N node2) throws GraphException;

    int getNodeCount();

    int getEdgesCount();

    int getEdgesCount(N node) throws GraphException;
}
