package exceptions.graph;

/**
 * <h2>Липсващо ребро</h2>
 *
 * <p>
 * Хвърля се когато се търси/премахва ребро, което не съществува в графа.
 * </p>
 */
public class EdgeNotFoundException extends GraphException {
    /**
     * @param message описание на проблема
     */
    public EdgeNotFoundException(String message) {
        super(message);
    }
}
