package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code recognize &lt;id&gt; &lt;word&gt;}</h2>
 *
 * <p>
 * Проверява дали автоматът с дадено ID разпознава/приема подадената дума.
 * Делегира към {@link machine.MachineFacade#recognize(int, String)}.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * recognize 1 abba
 * recognize 1 ""      // празна дума (epsilon)
 * </pre>
 *
 * <h3>Празна дума</h3>
 *
 * <p>
 * Празната дума се подава като празен стринг {@code ""} в CLI. Токенизаторът
 * {@link cli.command.CommandLineTokenizer} има специална поддръжка да не „изпусне“ такъв токен.
 * </p>
 *
 * <h3>Изход</h3>
 *
 * <p>
 * Печата {@code true} ако думата се приема, иначе {@code false}.
 * </p>
 */
public final class RecognizeCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code recognize}.
     *
     * @param ctx CLI контекст (фасада + out)
     */
    public RecognizeCommand(CliContext ctx) {
        super(CommandToken.RECOGNIZE, 2, args -> {
            int id = CliIdParser.parseId(args.get(0));
            String word = args.get(1);
            ctx.out().println(ctx.machines().recognize(id, word));
        });
    }
}

