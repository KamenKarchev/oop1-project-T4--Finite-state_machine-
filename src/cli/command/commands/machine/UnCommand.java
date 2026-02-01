package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code un &lt;id&gt;}</h2>
 *
 * <p>
 * Създава нов автомат, който разпознава положителната Клини затвореност (positive closure)
 * на езика на даден автомат: \(L^+\) (едно или повече повторения).
 * Делегира към {@link machine.MachineFacade#un(int)}.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * un 3
 * </pre>
 *
 * <h3>Изход</h3>
 *
 * <p>Печата ID на новата машина.</p>
 */
public final class UnCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code un}.
     *
     * @param ctx CLI контекст (фасада + out)
     */
    public UnCommand(CliContext ctx) {
        super(CommandToken.UN, 1, args -> {
            int id = CliIdParser.parseId(args.get(0));
            int out = ctx.machines().un(id);
            ctx.out().println(out);
        });
    }
}

