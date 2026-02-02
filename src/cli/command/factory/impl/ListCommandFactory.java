package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.ListCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code list}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.ListCommand}.</p>
 */
public class ListCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public ListCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.LIST;
    }

    @Override
    public Command create() throws CliException {
        return new ListCommand(ctx);
    }
}

