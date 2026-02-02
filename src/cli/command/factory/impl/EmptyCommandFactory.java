package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.EmptyCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code empty}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.EmptyCommand}.</p>
 */
public class EmptyCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public EmptyCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.EMPTY;
    }

    @Override
    public Command create() throws CliException {
        return new EmptyCommand(ctx);
    }
}

