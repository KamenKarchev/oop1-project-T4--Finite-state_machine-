package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.DeterministicCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code deterministic}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.DeterministicCommand}.</p>
 */
public class DeterministicCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public DeterministicCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.DETERMINISTIC;
    }

    @Override
    public Command create() throws CliException {
        return new DeterministicCommand(ctx);
    }
}

