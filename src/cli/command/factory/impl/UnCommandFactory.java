package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.UnCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code un}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.UnCommand}.</p>
 */
public class UnCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public UnCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.UN;
    }

    @Override
    public Command create() throws CliException {
        return new UnCommand(ctx);
    }
}

