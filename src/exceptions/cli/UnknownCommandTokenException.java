package exceptions.cli;

/**
 * <h2>Непозната команда</h2>
 *
 * <p>
 * Хвърля се когато първият токен (име на команда) не може да се резолва до
 * {@link cli.command.CommandToken}, или когато има двусмислие/несъответствие в арността.
 * </p>
 */
public class UnknownCommandTokenException extends CliException {
    /**
     * @param message описва причината (напр. „Unknown command: ...“)
     */
    public UnknownCommandTokenException(String message) {
        super(message);
    }
}
