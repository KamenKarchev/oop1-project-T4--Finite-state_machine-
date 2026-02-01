package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code print &lt;id&gt;}</h2>
 *
 * <p>
 * Печата представяне на автомата с дадено ID. Форматирането се делегира към
 * {@link machine.MachineFacade#formatMachine(int)}.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * print 3
 * </pre>
 *
 * <h3>Грешки</h3>
 *
 * <ul>
 *   <li>Невалиден ID (не е число) → {@link exceptions.cli.InvalidCommandArgumentsException}</li>
 *   <li>Липсваща машина → домейн изключение от фасадата/регистъра</li>
 * </ul>
 */
public final class PrintCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code print}.
     *
     * @param ctx CLI контекст (ползва фасадата за формат)
     */
    public PrintCommand(CliContext ctx) {
        super(CommandToken.PRINT, 1, args -> {
            int id = CliIdParser.parseId(args.get(0));
            ctx.out().println(ctx.machines().formatMachine(id));
        });
    }
}

