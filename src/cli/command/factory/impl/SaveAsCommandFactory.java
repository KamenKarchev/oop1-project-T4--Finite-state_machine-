package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.file.SaveAsCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code saveas}</h2>
 *
 * <p>Създава {@link cli.command.commands.file.SaveAsCommand}.</p>
 */
public class SaveAsCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public SaveAsCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.SAVE_AS;
    }

    @Override
    public Command create() throws CliException {
        return new SaveAsCommand(ctx);
    }
}

