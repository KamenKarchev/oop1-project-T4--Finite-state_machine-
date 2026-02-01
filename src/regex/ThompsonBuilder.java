package regex;

import exceptions.graph.GraphException;
import exceptions.machine.InvalidStateException;
import exceptions.machine.InvalidSymbolException;
import exceptions.regex.RegexParseException;
import graph.mdg.MDGNode;
import machine.MultiedgeFinateLogicMachine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @deprecated Replaced by {@link machine.RegexParser}.\n
 * This module is kept only for reference/backwards compatibility.
 */
@Deprecated
final class ThompsonBuilder {
    private ThompsonBuilder() {}

    static final class Fragment {
        final MDGNode start;
        final MDGNode accept;

        Fragment(MDGNode start, MDGNode accept) {
            this.start = start;
            this.accept = accept;
        }
    }

    static Fragment build(MultiedgeFinateLogicMachine machine, java.util.List<RegexToken> postfix)
            throws RegexParseException {
        try {
            AtomicInteger counter = new AtomicInteger(0);
            Deque<Fragment> stack = new ArrayDeque<>();

            for (RegexToken t : postfix) {
                switch (t.type) {
                    case LITERAL -> {
                        MDGNode s = newState(counter);
                        MDGNode a = newState(counter);
                        machine.addTransition(s, a, t.literal);
                        stack.push(new Fragment(s, a));
                    }
                    case EPSILON -> {
                        MDGNode s = newState(counter);
                        MDGNode a = newState(counter);
                        machine.addTransition(s, a, null);
                        stack.push(new Fragment(s, a));
                    }
                    case EMPTY -> {
                        MDGNode s = newState(counter);
                        MDGNode a = newState(counter);
                        machine.addState(s);
                        machine.addState(a);
                        // no transition between them
                        stack.push(new Fragment(s, a));
                    }
                    case CONCAT -> {
                        Fragment right = pop(stack, "concat (right)");
                        Fragment left = pop(stack, "concat (left)");
                        machine.addTransition(left.accept, right.start, null);
                        stack.push(new Fragment(left.start, right.accept));
                    }
                    case PLUS -> {
                        Fragment b = pop(stack, "union (right)");
                        Fragment a = pop(stack, "union (left)");
                        MDGNode s = newState(counter);
                        MDGNode f = newState(counter);
                        machine.addTransition(s, a.start, null);
                        machine.addTransition(s, b.start, null);
                        machine.addTransition(a.accept, f, null);
                        machine.addTransition(b.accept, f, null);
                        stack.push(new Fragment(s, f));
                    }
                    case STAR -> {
                        Fragment a = pop(stack, "star");
                        MDGNode s = newState(counter);
                        MDGNode f = newState(counter);
                        machine.addTransition(s, a.start, null);
                        machine.addTransition(s, f, null);
                        machine.addTransition(a.accept, a.start, null);
                        machine.addTransition(a.accept, f, null);
                        stack.push(new Fragment(s, f));
                    }
                    default -> throw new RegexParseException("Unexpected token in postfix: " + t);
                }
            }

            if (stack.size() != 1) {
                throw new RegexParseException("Invalid regex: leftover fragments after build (" + stack.size() + ")");
            }
            return stack.pop();
        } catch (GraphException | InvalidStateException | InvalidSymbolException e) {
            throw new RegexParseException("Failed to build machine from regex: " + e.getMessage(), e);
        }
    }

    private static Fragment pop(Deque<Fragment> stack, String where) throws RegexParseException {
        Fragment f = stack.pollFirst();
        if (f == null) {
            throw new RegexParseException("Invalid regex: missing operand for " + where);
        }
        return f;
    }

    private static MDGNode newState(AtomicInteger counter) throws GraphException {
        try {
            return new MDGNode("q" + counter.getAndIncrement());
        } catch (exceptions.graph.InvalidNodeNameException e) {
            // should never happen with qN
            throw new GraphException("Failed to create state", e);
        }
    }
}

