package graph.mdg;

import graph.base.Edge;

import java.util.Objects;

/**
 * <h2>Ребро в много-ребрен ориентиран граф (MDG)</h2>
 *
 * <p>
 * {@code MDGEdge} е конкретна имплементация на {@link graph.base.Edge} за MDG графа.
 * Реброто е дефинирано от тройката {@code (from, to, label)} и затова {@link #equals(Object)}
 * и {@link #hashCode()} използват точно тези полета.
 * </p>
 *
 * <p>
 * Етикетът ({@link #getLabel()}) е {@link Character} и може да е {@code null} за ε-преход.
 * </p>
 */
public class MDGEdge implements Edge<MDGNode> {
    private final MDGNode from;
    private final MDGNode to;
    private final Character label;

    /**
     * Създава ε-ребро (label == null).
     *
     * @param from изходен възел
     * @param to входен възел
     */
    public MDGEdge(MDGNode from, MDGNode to) {
        this(from, to, null);
    }

    /**
     * Създава ребро.
     *
     * @param from изходен възел
     * @param to входен възел
     * @param label етикет/символ или {@code null} за ε
     */
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
