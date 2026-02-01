package exceptions.regex;

public class RegexParseException extends Exception {
    public RegexParseException(String message) {
        super(message);
    }

    public RegexParseException(String message, Throwable cause) {
        super(message, cause);
    }
}

