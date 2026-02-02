/**
 * <h2>Приложение (entry point)</h2>
 *
 * <p>
 * Пакетът съдържа входната точка на приложението {@link app.Main}. Програмата стартира
 * интерактивен CLI цикъл, който чете редове от стандартния вход и ги подава към
 * {@link cli.command.CommandHandler}.
 * </p>
 *
 * <h3>Поток</h3>
 *
 * <ol>
 *   <li>{@link app.Main} създава {@link java.util.Scanner}.</li>
 *   <li>В цикъл чете редове и извиква {@link cli.command.CommandHandler#handleLine(String)}.</li>
 *   <li>При {@code exit} CLI цикълът приключва.</li>
 * </ol>
 */
package app;

