package regex;

/**
 * Internal token for the regex parser.
 *
 * @deprecated Replaced by {@link machine.RegexParser}.\n
 * This module is kept only for reference/backwards compatibility.
 */
@Deprecated
final class RegexToken {
    enum Type {
        LITERAL,
        EPSILON,
        EMPTY,
        PLUS,      // +
        CONCAT,    // implicit concatenation (internal)
        STAR,      // *
        LPAREN,    // (
        RPAREN     // )
    }

    final Type type;
    final Character literal; // only for LITERAL

    private RegexToken(Type type, Character literal) {
        this.type = type;
        this.literal = literal;
    }

    static RegexToken lit(char c) {
        return new RegexToken(Type.LITERAL, c);
    }

    static RegexToken of(Type type) {
        return new RegexToken(type, null);
    }

    boolean isOperand() {
        return type == Type.LITERAL || type == Type.EPSILON || type == Type.EMPTY;
    }

    @Override
    public String toString() {
        return type == Type.LITERAL ? ("LITERAL(" + literal + ")") : type.name();
    }
}

