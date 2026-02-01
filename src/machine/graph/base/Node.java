package graph.base;

import graph.mdg.MDGEdge;

import java.util.Set;

public interface Node<E extends Edge<?>> {
    String getName();

    Set<E> getEdges();
}
