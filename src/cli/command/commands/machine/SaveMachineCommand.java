package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

import java.nio.file.Path;

/**
 * <h2>Команда {@code save &lt;id&gt; &lt;file&gt;} (SAVEM)</h2>
 *
 * <p>
 * Записва <b>един конкретен автомат</b> в XML файл. Това е различно от {@code save} без аргументи,
 * което записва целия регистър към текущия отворен файл.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * save 3 m3.xml
 * </pre>
 *
 * <h3>XML формат</h3>
 *
 * <p>
 * Сериализацията се извършва от {@link io.MachineXmlStore#saveSingle(java.nio.file.Path, int, machine.MultiedgeFinateLogicMachine)}.
 * </p>
 *
 * <h3>Грешки</h3>
 *
 * <ul>
 *   <li>Невалиден ID → {@link exceptions.cli.InvalidCommandArgumentsException}</li>
 *   <li>Липсваща машина по ID → домейн изключение от регистъра</li>
 *   <li>I/O проблем при запис → {@link java.io.IOException} (обвито/препратено нагоре)</li>
 * </ul>
 */
public final class SaveMachineCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code save <id> <file>} (варианта за единична машина).
     *
     * @param ctx CLI контекст (xml store + registry + out)
     */
    public SaveMachineCommand(CliContext ctx) {
        super(CommandToken.SAVEM, 2, args -> {
            int id = CliIdParser.parseId(args.get(0));
            Path file = Path.of(args.get(1));
            ctx.xmlStore().saveSingle(file, id, ctx.registry().get(id));
            ctx.out().println("Saved machine " + id + " to " + file);
        });
    }
}

