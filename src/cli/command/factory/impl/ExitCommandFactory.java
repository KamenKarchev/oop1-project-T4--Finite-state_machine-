package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.base.ExitCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class ExitCommandFactory implements CommandFactory {
    private final CliContext ctx;

    public ExitCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.EXIT;
    }

    @Override
    public Command create() throws CliException {
        return new ExitCommand(ctx);
    }
}

