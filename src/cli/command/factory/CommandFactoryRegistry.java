package cli.command.factory;

import cli.command.CommandToken;
import exceptions.cli.CliException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>Регистър на фабрики за команди</h2>
 *
 * <p>
 * {@code CommandFactoryRegistry} държи съответствие между {@link cli.command.CommandToken}
 * и {@link cli.command.factory.CommandFactory}.
 * </p>
 *
 * <p>
 * Използва {@link java.util.EnumMap}, защото ключът е enum и това е ефективна имплементация
 * (малко памет и бърз достъп).
 * </p>
 *
 * <h3>Поведение</h3>
 *
 * <ul>
 *   <li>{@link #register(CommandFactory)}: регистрира/заменя фабрика за даден токен.</li>
 *   <li>{@link #get(cli.command.CommandToken)}: връща фабрика или хвърля {@link exceptions.cli.CliException}.</li>
 * </ul>
 */
public class CommandFactoryRegistry {
    private final Map<CommandToken, CommandFactory> factories = new EnumMap<>(CommandToken.class);

    /**
     * Регистрира фабрика. Ако вече има фабрика за същия токен, тя се заменя.
     *
     * @param factory фабрика (не {@code null})
     * @throws NullPointerException ако {@code factory == null}
     */
    public void register(CommandFactory factory) {
        Objects.requireNonNull(factory, "factory");
        factories.put(factory.token(), factory);
    }

    /**
     * Връща фабриката за даден токен.
     *
     * @param token токен на команда
     * @return фабрика за този токен
     * @throws CliException ако няма регистрирана фабрика
     */
    public CommandFactory get(CommandToken token) throws CliException {
        CommandFactory factory = factories.get(token);
        if (factory == null) {
            throw new CliException("No factory registered for token: " + token);
        }
        return factory;
    }
}

