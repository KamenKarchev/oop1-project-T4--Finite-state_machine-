package cli.command.commands.file;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code save}</h2>
 *
 * <p>
 * Записва текущия регистър от автомати към „текущия отворен файл“ в рамките на сесията.
 * Този файл се управлява от {@link io.RegistryFileSession} и се задава чрез:
 * </p>
 *
 * <ul>
 *   <li>{@code open &lt;file&gt;} — отваря файл и го прави текущ</li>
 *   <li>{@code saveas &lt;file&gt;} — записва и прави новия файл текущ</li>
 * </ul>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * save
 * </pre>
 *
 * <h3>Грешки</h3>
 *
 * <p>
 * Ако няма текущ файл (не е правено {@code open} или {@code saveas}), {@link io.RegistryFileSession}
 * ще хвърли грешка при {@code save}.
 * </p>
 */
public final class SaveCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code save}.
     *
     * @param ctx CLI контекст (file session + registry)
     */
    public SaveCommand(CliContext ctx) {
        super(CommandToken.SAVE, 0, args -> {
            ctx.fileSession().save(ctx.registry());
            ctx.out().println("Saved machines to " + ctx.fileSession().currentFileOrNull());
        });
    }
}

