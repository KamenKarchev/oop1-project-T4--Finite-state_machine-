package cli.command.commands.machine;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h2>Команда {@code list}</h2>
 *
 * <p>
 * Показва всички ID-та на автомати, които съществуват в текущия регистър.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * list
 * </pre>
 *
 * <h3>Изход</h3>
 *
 * <ul>
 *   <li>Ако няма машини: печата {@code (no machines)}</li>
 *   <li>Иначе: печата всяко ID на отделен ред, сортирано във възходящ ред.</li>
 * </ul>
 */
public final class ListCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code list}.
     *
     * @param ctx CLI контекст (ползва {@link machine.MachineFacade#listIds()})
     */
    public ListCommand(CliContext ctx) {
        super(CommandToken.LIST, 0, args -> {
            List<Integer> ids = new ArrayList<>(ctx.machines().listIds());
            Collections.sort(ids);
            if (ids.isEmpty()) {
                ctx.out().println("(no machines)");
                return;
            }
            for (Integer id : ids) {
                ctx.out().println(id);
            }
        });
    }
}

