package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.file.CloseCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code close}</h2>
 *
 * <p>
 * Конкретна {@link cli.command.factory.CommandFactory}, която създава команда
 * {@link cli.command.commands.file.CloseCommand}.
 * </p>
 */
public class CloseCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст, който се подава към командата
     */
    public CloseCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.CLOSE;
    }

    @Override
    public Command create() throws CliException {
        return new CloseCommand(ctx);
    }
}

