package exceptions.machine;

/**
 * <h2>Базово изключение за автоматите</h2>
 *
 * <p>
 * {@code MachineException} е базов тип за грешки в домейн слоя „machine“: невалидна азбука,
 * невалидно състояние, невалиден символ при преход/разпознаване и т.н.
 * </p>
 */
public class MachineException extends Exception {
    /**
     * @param message човекочетим текст за грешката
     */
    public MachineException(String message) {
        super(message);
    }

    /**
     * @param message човекочетим текст за грешката
     * @param cause първопричина (ако има)
     */
    public MachineException(String message, Throwable cause) {
        super(message, cause);
    }
}

