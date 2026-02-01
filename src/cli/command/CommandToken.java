package cli.command;

import exceptions.cli.UnknownCommandTokenException;

/**
 * <h2>Токени (типове) на CLI команди</h2>
 *
 * <p>
 * {@code CommandToken} е изброим тип (enum), който представя всички поддържани команди в CLI.
 * Важно: в този проект резолюцията на команда не зависи само от името, а и от броя параметри
 * (арност). Например {@code save} има два варианта:
 * </p>
 *
 * <ul>
 *   <li>{@code save} (0 параметъра) → {@link #SAVE} — запис на целия регистър в текущия отворен файл.</li>
 *   <li>{@code save <id> <file>} (2 параметъра) → {@link #SAVEM} — запис на една машина в отделен XML файл.</li>
 * </ul>
 *
 * <p>
 * Точно затова публичният вход за резолюция е {@link #resolve(String, int)}, а не „само по име“.
 * </p>
 */
public enum CommandToken {
    OPEN,
    CLOSE,
    SAVE,
    SAVE_AS,
    HELP,
    EXIT,
    LIST,
    PRINT,
    SAVEM,
    EMPTY,
    DETERMINISTIC,
    RECOGNIZE,
    UNION,
    CONCAT,
    UN,
    REG;

    /**
     * <p><b>Deprecated</b>: резолюция само по име (без арност) е неточна.</p>
     *
     * <p>
     * Причина: командата {@code save} е двусмислена без брой аргументи. Затова вместо този метод
     * се използва {@link #resolve(String, int)} след tokenization на реда.
     * </p>
     *
     * @param raw сурово име на команда
     * @return токенът, който съответства на името
     * @throws UnknownCommandTokenException ако името е {@code null}, празно или непознато
     */
    @Deprecated
    public static CommandToken fromString(String raw) throws UnknownCommandTokenException {
        if (raw == null) {
            throw new UnknownCommandTokenException("Command cannot be null");
        }
        String normalized = raw.trim().toLowerCase();
        if (normalized.isEmpty()) {
            throw new UnknownCommandTokenException("Command cannot be empty");
        }
        normalized = normalized.replace("-", "").replace("_", "");

        return switch (normalized) {
            case "open" -> OPEN;
            case "close" -> CLOSE;
            case "save" -> SAVE;
            case "saveas" -> SAVE_AS;
            case "help" -> HELP;
            case "exit", "quit" -> EXIT;
            case "print" -> PRINT;
            default -> throw new UnknownCommandTokenException("Unknown command: " + raw);
        };
    }

    /**
     * Резолва команда по име <b>и</b> по брой параметри (арност).
     *
     * <p>
     * Този метод е „истинската“ резолюция в системата. Първо името се нормализира
     * (lowercase, махане на '-' и '_'), след което се избира команда.
     * </p>
     *
     * <p>
     * За повечето команди има фиксирана арност (напр. {@code print} изисква 1 параметър).
     * Ако арността не съвпада, се хвърля {@link UnknownCommandTokenException} с ясно съобщение.
     * </p>
     *
     * <p><b>Специален случай: {@code save}</b></p>
     *
     * <ul>
     *   <li>{@code save} (0 параметъра) → {@link #SAVE}</li>
     *   <li>{@code save <id> <file>} (2 параметъра) → {@link #SAVEM}</li>
     * </ul>
     *
     * @param commandName име на командата (първи токен от реда)
     * @param paramCount брой параметри (всички токени след името)
     * @return конкретният {@link CommandToken}, който трябва да се изпълни
     * @throws UnknownCommandTokenException при непозната команда или невалидна арност
     */
    public static CommandToken resolve(String commandName, int paramCount) throws UnknownCommandTokenException {
        if (commandName == null) {
            throw new UnknownCommandTokenException("Command cannot be null");
        }
        String normalized = commandName.trim().toLowerCase();
        if (normalized.isEmpty()) {
            throw new UnknownCommandTokenException("Command cannot be empty");
        }
        normalized = normalized.replace("-", "").replace("_", "");

        return switch (normalized) {
            case "open" -> requireArity(OPEN, paramCount, 1);
            case "close" -> requireArity(CLOSE, paramCount, 0);
            case "save" -> {
                if (paramCount == 0) yield SAVE;
                if (paramCount == 2) yield SAVEM;
                throw new UnknownCommandTokenException("Invalid arguments for save: expected 0 or 2 parameters, got " + paramCount);
            }
            case "saveas" -> requireArity(SAVE_AS, paramCount, 1);
            case "help" -> requireArity(HELP, paramCount, 0);
            case "exit", "quit" -> requireArity(EXIT, paramCount, 0);
            case "list" -> requireArity(LIST, paramCount, 0);
            case "print" -> requireArity(PRINT, paramCount, 1);
            case "empty" -> requireArity(EMPTY, paramCount, 1);
            case "deterministic" -> requireArity(DETERMINISTIC, paramCount, 1);
            case "recognize" -> requireArity(RECOGNIZE, paramCount, 2);
            case "union" -> requireArity(UNION, paramCount, 2);
            case "concat" -> requireArity(CONCAT, paramCount, 2);
            case "un" -> requireArity(UN, paramCount, 1);
            case "reg" -> requireArity(REG, paramCount, 1);
            default -> throw new UnknownCommandTokenException("Unknown command: " + commandName);
        };
    }

    /**
     * Вътрешна помощна функция: валидира фиксирана арност за командата.
     *
     * @param token токенът, който ще върнем при успех
     * @param actual реалният брой параметри от входа
     * @param expected очакваният брой параметри
     * @return {@code token} при съвпадение на арност
     * @throws UnknownCommandTokenException ако арността не съвпада
     */
    private static CommandToken requireArity(CommandToken token, int actual, int expected) throws UnknownCommandTokenException {
        if (actual != expected) {
            throw new UnknownCommandTokenException(
                    "Invalid arguments for " + token.name().toLowerCase() + ": expected " + expected + " parameters, got " + actual
            );
        }
        return token;
    }
}