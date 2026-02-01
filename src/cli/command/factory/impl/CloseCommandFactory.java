package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.file.CloseCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class CloseCommandFactory implements CommandFactory {
    private final CliContext ctx;

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

