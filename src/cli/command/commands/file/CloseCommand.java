package cli.command.commands.file;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code close}</h2>
 *
 * <p>
 * Затваря „файловата сесия“ ({@link io.RegistryFileSession}), т.е. изчиства текущия файл,
 * който би се използвал от {@code save} (без параметри).
 * </p>
 *
 * <h3>Какво НЕ прави</h3>
 *
 * <ul>
 *   <li>Не трие файлове от диска.</li>
 *   <li>Не изчиства машините от {@link machine.MachineRegistry}.</li>
 * </ul>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * close
 * </pre>
 */
public final class CloseCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code close}.
     *
     * @param ctx CLI контекст (file session + out)
     */
    public CloseCommand(CliContext ctx) {
        super(CommandToken.CLOSE, 0, args -> {
            ctx.fileSession().close();
            ctx.out().println("Closed file session.");
        });
    }
}

