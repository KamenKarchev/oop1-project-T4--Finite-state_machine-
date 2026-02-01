/**
 * <h2>CLI команди за операции върху крайни автомати</h2>
 *
 * <p>
 * Команди, които работят с регистъра от автомати и извикват операции от фасадата
 * {@link machine.MachineFacade}. Всички операции работят с машини по целочислено {@code id},
 * което е ключ в {@link machine.MachineRegistry}.
 * </p>
 *
 * <h3>Конвенция: вход/изход</h3>
 *
 * <ul>
 *   <li>Повечето команди печатат или {@code true/false}, или ID на новосъздаден автомат.</li>
 *   <li>
 *     {@code union} и {@code concat} печатат едноредово: {@code id=<id> regex=<regex>}, защото
 *     резултатът се конструира чрез комбиниране на оригиналните регулярни изрази.
 *   </li>
 * </ul>
 *
 * <h3>Парсване на ID</h3>
 *
 * <p>
 * За да е консистентно валидирането на идентификатори, се използва вътрешен helper
 * {@code CliIdParser}, който преобразува текст към {@code int} и хвърля
 * {@link exceptions.cli.InvalidCommandArgumentsException} при невалиден вход.
 * </p>
 */
package cli.command.commands.machine;

