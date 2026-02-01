package cli.command.factory;

import cli.command.Command;
import cli.command.CommandToken;
import exceptions.cli.CliException;

/**
 * <h2>Фабрика за CLI команди (Abstract Factory)</h2>
 *
 * <p>
 * {@code CommandFactory} капсулира създаването на конкретна {@link cli.command.Command}.
 * В този проект всяка команда има отделна фабрика, която:
 * </p>
 *
 * <ul>
 *   <li>декларира кой {@link cli.command.CommandToken} обслужва чрез {@link #token()};</li>
 *   <li>създава нова инстанция на команда чрез {@link #create()}.</li>
 * </ul>
 *
 * <p>
 * Това позволява {@link cli.command.CommandHandler} да остане прост:
 * той само резолва токена и пита регистъра за фабрика.
 * </p>
 */
public interface CommandFactory {
    /**
     * @return токенът, за който тази фабрика създава команда
     */
    CommandToken token();

    /**
     * Създава нова команда.
     *
     * <p>
     * Командите са обикновено „леки“ обекти, затова се създават при всяко изпълнение.
     * </p>
     *
     * @return нова инстанция на команда
     * @throws CliException ако по някаква причина командата не може да бъде създадена
     */
    Command create() throws CliException;
}

