/**
 * <h2>MDG имплементация (graph.mdg)</h2>
 *
 * <p>
 * Пакетът съдържа конкретна реализация на базовия графов модел като
 * <b>много-ребрен ориентиран граф</b> (Multi-edge Directed Graph).
 * </p>
 *
 * <p>
 * Основните класове са:
 * </p>
 *
 * <ul>
 *   <li>{@link graph.mdg.MDGNode} — възел със множество изходящи ребра.</li>
 *   <li>{@link graph.mdg.MDGEdge} — ребро с {@code from/to} и етикет (символ или ε).</li>
 *   <li>{@link graph.mdg.MultiedgeDirectedGraph} — граф като колекция от възли.</li>
 * </ul>
 *
 * <p>
 * Тези структури се използват от {@link machine.MultiedgeFinateLogicMachine}, за да представят
 * множеството състояния \(S\) и преходите \(T\).
 * </p>
 */
package graph.mdg;

