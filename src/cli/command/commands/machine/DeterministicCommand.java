package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code deterministic &lt;id&gt;}</h2>
 *
 * <p>
 * Проверява дали автоматът е детерминиран според дефиницията, използвана в проекта.
 * Делегира към {@link machine.MachineFacade#deterministic(int)}.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * deterministic 2
 * </pre>
 *
 * <h3>Изход</h3>
 *
 * <p>
 * Печата {@code true} ако е детерминиран, иначе {@code false}.
 * </p>
 */
public final class DeterministicCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code deterministic}.
     *
     * @param ctx CLI контекст (ползва фасадата)
     */
    public DeterministicCommand(CliContext ctx) {
        super(CommandToken.DETERMINISTIC, 1, args -> {
            int id = CliIdParser.parseId(args.get(0));
            ctx.out().println(ctx.machines().deterministic(id));
        });
    }
}

