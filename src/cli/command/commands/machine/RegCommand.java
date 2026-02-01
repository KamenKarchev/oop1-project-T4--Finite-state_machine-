package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code reg &lt;regex&gt;}</h2>
 *
 * <p>
 * Създава нов автомат от подаден регулярен израз и го регистрира в {@link machine.MachineRegistry}.
 * Командата делегира към {@link machine.MachineFacade#reg(String)}.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * reg a+b
 * reg "(a+b).c"
 * </pre>
 *
 * <h3>Изход</h3>
 *
 * <p>
 * Печата новото ID на автомата като число на отделен ред.
 * </p>
 */
public final class RegCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code reg}.
     *
     * @param ctx CLI контекст (използва фасадата за създаване на машина)
     */
    public RegCommand(CliContext ctx) {
        super(CommandToken.REG, 1, args -> {
            int id = ctx.machines().reg(args.get(0));
            ctx.out().println(id);
        });
    }
}

