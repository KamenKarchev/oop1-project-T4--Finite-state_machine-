package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.base.ExitCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code exit}</h2>
 *
 * <p>Създава {@link cli.command.commands.base.ExitCommand}.</p>
 */
public class ExitCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public ExitCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.EXIT;
    }

    @Override
    public Command create() throws CliException {
        return new ExitCommand(ctx);
    }
}

