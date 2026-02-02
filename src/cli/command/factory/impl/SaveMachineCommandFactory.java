package cli.command.factory.impl;

import cli.command.Command;
import cli.command.CommandToken;
import cli.command.commands.machine.SaveMachineCommand;
import cli.command.factory.CommandFactory;
import cli.runtime.CliContext;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за {@code save <id> <file>} (SAVEM)</h2>
 *
 * <p>Създава {@link cli.command.commands.machine.SaveMachineCommand}.</p>
 */
public class SaveMachineCommandFactory implements CommandFactory {
    private final CliContext ctx;

    /**
     * @param ctx CLI контекст
     */
    public SaveMachineCommandFactory(CliContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public CommandToken token() {
        return CommandToken.SAVEM;
    }

    @Override
    public Command create() throws CliException {
        return new SaveMachineCommand(ctx);
    }
}

