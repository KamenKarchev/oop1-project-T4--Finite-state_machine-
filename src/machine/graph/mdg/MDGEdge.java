package graph.mdg;

import graph.base.Edge;

import java.util.Objects;

public class MDGEdge implements Edge<MDGNode> {
    private final MDGNode from;
    private final MDGNode to;
    private final Character label;

    public MDGEdge(MDGNode from, MDGNode to) {
        this(from, to, null);
    }

    public MDGEdge(MDGNode from, MDGNode to, Character label) {
        this.from = Objects.requireNonNull(from, "from");
        this.to = Objects.requireNonNull(to, "to");
        this.label = label;
    }

    @Override
    public MDGNode getFrom() {
        return from;
    }

    @Override
    public MDGNode getTo() {
        return to;
    }

    @Override
    public Character getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MDGEdge mdgEdge)) return false;
        return Objects.equals(from, mdgEdge.from) && Objects.equals(to, mdgEdge.to) && Objects.equals(label, mdgEdge.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, label);
    }

    @Override
    public String toString() {
        return "MDGEdge{from=" + from + ", to=" + to + ", label=" + label + '}';
    }
}
