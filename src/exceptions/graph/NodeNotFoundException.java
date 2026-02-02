package exceptions.graph;

/**
 * <h2>Липсващ възел</h2>
 *
 * <p>
 * Хвърля се когато се търси/премахва възел (състояние), който не съществува в графа.
 * </p>
 */
public class NodeNotFoundException extends GraphException {
    /**
     * @param message описание на проблема
     */
    public NodeNotFoundException(String message) {
        super(message);
    }
}
