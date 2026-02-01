package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code concat &lt;id1&gt; &lt;id2&gt;}</h2>
 *
 * <p>
 * Създава нов автомат, който разпознава конкатенацията на езиците на два автомата.
 * Делегира към {@link machine.MachineFacade#concat(int, int)}.
 * </p>
 *
 * <h3>Как се реализира?</h3>
 *
 * <p>
 * Аналогично на {@code union}, фасадата комбинира оригиналните регулярни изрази на двата автомата,
 * но използва експлицитния оператор за конкатенация ({@code .}) и после парсва получения regex.
 * </p>
 *
 * <h3>Вход/изход</h3>
 *
 * <pre>
 * concat 1 2
 * </pre>
 *
 * <p>Изход (един ред):</p>
 *
 * <pre>
 * id=&lt;newId&gt; regex=&lt;combinedRegex&gt;
 * </pre>
 */
public final class ConcatCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code concat}.
     *
     * @param ctx CLI контекст (фасада + регистър + out)
     */
    public ConcatCommand(CliContext ctx) {
        super(CommandToken.CONCAT, 2, args -> {
            int id1 = CliIdParser.parseId(args.get(0));
            int id2 = CliIdParser.parseId(args.get(1));
            int out = ctx.machines().concat(id1, id2);
            String rx = ctx.registry().get(out).getOriginalRegex();
            ctx.out().println("id=" + out + " regex=" + rx);
        });
    }
}

