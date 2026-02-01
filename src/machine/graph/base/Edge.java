package graph.base;

public interface Edge<N extends Node<?>> {
    N getFrom();

    N getTo();

    Character getLabel();
}
