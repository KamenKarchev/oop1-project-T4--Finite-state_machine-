package cli.runtime;

import io.MachineXmlStore;
import io.RegistryFileSession;
import machine.MachineFacade;
import machine.MachineRegistry;

import java.io.PrintStream;
import java.util.Objects;

/**
 * <h2>Контекст (runtime state) на CLI сесията</h2>
 *
 * <p>
 * {@code CliContext} съдържа всички „споделени“ зависимости и състояние, които са нужни на
 * CLI командите по време на изпълнение. Вместо командите да използват глобални променливи,
 * те получават {@code CliContext} (dependency injection) и работят през него.
 * </p>
 *
 * <h3>Какво съдържа контекстът?</h3>
 *
 * <ul>
 *   <li>{@link machine.MachineRegistry}: паметният регистър (id → машина) за текущата сесия.</li>
 *   <li>{@link machine.MachineFacade}: фасада за операции над машини (reg, union, recognize и т.н.).</li>
 *   <li>{@link io.MachineXmlStore}: сериализация/десериализация на XML формат.</li>
 *   <li>{@link io.RegistryFileSession}: „отворен файл“ за командата {@code save} без аргументи.</li>
 *   <li>{@link java.io.PrintStream}: изходът, в който командите печатат резултати/съобщения.</li>
 * </ul>
 *
 * <h3>Защо е полезно това разделение?</h3>
 *
 * <ul>
 *   <li>Командите са по-малки и по-лесни за четене.</li>
 *   <li>Лесно може да се подмени изходът (например към файл/тестов буфер).</li>
 *   <li>Улеснява тестове и бъдещи промени (например друг storage).</li>
 * </ul>
 */
public final class CliContext {
    private final MachineFacade machines;
    private final MachineRegistry registry;
    private final MachineXmlStore xmlStore;
    private final RegistryFileSession fileSession;
    private final PrintStream out;

    /**
     * Създава нов CLI контекст.
     *
     * @param registry регистър на машини за текущата сесия
     * @param machines фасада за операции над машини; обикновено работи върху {@code registry}
     * @param xmlStore компонент за XML сериализация/десериализация
     * @param fileSession държи текущ „отворен“ файл за {@code save} (0 аргумента)
     * @param out изход (където командите печатат)
     * @throws NullPointerException ако някой аргумент е {@code null}
     */
    public CliContext(
            MachineRegistry registry,
            MachineFacade machines,
            MachineXmlStore xmlStore,
            RegistryFileSession fileSession,
            PrintStream out
    ) {
        this.registry = Objects.requireNonNull(registry, "registry");
        this.machines = Objects.requireNonNull(machines, "machines");
        this.xmlStore = Objects.requireNonNull(xmlStore, "xmlStore");
        this.fileSession = Objects.requireNonNull(fileSession, "fileSession");
        this.out = Objects.requireNonNull(out, "out");
    }

    /**
     * Достъп до регистъра на автомати за текущата сесия.
     *
     * @return регистъра (id → машина) за текущата сесия
     */
    public MachineRegistry registry() {
        return registry;
    }

    /**
     * Достъп до фасадата за операции над автомати.
     *
     * @return фасадата за домейн операции над машини (работи с ID-та от регистъра)
     */
    public MachineFacade machines() {
        return machines;
    }

    /**
     * Достъп до XML стор-а за сериализация/десериализация.
     *
     * @return XML store за четене/писане на машини/регистър
     */
    public MachineXmlStore xmlStore() {
        return xmlStore;
    }

    /**
     * Достъп до файл-сесията (кой файл е „отворен“ за save).
     *
     * @return файлова „сесия“ (коя е текущата отворена база/файл за save)
     */
    public RegistryFileSession fileSession() {
        return fileSession;
    }

    /**
     * Достъп до изходния поток за печат.
     *
     * @return изходът, където командите пишат резултат/съобщения
     */
    public PrintStream out() {
        return out;
    }
}
