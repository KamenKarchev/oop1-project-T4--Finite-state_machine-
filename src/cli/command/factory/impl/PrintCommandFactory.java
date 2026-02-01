package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.PrintCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class PrintCommandFactory implements CommandFactory {
    private final CliContext ctx;

    public PrintCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.PRINT;
    }

    @Override
    public Command create() throws CliException {
        return new PrintCommand(ctx);
    }
}

