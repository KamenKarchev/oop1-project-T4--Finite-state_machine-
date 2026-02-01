package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.file.OpenCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class OpenCommandFactory implements CommandFactory {
    private final CliContext ctx;

    public OpenCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.OPEN;
    }

    @Override
    public Command create() throws CliException {
        return new OpenCommand(ctx);
    }
}

