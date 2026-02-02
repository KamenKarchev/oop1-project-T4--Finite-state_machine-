/**
 * <h2>Базови интерфейси за граф (graph.base)</h2>
 *
 * <p>
 * Пакетът дефинира общите абстракции за графов модел, използван от автоматите:
 * </p>
 *
 * <ul>
 *   <li>{@link graph.base.Node} — възел (състояние), който съдържа изходящи ребра.</li>
 *   <li>{@link graph.base.Edge} — ребро (преход) с {@code from/to} и етикет (символ или ε).</li>
 *   <li>{@link graph.base.Graph} — колекция от възли и операции над графа.</li>
 * </ul>
 *
 * <p>
 * Конкретната имплементация за „multiedge directed graph“ е в {@code graph.mdg}.
 * </p>
 */
package graph.base;

