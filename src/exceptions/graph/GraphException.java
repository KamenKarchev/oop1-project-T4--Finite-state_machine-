package exceptions.graph;

/**
 * <h2>Базово изключение за графовия слой</h2>
 *
 * <p>
 * {@code GraphException} е базов тип за грешки, които се появяват при операции върху графа:
 * добавяне/премахване на възли и ребра, проверки за наличие, както и по-сложни трансформации
 * (напр. {@code cut}).
 * </p>
 */
public class GraphException extends Exception {
    /**
     * @param message човекочетим текст за грешката
     */
    public GraphException(String message) {
        super(message);
    }

    /**
     * @param message човекочетим текст за грешката
     * @param cause първопричина (ако има)
     */
    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }
}
