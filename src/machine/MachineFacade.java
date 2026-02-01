package machine;

import exceptions.graph.GraphException;
import exceptions.machine.InvalidAlphabetException;
import exceptions.machine.InvalidStateException;
import exceptions.machine.InvalidSymbolException;
import exceptions.regex.RegexParseException;
import graph.mdg.MDGEdge;
import graph.mdg.MDGNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.Set;

/**
 * Facade for managing multiple machines by unique IDs and performing required operations.
 */
public class MachineFacade {
    private final MachineRegistry registry;

    public MachineFacade(MachineRegistry registry) {
        this.registry = Objects.requireNonNull(registry, "registry");
    }

    public Set<Integer> listIds() {
        return registry.ids();
    }

    public int reg(String regex) throws RegexParseException {
        try {
            MultiedgeFinateLogicMachine m = new RegexParser().parse(regex);
            return registry.register(m);
        } catch (Exception e) {
            throw new RegexParseException("Failed to parse regex: " + e.getMessage(), e);
        }
    }

    public boolean empty(int id) throws InvalidStateException {
        return registry.get(id).isLanguageEmpty();
    }

    public boolean deterministic(int id) throws InvalidStateException, InvalidSymbolException {
        return registry.get(id).isDeterministic();
    }

    public boolean recognize(int id, String word) throws InvalidStateException, InvalidSymbolException {
        return registry.get(id).recognizes(word);
    }

    public int union(int id1, int id2) throws GraphException, InvalidStateException, InvalidSymbolException, InvalidAlphabetException {
        MultiedgeFinateLogicMachine a = registry.get(id1);
        MultiedgeFinateLogicMachine b = registry.get(id2);
        String rx1 = a.getOriginalRegex();
        String rx2 = b.getOriginalRegex();
        if (rx1 == null || rx1.isBlank() || rx2 == null || rx2.isBlank()) {
            throw new IllegalArgumentException("union requires machines created via reg (missing original regex)");
        }
        MultiedgeFinateLogicMachine out = new RegexParser().parse("(" + rx1 + ")+(" + rx2 + ")");
        return registry.register(out);
    }

    public int concat(int id1, int id2) throws GraphException, InvalidStateException, InvalidSymbolException, InvalidAlphabetException {
        MultiedgeFinateLogicMachine a = registry.get(id1);
        MultiedgeFinateLogicMachine b = registry.get(id2);
        String rx1 = a.getOriginalRegex();
        String rx2 = b.getOriginalRegex();
        if (rx1 == null || rx1.isBlank() || rx2 == null || rx2.isBlank()) {
            throw new IllegalArgumentException("concat requires machines created via reg (missing original regex)");
        }
        MultiedgeFinateLogicMachine out = new RegexParser().parse("(" + rx1 + ").(" + rx2 + ")");
        return registry.register(out);
    }

    public int un(int id) throws GraphException, InvalidStateException, InvalidSymbolException, InvalidAlphabetException {
        MultiedgeFinateLogicMachine a = registry.get(id);
        MultiedgeFinateLogicMachine out = new MultiedgeFinateLogicMachine(a.getAlphabet());

        CopyResult ca = copyInto(out, a, "m" + id + "_");

        MDGNode newStart = newNode(out, "p_start_" + id);
        MDGNode newAccept = newNode(out, "p_accept_" + id);
        out.addStartState(newStart);
        out.addAcceptState(newAccept);

        // ε from new start to old starts
        for (MDGNode s : ca.starts) {
            out.addTransition(newStart, s, null);
        }

        // For each old accept: loop back to each old start and also go to new accept
        for (MDGNode f : ca.accepts) {
            for (MDGNode s : ca.starts) {
                out.addTransition(f, s, null);
            }
            out.addTransition(f, newAccept, null);
        }

        return registry.register(out);
    }

    public String formatMachine(int id) throws InvalidStateException {
        MultiedgeFinateLogicMachine m = registry.get(id);

        StringBuilder sb = new StringBuilder();
        sb.append("Machine ").append(id).append(System.lineSeparator());
        sb.append("Sigma = ").append(m.getAlphabet()).append(System.lineSeparator());

        StringJoiner starts = new StringJoiner(", ", "{", "}");
        for (MDGNode s : m.getStartStates()) {
            starts.add(s.getName());
        }
        sb.append("I = ").append(starts).append(System.lineSeparator());

        StringJoiner accepts = new StringJoiner(", ", "{", "}");
        for (MDGNode a : m.getAcceptStates()) {
            accepts.add(a.getName());
        }
        sb.append("A = ").append(accepts).append(System.lineSeparator());

        sb.append("T:").append(System.lineSeparator());
        for (MDGNode from : m.getStates()) {
            for (MDGEdge e : from.getEdges()) {
                String label = e.getLabel() == null ? "eps" : e.getLabel().toString();
                sb.append("  ")
                        .append(from.getName())
                        .append(" --")
                        .append(label)
                        .append("--> ")
                        .append(e.getTo().getName())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    // ---- internal helpers ----

    private static Set<Character> unionAlphabet(MultiedgeFinateLogicMachine a, MultiedgeFinateLogicMachine b) {
        Set<Character> out = new HashSet<>();
        out.addAll(a.getAlphabet());
        out.addAll(b.getAlphabet());
        return out;
    }

    private static final class CopyResult {
        final Map<String, MDGNode> map;
        final Set<MDGNode> starts;
        final Set<MDGNode> accepts;

        CopyResult(Map<String, MDGNode> map, Set<MDGNode> starts, Set<MDGNode> accepts) {
            this.map = map;
            this.starts = starts;
            this.accepts = accepts;
        }
    }

    private static CopyResult copyInto(
            MultiedgeFinateLogicMachine target,
            MultiedgeFinateLogicMachine source,
            String prefix
    ) throws GraphException, InvalidStateException, InvalidSymbolException {
        Map<String, MDGNode> mapping = new HashMap<>();

        // 1) create all states
        List<MDGNode> states = source.getStates();
        for (MDGNode s : states) {
            MDGNode ns = newNode(target, prefix + s.getName());
            mapping.put(s.getName(), ns);
        }

        // 2) copy transitions
        for (MDGNode s : states) {
            MDGNode from = mapping.get(s.getName());
            for (MDGEdge e : s.getEdges()) {
                MDGNode to = mapping.get(e.getTo().getName());
                target.addTransition(from, to, e.getLabel());
            }
        }

        // 3) map start/accept sets
        Set<MDGNode> starts = new HashSet<>();
        for (MDGNode s : source.getStartStates()) {
            starts.add(mapping.get(s.getName()));
        }
        Set<MDGNode> accepts = new HashSet<>();
        for (MDGNode f : source.getAcceptStates()) {
            accepts.add(mapping.get(f.getName()));
        }

        return new CopyResult(mapping, starts, accepts);
    }

    private static MDGNode newNode(MultiedgeFinateLogicMachine m, String name) throws GraphException, InvalidStateException {
        try {
            MDGNode n = new MDGNode(name);
            m.addState(n);
            return n;
        } catch (exceptions.graph.InvalidNodeNameException e) {
            throw new InvalidStateException("Invalid generated state name: " + name);
        }
    }
}

