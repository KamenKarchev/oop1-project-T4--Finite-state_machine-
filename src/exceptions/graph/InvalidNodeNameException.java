package exceptions.graph;

/**
 * <h2>Невалидно име на възел</h2>
 *
 * <p>
 * Хвърля се когато името на възел (състояние) не отговаря на ограниченията на проекта
 * (напр. празен стринг, забранени символи и т.н.).
 * </p>
 */
public class InvalidNodeNameException extends GraphException {
    /**
     * @param message описание на проблема
     */
    public InvalidNodeNameException(String message) {
        super(message);
    }
}
