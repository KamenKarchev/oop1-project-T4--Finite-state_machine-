package graph.base;

/**
 * <h2>Ребро в граф</h2>
 *
 * <p>
 * Реброто представя преход от възел {@code from} към възел {@code to}.
 * В контекста на автоматите етикетът ({@link #getLabel()}) е символ от азбуката или {@code null}
 * за ε-преход.
 * </p>
 *
 * @param <N> тип на възлите в графа
 */
public interface Edge<N extends Node<?>> {
    /**
     * @return изходният (source) възел на реброто
     */
    N getFrom();

    /**
     * @return входният (destination) възел на реброто
     */
    N getTo();

    /**
     * @return етикет/символ на реброто или {@code null} за ε-преход
     */
    Character getLabel();
}
