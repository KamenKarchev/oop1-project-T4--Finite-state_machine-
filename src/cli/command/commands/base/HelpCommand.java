package cli.command.commands.base;

import cli.command.CommandToken;
import cli.command.commands.AbstractArityCommand;
import cli.runtime.CliContext;

/**
 * <h2>Команда {@code help}</h2>
 *
 * <p>
 * Показва кратка справка за поддържаните команди и тяхната употреба (синтаксис).
 * Командата няма параметри.
 * </p>
 *
 * <h3>Вход</h3>
 *
 * <pre>
 * help
 * </pre>
 *
 * <h3>Изход</h3>
 *
 * <p>
 * Печата текстово меню към {@link CliContext#out()}.
 * </p>
 *
 * <h3>Бележки</h3>
 *
 * <p>
 * Това меню е човеко-четим изход и е предназначено за интерактивна употреба.
 * При добавяне/промяна на команди е препоръчително този списък да се актуализира, за да отразява
 * реално наличните {@link cli.command.CommandToken}-и и тяхната арност.
 * </p>
 */
public final class HelpCommand extends AbstractArityCommand {
    /**
     * Създава командата {@code help}.
     *
     * @param ctx CLI контекст (използва се основно за {@link CliContext#out()})
     */
    public HelpCommand(CliContext ctx) {
        super(CommandToken.HELP, 0, args -> ctx.out().println(
                String.join(System.lineSeparator(),
                        "Supported commands:",
                        "  help",
                        "  exit",
                        "",
                        "File/database (placeholder-backed by TextDatabase):",
                        "  open <file>",
                        "  close",
                        "  save",
                        "  saveas <file>",
                        "",
                        "Machines:",
                        "  reg <regex>              -> prints new id",
                        "  list                     -> prints ids",
                        "  print <id>               -> prints machine",
                        "  empty <id>               -> prints true/false",
                        "  deterministic <id>        -> prints true/false",
                        "  recognize <id> <word>     -> prints true/false (word can be \"\")",
                        "  union <id1> <id2>         -> prints new id",
                        "  concat <id1> <id2>        -> prints new id",
                        "  un <id>                   -> prints new id",
                        "  save <id> <file>          -> writes machine to file"
                )
        ));
    }
}

