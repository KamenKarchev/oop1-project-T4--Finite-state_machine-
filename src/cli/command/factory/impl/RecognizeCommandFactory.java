package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.RecognizeCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code recognize}</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.RecognizeCommand}.</p>
 */
public class RecognizeCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public RecognizeCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.RECOGNIZE;
    }

    @Override
    public Command create() throws CliException {
        return new RecognizeCommand(ctx);
    }
}

