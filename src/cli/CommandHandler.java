package cli.command;

import cli.command.factory.CommandFactory;
import cli.command.factory.CommandFactoryRegistry;
import cli.command.factory.impl.CloseCommandFactory;
import cli.command.factory.impl.ConcatCommandFactory;
import cli.command.factory.impl.DeterministicCommandFactory;
import cli.command.factory.impl.EmptyCommandFactory;
import cli.command.factory.impl.ExitCommandFactory;
import cli.command.factory.impl.HelpCommandFactory;
import cli.command.factory.impl.ListCommandFactory;
import cli.command.factory.impl.OpenCommandFactory;
import cli.command.factory.impl.PrintCommandFactory;
import cli.command.factory.impl.RecognizeCommandFactory;
import cli.command.factory.impl.RegCommandFactory;
import cli.command.factory.impl.SaveAsCommandFactory;
import cli.command.factory.impl.SaveCommandFactory;
import cli.command.factory.impl.SaveMachineCommandFactory;
import cli.command.factory.impl.UnCommandFactory;
import cli.command.factory.impl.UnionCommandFactory;
import cli.runtime.CliContext;
import io.MachineXmlStore;
import io.RegistryFileSession;
import machine.MachineFacade;
import machine.MachineRegistry;

/**
 * <h2>Диспечер на CLI команди (Singleton)</h2>
 *
 * <p>
 * {@code CommandHandler} е централната точка за изпълнение на командите от конзолата.
 * Той приема ред (стринг), парсва го до име на команда + параметри, резолва го до конкретен
 * {@link cli.command.CommandToken} (включително на база брой параметри), намира фабрика за
 * съответния токен и изпълнява създадената команда.
 * </p>
 *
 * <h3>Архитектурни роли</h3>
 *
 * <ul>
 *   <li>
 *     <b>Tokenization</b>:
 *     {@link cli.command.CommandLineTokenizer} разделя реда на {@code commandName + params}.
 *     Поддържа кавички и празен аргумент {@code \"\"}.
 *   </li>
 *   <li>
 *     <b>Резолюция на команда</b>:
 *     {@link cli.command.CommandToken#resolve(String, int)} решава коя команда е поискана,
 *     като отчита броя параметри. Това е важно за двусмислени имена като {@code save}
 *     (0 параметъра -> {@code SAVE}, 2 параметъра -> {@code SAVEM}).
 *   </li>
 *   <li>
 *     <b>Създаване на команда</b>:
 *     {@link cli.command.factory.CommandFactoryRegistry} връща фабрика, която създава конкретна
 *     {@link cli.command.Command}.
 *   </li>
 *   <li>
 *     <b>Изпълнение</b>:
 *     Командата валидира арност (чрез {@code AbstractArityCommand}) и делегира към
 *     {@link cli.operations.Operation}.
 *   </li>
 * </ul>
 *
 * <h3>Защо Singleton?</h3>
 *
 * <p>
 * Проектът е учебен и CLI-то е централизирано около една сесия (един регистър с автомати, една
 * „отворена“ файлова сесия и общ изход {@link java.io.PrintStream}). Затова е удобно да има един
 * инстанс на handler-а, който подготвя контекста и регистрира всички фабрики точно веднъж.
 * </p>
 */
public class CommandHandler {

    private static CommandHandler instance;
    private final CommandFactoryRegistry registry;

    private CommandHandler(CommandFactoryRegistry registry) {
        this.registry = registry;
    }

    /**
     * Връща singleton инстанцията на handler-а и (при първо извикване) инициализира CLI контекста
     * и регистрира всички фабрики за команди.
     *
     * @return singleton инстанция
     */
    public static CommandHandler getInstance() {
        if (instance == null) {
            // Създаваме домейн-обектите за текущата CLI сесия.
            MachineRegistry machineRegistry = new MachineRegistry();
            MachineFacade machineFacade = new MachineFacade(machineRegistry);
            MachineXmlStore xmlStore = new MachineXmlStore();
            RegistryFileSession fileSession = new RegistryFileSession(xmlStore);
            CliContext ctx = new CliContext(machineRegistry, machineFacade, xmlStore, fileSession, System.out);

            CommandFactoryRegistry reg = new CommandFactoryRegistry();

            // Регистрация на фабрики за команди.
            // Идеята: всяка команда има отделна фабрика (Abstract Factory), което прави разширяването лесно.

            // base
            reg.register(new HelpCommandFactory(ctx));
            reg.register(new ExitCommandFactory(ctx));

            // file/database
            reg.register(new OpenCommandFactory(ctx));
            reg.register(new CloseCommandFactory(ctx));
            reg.register(new SaveCommandFactory(ctx));
            reg.register(new SaveAsCommandFactory(ctx));

            // machine
            reg.register(new ListCommandFactory(ctx));
            reg.register(new PrintCommandFactory(ctx));
            reg.register(new RegCommandFactory(ctx));
            reg.register(new EmptyCommandFactory(ctx));
            reg.register(new DeterministicCommandFactory(ctx));
            reg.register(new RecognizeCommandFactory(ctx));
            reg.register(new UnionCommandFactory(ctx));
            reg.register(new ConcatCommandFactory(ctx));
            reg.register(new UnCommandFactory(ctx));
            reg.register(new SaveMachineCommandFactory(ctx));

            instance = new CommandHandler(reg);
        }
        return instance;
    }

    /**
     * Обработва един ред от конзолата.
     *
     * <p>
     * Методът реализира целия pipeline:
     * </p>
     *
     * <ol>
     *   <li>Парсва реда до {@code commandName + params}.</li>
     *   <li>Резолва го до {@link cli.command.CommandToken} на база име + брой параметри.</li>
     *   <li>Намира фабрика за токена и създава {@link cli.command.Command}.</li>
     *   <li>Изпълнява командата с параметрите.</li>
     * </ol>
     *
     * <p>
     * Връща {@code false} единствено при {@code exit}, за да може main-loop-а да приключи.
     * </p>
     *
     * @param line суров ред от конзолата; не трябва да е {@code null} или празен след trim
     * @return {@code true} ако CLI трябва да продължи; {@code false} ако трябва да се прекрати
     * @throws Exception ако има проблем при парсване, резолюция или изпълнение на командата
     *                  (напр. непознат токен, грешна арност, липсваща машина по ID, проблем при I/O)
     */
    public boolean handleLine(String line) throws Exception {
        CommandLineTokenizer.CommandLineTokens tokens = CommandLineTokenizer.tokenize(line);
        CommandToken token = CommandToken.resolve(tokens.commandName(), tokens.params().size());
        CommandFactory factory = registry.get(token);
        Command cmd = factory.create();
        cmd.execute(tokens.params());
        return token != CommandToken.EXIT;
    }
}
