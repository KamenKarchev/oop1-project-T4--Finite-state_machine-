/**
 * <h2>Изключения за графовия слой</h2>
 *
 * <p>
 * Пакетът съдържа изключения, свързани с графовата структура, използвана от автоматите.
 * Грешките могат да бъдат например: липсващ възел, липсващо ребро, дублиране, невалидни имена.
 * </p>
 *
 * <p>
 * Базовият тип е {@link exceptions.graph.GraphException}. Конкретните подтипове (напр.
 * {@link exceptions.graph.NodeNotFoundException}, {@link exceptions.graph.EdgeNotFoundException},
 * {@link exceptions.graph.DuplicateNodeException}, {@link exceptions.graph.DuplicateEdgeException})
 * позволяват по-ясни диагностични съобщения.
 * </p>
 */
package exceptions.graph;

