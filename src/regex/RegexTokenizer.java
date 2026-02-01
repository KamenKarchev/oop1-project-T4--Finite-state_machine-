package regex;

import exceptions.regex.RegexParseException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @deprecated Replaced by {@link machine.RegexParser}.\n
 * This module is kept only for reference/backwards compatibility.
 */
@Deprecated
final class RegexTokenizer {
    private RegexTokenizer() {}

    static final class Result {
        final List<RegexToken> tokens;
        final Set<Character> alphabet;

        Result(List<RegexToken> tokens, Set<Character> alphabet) {
            this.tokens = tokens;
            this.alphabet = alphabet;
        }
    }

    static Result tokenize(String regex) throws RegexParseException {
        if (regex == null) {
            throw new RegexParseException("Regex cannot be null");
        }
        String input = regex.trim();
        if (input.isEmpty()) {
            throw new RegexParseException("Regex cannot be empty");
        }

        List<RegexToken> out = new ArrayList<>();
        Set<Character> alphabet = new HashSet<>();

        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // keywords: eps / empty
            if (Character.isLetter(c)) {
                int j = i;
                while (j < input.length() && Character.isLetter(input.charAt(j))) {
                    j++;
                }
                String word = input.substring(i, j).toLowerCase();
                if (word.equals("eps")) {
                    out.add(RegexToken.of(RegexToken.Type.EPSILON));
                    i = j;
                    continue;
                }
                if (word.equals("empty")) {
                    out.add(RegexToken.of(RegexToken.Type.EMPTY));
                    i = j;
                    continue;
                }

                // otherwise, treat letters one by one (must be a-z)
                for (int k = i; k < j; k++) {
                    char ch = input.charAt(k);
                    if (ch < 'a' || ch > 'z') {
                        throw new RegexParseException("Invalid literal: " + ch + " (allowed: a-z, 0-9)");
                    }
                    out.add(RegexToken.lit(ch));
                    alphabet.add(ch);
                }
                i = j;
                continue;
            }

            if (c >= '0' && c <= '9') {
                out.add(RegexToken.lit(c));
                alphabet.add(c);
                i++;
                continue;
            }

            switch (c) {
                case 'ε' -> out.add(RegexToken.of(RegexToken.Type.EPSILON));
                case '∅' -> out.add(RegexToken.of(RegexToken.Type.EMPTY));
                case '+' -> out.add(RegexToken.of(RegexToken.Type.PLUS));
                case '*' -> out.add(RegexToken.of(RegexToken.Type.STAR));
                case '(' -> out.add(RegexToken.of(RegexToken.Type.LPAREN));
                case ')' -> out.add(RegexToken.of(RegexToken.Type.RPAREN));
                default -> throw new RegexParseException("Unexpected character in regex: '" + c + "'");
            }
            i++;
        }

        return new Result(out, alphabet);
    }
}

