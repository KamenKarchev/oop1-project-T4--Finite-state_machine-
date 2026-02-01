package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.file.SaveCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class SaveCommandFactory implements CommandFactory {
    private final CliContext ctx;

    public SaveCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.SAVE;
    }

    @Override
    public Command create() throws CliException {
        return new SaveCommand(ctx);
    }
}

