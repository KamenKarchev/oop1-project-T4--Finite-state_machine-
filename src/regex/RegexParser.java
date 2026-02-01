package regex;

import exceptions.machine.InvalidAlphabetException;
import exceptions.machine.InvalidStateException;
import exceptions.regex.RegexParseException;
import machine.MultiedgeFinateLogicMachine;

import java.util.List;
import java.util.Set;

/**
 * @deprecated Replaced by {@link machine.RegexParser}.\n
 * This module is kept only for reference/backwards compatibility.
 */
@Deprecated
public final class RegexParser {
    private RegexParser() {}

    /**
     * Parses a Kleene-style regex into an ε-NFA machine.\n
     * Supported operators: +, implicit concatenation, *, parentheses.\n
     * Supported constants: ε/eps, ∅/empty.\n
     * Literals: [a-z0-9].
     */
    public static MultiedgeFinateLogicMachine parse(String regex) throws RegexParseException {
        RegexTokenizer.Result tok = RegexTokenizer.tokenize(regex);
        List<RegexToken> postfix = RegexToPostfix.toPostfixWithConcat(tok.tokens);

        MultiedgeFinateLogicMachine machine = createMachine(tok.alphabet);
        ThompsonBuilder.Fragment frag = ThompsonBuilder.build(machine, postfix);

        try {
            machine.addStartState(frag.start);
            machine.addAcceptState(frag.accept);
        } catch (Exception e) {
            throw new RegexParseException("Failed to set start/accept states: " + e.getMessage(), e);
        }

        return machine;
    }

    private static MultiedgeFinateLogicMachine createMachine(Set<Character> alphabet) throws RegexParseException {
        try {
            return new MultiedgeFinateLogicMachine(alphabet);
        } catch (InvalidAlphabetException e) {
            throw new RegexParseException("Invalid alphabet derived from regex: " + e.getMessage(), e);
        }
    }
}

