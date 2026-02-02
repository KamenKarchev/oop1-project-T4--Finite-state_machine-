package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.ConcatCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code concat}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.ConcatCommand}.</p>
 */
public class ConcatCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public ConcatCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.CONCAT;
    }

    @Override
    public Command create() throws CliException {
        return new ConcatCommand(ctx);
    }
}

