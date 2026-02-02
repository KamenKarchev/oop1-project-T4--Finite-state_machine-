package exceptions.cli;

/**
 * <h2>Невалидни аргументи към команда</h2>
 *
 * <p>
 * Хвърля се когато команда е разпозната, но подадените аргументи са невалидни:
 * грешен брой параметри (арност), невалидно ID и т.н.
 * </p>
 */
public class InvalidCommandArgumentsException extends CliException {
    /**
     * @param message описва какво е невалидно във входа
     */
    public InvalidCommandArgumentsException(String message) {
        super(message);
    }
}
