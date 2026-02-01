package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code union &lt;id1&gt; &lt;id2&gt;}</h2>
 *
 * <p>
 * Създава нов автомат, който разпознава обединението на езиците на два автомата.
 * Реалната операция се делегира към {@link machine.MachineFacade#union(int, int)}.
 * </p>
 *
 * <h3>Как се реализира на ниво фасада?</h3>
 *
 * <p>
 * В този проект {@code union} се реализира чрез комбиниране на оригиналните регулярни изрази
 * на двата автомата (ако са налични) и повторно парсване. Поради това командата печата както
 * новото ID, така и новия regex.
 * </p>
 *
 * <h3>Вход/изход</h3>
 *
 * <pre>
 * union 1 2
 * </pre>
 *
 * <p>Изход (един ред):</p>
 *
 * <pre>
 * id=&lt;newId&gt; regex=&lt;combinedRegex&gt;
 * </pre>
 *
 * <h3>Грешки</h3>
 *
 * <ul>
 *   <li>Невалидни ID аргументи → {@link exceptions.cli.InvalidCommandArgumentsException}</li>
 *   <li>Липсващи машини по ID → домейн изключение</li>
 *   <li>Липсващ „original regex“ за някоя машина → {@link IllegalArgumentException}</li>
 * </ul>
 */
public final class UnionCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code union}.
     *
     * @param ctx CLI контекст (фасада + регистър + out)
     */
    public UnionCommand(CliContext ctx) {
        super(CommandToken.UNION, 2, args -> {
            int id1 = CliIdParser.parseId(args.get(0));
            int id2 = CliIdParser.parseId(args.get(1));
            int out = ctx.machines().union(id1, id2);
            String rx = ctx.registry().get(out).getOriginalRegex();
            ctx.out().println("id=" + out + " regex=" + rx);
        });
    }
}

