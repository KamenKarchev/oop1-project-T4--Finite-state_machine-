package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.factory.CommandFactory;
import cli.command.commands.base.HelpCommand;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class HelpCommandFactory implements CommandFactory {
    private final CliContext ctx;

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

