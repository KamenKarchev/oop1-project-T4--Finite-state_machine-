package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.base.HelpCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code help}</h2>
 *
 * <p>Създава {@link cli.command.commands.base.HelpCommand}.</p>
 */
public class HelpCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public HelpCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.HELP;
    }

    @Override
    public Command create() throws CliException {
        return new HelpCommand(ctx);
    }
}

