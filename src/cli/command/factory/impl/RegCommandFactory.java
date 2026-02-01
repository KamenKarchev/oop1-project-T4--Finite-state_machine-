package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.RegCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

public class RegCommandFactory implements CommandFactory {
    private final CliContext ctx;

    public RegCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.REG;
    }

    @Override
    public Command create() throws CliException {
        return new RegCommand(ctx);
    }
}

