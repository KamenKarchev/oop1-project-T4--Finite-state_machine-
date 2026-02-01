package cli.command;

import exceptions.cli.UnknownCommandTokenException;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Токенизатор на CLI ред</h2>
 *
 * <p>
 * Вътрешен helper, който разделя един входен ред на:
 * </p>
 *
 * <ul>
 *   <li><b>име на команда</b> (първият токен)</li>
 *   <li><b>параметри</b> (всички следващи токени)</li>
 * </ul>
 *
 * <p>
 * Поддържа параметри в кавички, за да може един параметър да съдържа интервали или да е празен.
 * Например:
 * </p>
 *
 * <ul>
 *   <li>{@code recognize 1 ""} → commandName={@code recognize}, params=[{@code 1}, {@code ""} (празен стринг)]</li>
 *   <li>{@code reg "(a+b).c"} → commandName={@code reg}, params=[{@code (a+b).c}]</li>
 * </ul>
 *
 * <h3>Алгоритъм</h3>
 *
 * <p>
 * {@link #splitArgs(String)} работи като малка крайна машина (state machine) със състояния:
 * </p>
 *
 * <ul>
 *   <li>{@code inQuotes}: дали сме вътре в кавички</li>
 *   <li>{@code tokenWasQuoted}: дали текущият токен е бил в кавички (важно за {@code ""})</li>
 * </ul>
 *
 * <p>
 * Когато срещнем интервал извън кавички, приключваме текущия токен. Ако токенът е празен, но е
 * бил в кавички, пак го добавяме — това е ключово за правилно представяне на празен параметър.
 * </p>
 */
final class CommandLineTokenizer {
    private CommandLineTokenizer() {}

    /**
     * Парсва суров CLI ред и връща структура с име на команда и параметри.
     *
     * @param line суров ред от конзолата
     * @return {@link CommandLineTokens} (име + params)
     * @throws UnknownCommandTokenException ако {@code line} е {@code null} или празен след {@code trim()}
     * @throws IndexOutOfBoundsException ако след split няма първи токен (не би трябвало при валиден вход)
     */
    static CommandLineTokens tokenize(String line) throws UnknownCommandTokenException {
        if (line == null) {
            throw new UnknownCommandTokenException("Command cannot be null");
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            throw new UnknownCommandTokenException("Command cannot be empty");
        }

        List<String> parts = splitArgs(trimmed);
        String command = parts.get(0);
        List<String> params = parts.size() > 1 ? parts.subList(1, parts.size()) : List.of();
        return new CommandLineTokens(command, params);
    }

    /**
     * Разделя входен текст на токени, като уважава двойни кавички.
     *
     * <p>
     * Този метод е „нисък слой“ и не знае нищо за конкретни команди — просто връща списък от части.
     * Първият елемент по конвенция е името на командата.
     * </p>
     *
     * <p><b>Важно</b>: поддържа празен токен в кавички.</p>
     *
     * <pre>
     * splitArgs("recognize 1 \"\"") -> ["recognize", "1", ""]
     * </pre>
     *
     * @param input входният ред (обикновено trim-нат)
     * @return списък от токени (никога {@code null})
     */
    private static List<String> splitArgs(String input) {
        List<String> out = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        boolean tokenWasQuoted = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
                tokenWasQuoted = true;
                continue;
            }
            if (!inQuotes && Character.isWhitespace(c)) {
                if (!current.isEmpty() || tokenWasQuoted) {
                    out.add(current.toString());
                    current.setLength(0);
                    tokenWasQuoted = false;
                }
                continue;
            }
            current.append(c);
        }
        if (!current.isEmpty() || tokenWasQuoted) {
            out.add(current.toString());
        }
        return out;
    }

    /**
     * Минимален преносим резултат от tokenization-а: име на команда + параметри.
     *
     * @param commandName името на командата (първи токен)
     * @param params параметрите (всички токени след първия)
     */
    record CommandLineTokens(String commandName, List<String> params) {}
}

