package exceptions.graph;

/**
 * <h2>Дублиран възел</h2>
 *
 * <p>
 * Хвърля се при опит да се добави възел (състояние), който вече съществува в графа според
 * дефинираната равенство на възли (обикновено по име).
 * </p>
 */
public class DuplicateNodeException extends GraphException {
    /**
     * @param message описание на проблема
     */
    public DuplicateNodeException(String message) {
        super(message);
    }
}
