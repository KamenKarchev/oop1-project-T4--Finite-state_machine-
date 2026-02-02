package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.UnionCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code union}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.UnionCommand}.</p>
 */
public class UnionCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public UnionCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.UNION;
    }

    @Override
    public Command create() throws CliException {
        return new UnionCommand(ctx);
    }
}

