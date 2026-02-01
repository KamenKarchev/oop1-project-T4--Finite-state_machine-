package regex;

import exceptions.regex.RegexParseException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @deprecated Replaced by {@link machine.RegexParser}.\n
 * This module is kept only for reference/backwards compatibility.
 */
@Deprecated
final class RegexToPostfix {
    private RegexToPostfix() {}

    static List<RegexToken> toPostfixWithConcat(List<RegexToken> tokens) throws RegexParseException {
        List<RegexToken> withConcat = insertConcat(tokens);
        return shuntingYard(withConcat);
    }

    private static List<RegexToken> insertConcat(List<RegexToken> tokens) throws RegexParseException {
        List<RegexToken> out = new ArrayList<>();
        RegexToken prev = null;

        for (RegexToken cur : tokens) {
            if (prev != null) {
                boolean prevCanEnd =
                        prev.isOperand()
                                || prev.type == RegexToken.Type.RPAREN
                                || prev.type == RegexToken.Type.STAR;

                boolean curCanStart =
                        cur.isOperand()
                                || cur.type == RegexToken.Type.LPAREN;

                if (prevCanEnd && curCanStart) {
                    out.add(RegexToken.of(RegexToken.Type.CONCAT));
                }
            }
            out.add(cur);
            prev = cur;
        }

        return out;
    }

    private static int precedence(RegexToken.Type type) {
        return switch (type) {
            case PLUS -> 1;
            case CONCAT -> 2;
            default -> 0;
        };
    }

    private static boolean isBinaryOperator(RegexToken.Type type) {
        return type == RegexToken.Type.PLUS || type == RegexToken.Type.CONCAT;
    }

    private static List<RegexToken> shuntingYard(List<RegexToken> tokens) throws RegexParseException {
        List<RegexToken> output = new ArrayList<>();
        Deque<RegexToken> ops = new ArrayDeque<>();

        RegexToken prev = null;

        for (RegexToken t : tokens) {
            switch (t.type) {
                case LITERAL, EPSILON, EMPTY -> output.add(t);
                case LPAREN -> ops.push(t);
                case RPAREN -> {
                    boolean found = false;
                    while (!ops.isEmpty()) {
                        RegexToken top = ops.pop();
                        if (top.type == RegexToken.Type.LPAREN) {
                            found = true;
                            break;
                        }
                        output.add(top);
                    }
                    if (!found) {
                        throw new RegexParseException("Mismatched parentheses: missing '('");
                    }
                }
                case STAR -> {
                    // postfix unary: must have something before it
                    if (prev == null || prev.type == RegexToken.Type.PLUS || prev.type == RegexToken.Type.CONCAT || prev.type == RegexToken.Type.LPAREN) {
                        throw new RegexParseException("'*' has no operand");
                    }
                    output.add(t);
                }
                case PLUS, CONCAT -> {
                    // binary operator: must have operand before it
                    if (prev == null || prev.type == RegexToken.Type.PLUS || prev.type == RegexToken.Type.CONCAT || prev.type == RegexToken.Type.LPAREN) {
                        throw new RegexParseException("Binary operator '" + t.type + "' missing left operand");
                    }
                    while (!ops.isEmpty() && isBinaryOperator(ops.peek().type)
                            && precedence(ops.peek().type) >= precedence(t.type)) {
                        output.add(ops.pop());
                    }
                    ops.push(t);
                }
                default -> throw new RegexParseException("Unexpected token: " + t);
            }
            prev = t;
        }

        // expression cannot end with binary operator
        if (prev != null && (prev.type == RegexToken.Type.PLUS || prev.type == RegexToken.Type.CONCAT)) {
            throw new RegexParseException("Expression cannot end with operator");
        }

        while (!ops.isEmpty()) {
            RegexToken top = ops.pop();
            if (top.type == RegexToken.Type.LPAREN) {
                throw new RegexParseException("Mismatched parentheses: missing ')'");
            }
            output.add(top);
        }

        return output;
    }
}

