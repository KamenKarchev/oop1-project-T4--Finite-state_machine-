package exceptions.graph;

/**
 * <h2>Дублирано ребро</h2>
 *
 * <p>
 * Хвърля се при опит за добавяне на ребро, което вече съществува според дефинираната в проекта
 * равенство на ребра (обикновено: {@code from + to + label}).
 * </p>
 */
public class DuplicateEdgeException extends GraphException {
    /**
     * @param message описание на проблема (напр. кое ребро се дублира)
     */
    public DuplicateEdgeException(String message) {
        super(message);
    }
}
