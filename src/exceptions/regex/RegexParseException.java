package exceptions.regex;

/**
 * <h2>Грешка при парсване на регулярен израз</h2>
 *
 * <p>
 * Използва се когато регулярен израз не може да бъде токенизиран/парснат, или когато по време
 * на построяване на автомат от regex възникне домейн грешка.
 * </p>
 */
public class RegexParseException extends Exception {
    /**
     * @param message описание на грешката
     */
    public RegexParseException(String message) {
        super(message);
    }

    /**
     * @param message описание на грешката
     * @param cause първопричина (ако има)
     */
    public RegexParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

