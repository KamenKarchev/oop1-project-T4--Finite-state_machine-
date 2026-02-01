package cli.command.commands;

import cli.command.Command;
import cli.command.CommandToken;
import cli.operations.Operation;
import exceptions.cli.InvalidCommandArgumentsException;

import java.util.List;
import java.util.Objects;

/**
 * <h2>Базов клас за команди с фиксирана арност (Template Method)</h2>
 *
 * <p>
 * Много CLI команди имат фиксиран брой параметри (арност). Вместо всяка команда да повтаря
 * проверка „колко аргумента са подадени“, този абстрактен клас централизира проверката и
 * стандартизира грешките.
 * </p>
 *
 * <h3>Шаблон (Template Method)</h3>
 *
 * <p>
 * Методът {@link #execute(List)} е {@code final} и реализира общия алгоритъм:
 * </p>
 *
 * <ol>
 *   <li>изчислява реалната арност от {@code args};</li>
 *   <li>сравнява с {@code expectedArity};</li>
 *   <li>при несъответствие хвърля {@link exceptions.cli.InvalidCommandArgumentsException};</li>
 *   <li>при успех делегира към {@link cli.operations.Operation#execute(List)}.</li>
 * </ol>
 *
 * <p>
 * Конкретните команди обикновено само:
 * </p>
 *
 * <ul>
 *   <li>избират {@link cli.command.CommandToken};</li>
 *   <li>подават {@code expectedArity};</li>
 *   <li>дефинират {@link cli.operations.Operation} (често като ламбда) и използват {@link cli.runtime.CliContext}.</li>
 * </ul>
 *
 * <h3>Null поведение</h3>
 *
 * <p>
 * Ако {@code args == null}, тази реализация го третира като „0 аргумента“ и подава празен списък
 * към операцията. Това предпазва от {@link NullPointerException} в командите.
 * </p>
 */
public abstract class AbstractArityCommand implements Command {
    private final CommandToken token;
    private final int expectedArity;
    private final Operation operation;

    /**
     * Конструктор за команда с фиксирана арност.
     *
     * @param token токенът (типът) на командата
     * @param expectedArity очакван брой аргументи; трябва да е {@code >= 0}
     * @param operation стратегията/операцията, която ще се изпълни при валидни аргументи
     * @throws NullPointerException ако {@code token} или {@code operation} е {@code null}
     * @throws IllegalArgumentException ако {@code expectedArity < 0}
     */
    protected AbstractArityCommand(CommandToken token, int expectedArity, Operation operation) {
        this.token = Objects.requireNonNull(token, "token");
        if (expectedArity < 0) {
            throw new IllegalArgumentException("expectedArity must be >= 0");
        }
        this.expectedArity = expectedArity;
        this.operation = Objects.requireNonNull(operation, "operation");
    }

    @Override
    public final CommandToken token() {
        return token;
    }

    @Override
    public final Operation operation() {
        return operation;
    }

    /**
     * Финална имплементация на изпълнение с проверка за арност.
     *
     * @param args аргументите (без името на командата)
     * @throws InvalidCommandArgumentsException ако броят аргументи не съвпада с {@code expectedArity}
     * @throws Exception ако операцията хвърли грешка (домейн или I/O)
     */
    @Override
    public final void execute(List<String> args) throws Exception {
        int actual = args == null ? 0 : args.size();
        if (actual != expectedArity) {
            throw new InvalidCommandArgumentsException(
                    token.name().toLowerCase() + " expects " + expectedArity + " parameter(s), got " + actual
            );
        }
        operation.execute(args == null ? List.of() : args);
    }
}

