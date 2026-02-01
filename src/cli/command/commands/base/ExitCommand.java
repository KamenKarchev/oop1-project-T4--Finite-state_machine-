package cli.command.commands.base;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code exit}</h2>
 *
 * <p>
 * Логически прекратява CLI сесията. Тази команда няма параметри.
 * </p>
 *
 * <h3>Как реално „спира“ програмата?</h3>
 *
 * <p>
 * Командата сама по себе си само печата съобщение. Реалното прекратяване става в main-loop-а:
 * {@link cli.command.CommandHandler#handleLine(String)} връща {@code false} когато токенът е
 * {@link cli.command.CommandToken#EXIT}, което позволява на приложението да прекъсне цикъла.
 * </p>
 *
 * <pre>
 * exit
 * </pre>
 */
public final class ExitCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code exit}.
     *
     * @param ctx CLI контекст (ползва се за изходното съобщение)
     */
    public ExitCommand(CliContext ctx) {
        super(CommandToken.EXIT, 0, args -> ctx.out().println("Exiting the program..."));
    }
}

