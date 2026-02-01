package cli.command.commands.file;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

import java.nio.file.Path;

/**
 * <h2>Команда {@code saveas &lt;file&gt;}</h2>
 *
 * <p>
 * Записва текущия регистър от автомати в посочения файл и го прави „текущ“ за последващи
 * {@code save} команди.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * saveas machines.xml
 * </pre>
 *
 * <h3>Разлика спрямо {@code save}</h3>
 *
 * <ul>
 *   <li>{@code save} използва вече зададен текущ файл (ако има такъв).</li>
 *   <li>{@code saveas} винаги приема път и настройва текущия файл към него.</li>
 * </ul>
 */
public final class SaveAsCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code saveas}.
     *
     * @param ctx CLI контекст (file session + registry)
     */
    public SaveAsCommand(CliContext ctx) {
        super(CommandToken.SAVE_AS, 1, args -> {
            Path file = Path.of(args.get(0));
            ctx.fileSession().saveAs(file, ctx.registry());
            ctx.out().println("Saved machines as " + file);
        });
    }
}

