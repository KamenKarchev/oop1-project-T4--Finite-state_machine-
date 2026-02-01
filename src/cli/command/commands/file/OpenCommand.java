package cli.command.commands.file;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;
import machine.MachineRegistry;

import java.nio.file.Path;

/**
 * <h2>Команда {@code open &lt;file&gt;}</h2>
 *
 * <p>
 * Зарежда регистър от автомати от XML файл и го прави „текущ“ за сесията.
 * Това означава, че съдържанието на {@link CliContext#registry()} се заменя със зареденото.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * open machines.xml
 * </pre>
 *
 * <h3>Поведение (важни детайли)</h3>
 *
 * <ul>
 *   <li>Файлът се зарежда чрез {@link CliContext#xmlStore()}.</li>
 *   <li>
 *     Текущият {@link machine.MachineRegistry} <b>не се подменя като обект</b>, а се изчиства и
 *     се попълва наново. Това е важно, защото други части (например фасадата) държат референция към него.
 *   </li>
 *   <li>След зареждане се обновява генераторът на следващи ID-та.</li>
 *   <li>Маркира се, че текущият отворен файл е {@code file} чрез {@link CliContext#fileSession()}.</li>
 * </ul>
 *
 * @see io.MachineXmlStore
 * @see io.RegistryFileSession
 */
public final class OpenCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code open}.
     *
     * @param ctx CLI контекст (registry, xml store, file session, out)
     */
    public OpenCommand(CliContext ctx) {
        super(CommandToken.OPEN, 1, args -> {
            Path file = Path.of(args.get(0));
            MachineRegistry loaded = ctx.xmlStore().load(file);

            // replace current registry content (preserve the same registry object)
            ctx.registry().clear();
            for (Integer id : loaded.ids()) {
                ctx.registry().put(id, loaded.get(id));
            }
            ctx.registry().resetNextIdAfterLoad();
            ctx.fileSession().open(file);
            ctx.out().println("Loaded machines from " + file);
        });
    }
}

