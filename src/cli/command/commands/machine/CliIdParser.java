package cli.command.commands.machine;

import exceptions.cli.InvalidCommandArgumentsException;

/**
 * <h2>Helper за парсване на машинни идентификатори</h2>
 *
 * <p>
 * В CLI командите машините се адресират чрез целочислен {@code id}.
 * Този клас централизира логиката за преобразуване на текстов аргумент към {@code int} и
 * за генериране на консистентни грешки при невалиден вход.
 * </p>
 *
 * <p>
 * Класът е {@code package-private} и {@code final}, защото е вътрешна реализация за командите
 * в пакета {@code cli.command.commands.machine}.
 * </p>
 */
final class CliIdParser {
    private CliIdParser() {}

    /**
     * Парсва {@code id} от CLI аргумент.
     *
     * <p>
     * При невалиден формат хвърля {@link InvalidCommandArgumentsException}, за да може
     * {@link cli.command.CommandHandler} / main-loop-а да покажат смислено съобщение на потребителя.
     * </p>
     *
     * @param raw суров текстов аргумент, напр. {@code "12"}
     * @return парсираното {@code int} ID
     * @throws InvalidCommandArgumentsException ако {@code raw} е {@code null} или не е число
     */
    static int parseId(String raw) throws InvalidCommandArgumentsException {
        if (raw == null) {
            throw new InvalidCommandArgumentsException("id cannot be null");
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Invalid id: " + raw);
        }
    }
}

