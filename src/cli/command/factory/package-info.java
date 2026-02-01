/**
 * <h2>Фабрики за CLI команди (Abstract Factory)</h2>
 *
 * <p>
 * Пакетът съдържа абстракции за създаване на CLI команди чрез шаблона <b>Abstract Factory</b>.
 * Всяка конкретна команда има собствена фабрика (в {@code cli.command.factory.impl}), което прави
 * системата лесна за разширяване: добавянето на нова команда обикновено означава добавяне на:
 * </p>
 *
 * <ul>
 *   <li>нов {@link cli.command.CommandToken} (ако е нова команда/токен);</li>
 *   <li>нов клас команда (обикновено в {@code cli.command.commands.*});</li>
 *   <li>нова фабрика, имплементираща {@link cli.command.factory.CommandFactory};</li>
 *   <li>регистрация на фабриката в {@link cli.command.CommandHandler}.</li>
 * </ul>
 *
 * <p>
 * Регистърът {@link cli.command.factory.CommandFactoryRegistry} представлява проста мап-структура
 * {@code CommandToken -> CommandFactory}. Това разделя отговорностите:
 * </p>
 *
 * <ul>
 *   <li>{@link cli.command.CommandHandler}: парсва ред, резолва токен, намира фабрика, изпълнява.</li>
 *   <li>{@link cli.command.factory.CommandFactoryRegistry}: съхранява фабриките и връща правилната.</li>
 *   <li>{@link cli.command.factory.CommandFactory}: създава конкретна {@link cli.command.Command}.</li>
 * </ul>
 */
package cli.command.factory;

