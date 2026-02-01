package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code empty &lt;id&gt;}</h2>
 *
 * <p>
 * Проверява дали езикът на автомата е празен (т.е. дали автоматът приема поне една дума).
 * Делегира към {@link machine.MachineFacade#empty(int)}.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * empty 1
 * </pre>
 *
 * <h3>Изход</h3>
 *
 * <p>
 * Печата {@code true} ако езикът е празен, иначе {@code false}.
 * </p>
 */
public final class EmptyCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code empty}.
     *
     * @param ctx CLI контекст (ползва фасадата)
     */
    public EmptyCommand(CliContext ctx) {
        super(CommandToken.EMPTY, 1, args -> {
            int id = CliIdParser.parseId(args.get(0));
            ctx.out().println(ctx.machines().empty(id));
        });
    }
}

