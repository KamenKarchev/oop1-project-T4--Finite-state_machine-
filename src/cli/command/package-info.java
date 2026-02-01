/**
 * <h2>CLI протокол и диспечиране на команди</h2>
 *
 * <p>
 * Този пакет описва „протокола“ на конзолния интерфейс (CLI) на проекта: как се приема ред от
 * потребителя, как се разделя на токени (име на команда + параметри), как се резолва към конкретен
 * {@link cli.command.CommandToken} с конкретна арност (брой параметри), и как се намира и изпълнява
 * съответната команда.
 * </p>
 *
 * <h3>Идеята: Command + Operation + Abstract Factory</h3>
 *
 * <p>
 * В CLI слоя използваме няколко класически шаблона (design patterns):
 * </p>
 *
 * <ul>
 *   <li>
 *     <b>Command pattern</b>:
 *     {@link cli.command.Command} представя „изпълнима“ команда от вида
 *     {@code token + operation + execute(args)}.
 *   </li>
 *   <li>
 *     <b>Strategy pattern</b>:
 *     реалната работа на командата е инкапсулирана в {@link cli.operations.Operation} (функционален
 *     интерфейс), което позволява операции да се дефинират като ламбда/inline стратегия.
 *   </li>
 *   <li>
 *     <b>Abstract Factory</b>:
 *     командите се конструират чрез {@link cli.command.factory.CommandFactory}, а всички фабрики
 *     се регистрират в {@link cli.command.factory.CommandFactoryRegistry}.
 *   </li>
 * </ul>
 *
 * <h3>Поток на изпълнение</h3>
 *
 * <ol>
 *   <li>Потребителят въвежда ред: напр. {@code recognize 3 ""}.</li>
 *   <li>Редът се разделя на токени чрез {@link cli.command.CommandLineTokenizer}.</li>
 *   <li>
 *     Името на командата и броят параметри се подават към
 *     {@link cli.command.CommandToken#resolve(String, int)}.
 *     Това е важно, защото някои команди могат да се различават само по арност (пример: {@code save}).
 *   </li>
 *   <li>От {@link cli.command.factory.CommandFactoryRegistry} се намира фабрика за токена.</li>
 *   <li>Фабриката създава {@link cli.command.Command} и командата се изпълнява с параметрите.</li>
 * </ol>
 *
 * <h3>Важно за парсването на аргументи</h3>
 *
 * <p>
 * Токенизаторът поддържа кавички за параметри. Специално е добавена поддръжка за празен
 * параметър {@code ""}, за да може да се подава празна дума при разпознаване:
 * {@code recognize <id> ""}.
 * </p>
 */
package cli.command;

