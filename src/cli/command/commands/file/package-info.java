/**
 * <h2>Файлови CLI команди (XML persistence)</h2>
 *
 * <p>
 * Команди, които управляват запис/зареждане на регистъра с автомати към/от XML файлове.
 * Използват се два основни компонента от {@link cli.runtime.CliContext}:
 * </p>
 *
 * <ul>
 *   <li>{@link io.MachineXmlStore}: сериализация/десериализация на XML (формат на автомати).</li>
 *   <li>{@link io.RegistryFileSession}: „сесия“ с текущ отворен файл (за {@code save} без параметри).</li>
 * </ul>
 *
 * <p>
 * Тези команди обичайно променят състоянието на сесията: зареждане подменя съдържанието на
 * текущия {@link machine.MachineRegistry}; {@code save}/{@code saveas} записват текущия регистър;
 * {@code close} забравя текущия файл, но не трие данни от паметта.
 * </p>
 */
package cli.command.commands.file;

