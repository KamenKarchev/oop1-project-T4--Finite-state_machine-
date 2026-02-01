package cli.command;

import cli.operations.Operation;

import java.util.List;

/**
 * <h2>Интерфейс за CLI команда (Command pattern)</h2>
 *
 * <p>
 * CLI командата е обект, който може да бъде изпълнен с списък от параметри.
 * В този проект командата логически има две части:
 * </p>
 *
 * <ul>
 *   <li><b>Идентичност</b> (кой токен/тип команда е това) — {@link #token()}.</li>
 *   <li><b>Поведение</b> (какво прави) — {@link #operation()} и {@link #execute(List)}.</li>
 * </ul>
 *
 * <p>
 * В повечето случаи конкретните команди наследяват {@code AbstractArityCommand}, който:
 * </p>
 *
 * <ul>
 *   <li>проверява броя параметри (арност);</li>
 *   <li>делегира към {@link cli.operations.Operation}.</li>
 * </ul>
 *
 * <h3>Аргументи</h3>
 *
 * <p>
 * Аргументите идват от {@link cli.command.CommandLineTokenizer} като {@link java.util.List} от низове,
 * в реда в който са въведени.
 * </p>
 */
public interface Command {
    /**
     * Връща токена (тип/име) на командата.
     *
     * @return токенът (типът) на тази команда
     */
    CommandToken token();

    /**
     * Връща операцията (стратегията), която командата изпълнява.
     *
     * @return стратегията (операцията), която командата изпълнява
     */
    Operation operation();

    /**
     * Изпълнява командата с подадените параметри.
     *
     * <p>
     * Контрактът (валидиране, грешки, side effects) е специфичен за конкретната команда.
     * При команди, наследяващи {@code AbstractArityCommand}, този метод:
     * </p>
     *
     * <ul>
     *   <li>валидира точния брой параметри;</li>
     *   <li>извиква {@link #operation()}.</li>
     * </ul>
     *
     * @param args параметри от CLI (може да е празен списък за parameterless команди)
     * @throws Exception ако има проблем при валидиране или изпълнение
     */
    void execute(List<String> args) throws Exception;
}
