package exceptions.cli;

/**
 * <h2>Базово изключение за CLI слоя</h2>
 *
 * <p>
 * {@code CliException} е базов тип за грешки, които са свързани с конзолния интерфейс:
 * парсване на команда, валидиране на аргументи, резолюция на токени и други подобни.
 * </p>
 *
 * <p>
 * Обичайно тези изключения се хващат в main-loop-а и се показва {@link #getMessage()} на потребителя.
 * </p>
 */
public class CliException extends Exception {
    /**
     * @param message човекочетим текст за грешката
     */
    public CliException(String message) {
        super(message);
    }

    /**
     * @param message човекочетим текст за грешката
     * @param cause първопричината (ако има)
     */
    public CliException(String message, Throwable cause) {
        super(message, cause);
    }
}
