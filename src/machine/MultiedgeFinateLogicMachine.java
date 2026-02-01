package machine;

import exceptions.graph.GraphException;
import exceptions.machine.InvalidAlphabetException;
import exceptions.machine.InvalidStateException;
import exceptions.machine.InvalidSymbolException;
import graph.mdg.MDGEdge;
import graph.mdg.MDGNode;
import graph.mdg.MultiedgeDirectedGraph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Finite state machine M = (S, Σ, T, I, A)\n
 * - S (states) and T (transitions) are stored in the underlying multiedge directed graph.\n
 * - Σ (alphabet) is provided at construction time and is immutable.\n
 * - I (start states) and A (final/accept states) are subsets of S.\n
 * - ε transitions are represented with label == null.\n
 */
public class MultiedgeFinateLogicMachine {
    private final MultiedgeDirectedGraph graph;
    private final Set<Character> alphabet;
    private final Set<MDGNode> startStates;
    private final Set<MDGNode> acceptStates;
    private String originalRegex;

    public MultiedgeFinateLogicMachine(Set<Character> alphabet) throws InvalidAlphabetException {
        this.graph = new MultiedgeDirectedGraph();
        this.alphabet = Collections.unmodifiableSet(validateAlphabet(alphabet));
        this.startStates = new HashSet<>();
        this.acceptStates = new HashSet<>();
        this.originalRegex = null;
    }

    public MultiedgeFinateLogicMachine(Set<Character> alphabet, MultiedgeDirectedGraph graph) throws InvalidAlphabetException {
        this.graph = graph;
        this.alphabet = Collections.unmodifiableSet(validateAlphabet(alphabet));
        this.startStates = new HashSet<>();
        this.acceptStates = new HashSet<>();
        this.originalRegex = null;
    }

    public String getOriginalRegex() {
        return originalRegex;
    }

    public void setOriginalRegex(String originalRegex) {
        this.originalRegex = originalRegex;
    }

    public static MultiedgeFinateLogicMachine internalMerge(MultiedgeFinateLogicMachine machine1, MultiedgeFinateLogicMachine machine2) throws InvalidAlphabetException, GraphException {
        if (machine1.alphabet.containsAll(machine2.alphabet) && machine2.alphabet.containsAll(machine1.alphabet)){
            MultiedgeDirectedGraph mergedGraph = new MultiedgeDirectedGraph();
            for( MDGNode node : machine1.graph.getNodes()){
                mergedGraph.putNode(node);
            }
            for( MDGNode node : machine2.graph.getNodes()){
                mergedGraph.putNode(node);
            }
            return new MultiedgeFinateLogicMachine(machine1.alphabet, mergedGraph);
        }
        throw new RuntimeException("not internal merge");
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public List<MDGNode> getStates() {
        return graph.getNodes();
    }

    public void addState(MDGNode state) throws GraphException, InvalidStateException {
        ensureState(state);
    }

    public void addStartState(MDGNode state) throws GraphException, InvalidStateException {
        MDGNode canonical = ensureState(state);
        startStates.add(canonical);
    }

    public void removeStartState(MDGNode state) throws InvalidStateException {
        if (state == null) {
            throw new InvalidStateException("Start state cannot be null");
        }
        startStates.remove(requireExistingState(state));
    }

    public Set<MDGNode> getStartStates() throws InvalidStateException {
        // Ensure all stored references are canonical (in case states were added before refactor or via equals-only)
        Set<MDGNode> out = new HashSet<>();
        for (MDGNode s : startStates) {
            out.add(requireExistingState(s));
        }
        return out;
    }

    public void addAcceptState(MDGNode state) throws GraphException, InvalidStateException {
        MDGNode canonical = ensureState(state);
        acceptStates.add(canonical);
    }

    public void removeAcceptState(MDGNode state) throws InvalidStateException {
        if (state == null) {
            throw new InvalidStateException("Accept state cannot be null");
        }
        acceptStates.remove(requireExistingState(state));
    }

    public Set<MDGNode> getAcceptStates() throws InvalidStateException {
        Set<MDGNode> out = new HashSet<>();
        for (MDGNode s : acceptStates) {
            out.add(requireExistingState(s));
        }
        return out;
    }

    public void addTransition(MDGNode from, MDGNode to, Character label)
            throws GraphException, InvalidStateException, InvalidSymbolException {
        MDGNode canonicalFrom = ensureState(from);
        MDGNode canonicalTo = ensureState(to);

        if (label != null) {
            assertSymbolAllowed(label);
        }
        graph.addEdge(canonicalFrom, new MDGEdge(canonicalFrom, canonicalTo, label));
    }

    public boolean hasTransition(MDGNode from, MDGNode to, Character label) throws GraphException, InvalidStateException {
        MDGNode canonicalFrom = requireExistingState(from);
        MDGNode canonicalTo = requireExistingState(to);
        return graph.hasEdge(canonicalFrom, new MDGEdge(canonicalFrom, canonicalTo, label));
    }

    public void removeTransition(MDGNode from, MDGNode to, Character label) throws GraphException, InvalidStateException {
        MDGNode canonicalFrom = requireExistingState(from);
        MDGNode canonicalTo = requireExistingState(to);
        graph.removeEdge(canonicalFrom, new MDGEdge(canonicalFrom, canonicalTo, label));
    }

    /**
     * Replace-cut: removes {@code node1} and replaces it with {@code node2}.\n
     * - {@code node2} may be new/alien; it will be inserted if missing.\n
     * - Incoming edges to {@code node1} are rewired to {@code node2}.\n
     * - Outgoing edges of {@code node1} are inherited by {@code node2}.\n
     * - Start/accept sets are updated accordingly.
     */
    public void cutState(MDGNode node1, MDGNode node2) throws GraphException, InvalidStateException {
        MDGNode canonical1 = requireExistingState(node1);
        graph.cut(canonical1, node2);
        MDGNode canonical2 = requireExistingState(node2);

        if (startStates.remove(canonical1)) {
            startStates.add(canonical2);
        }
        if (acceptStates.remove(canonical1)) {
            acceptStates.add(canonical2);
        }
    }

    public boolean isDeterministic() throws InvalidStateException, InvalidSymbolException {
        // deterministic requires exactly one start state
        if (startStates.size() != 1) {
            return false;
        }

        for (MDGNode s : graph.getNodes()) {
            java.util.HashMap<Character, Integer> seen = new java.util.HashMap<>();
            for (MDGEdge e : s.getEdges()) {
                Character label = e.getLabel();
                if (label == null) {
                    return false; // epsilon transition makes it NFA-ε
                }
                if (!alphabet.contains(label)) {
                    throw new InvalidSymbolException("Transition uses symbol not in Σ: " + label);
                }
                seen.put(label, seen.getOrDefault(label, 0) + 1);
                if (seen.get(label) > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLanguageEmpty() throws InvalidStateException {
        Set<MDGNode> starts = getStartStates();
        if (starts.isEmpty()) {
            return true;
        }
        Set<MDGNode> accepts = getAcceptStates();
        if (accepts.isEmpty()) {
            return true;
        }

        // BFS reachability over all edges (including ε)
        ArrayDeque<MDGNode> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        Set<MDGNode> startClosure = epsilonClosure(starts);
        for (MDGNode s : startClosure) {
            q.add(s);
            visited.add(s.getName());
        }

        while (!q.isEmpty()) {
            MDGNode cur = q.removeFirst();
            if (accepts.contains(cur)) {
                return false;
            }
            for (MDGEdge e : cur.getEdges()) {
                MDGNode nxt = getCanonicalByNameOrNull(e.getTo().getName());
                if (nxt == null) continue;
                if (visited.add(nxt.getName())) {
                    q.add(nxt);
                }
            }
        }
        return true;
    }

    public boolean recognizes(String word) throws InvalidStateException, InvalidSymbolException {
        if (word == null) {
            throw new InvalidStateException("Word cannot be null");
        }

        Set<MDGNode> current = epsilonClosure(getStartStates());
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            assertSymbolAllowed(c);
            current = epsilonClosure(move(current, c));
            if (current.isEmpty()) {
                return false;
            }
        }

        Set<MDGNode> accepts = getAcceptStates();
        for (MDGNode s : current) {
            if (accepts.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private Set<MDGNode> move(Set<MDGNode> states, Character symbol) throws InvalidSymbolException {
        if (symbol == null) {
            throw new InvalidSymbolException("Symbol cannot be null for move()");
        }
        Set<MDGNode> out = new HashSet<>();
        for (MDGNode s : states) {
            for (MDGEdge e : s.getEdges()) {
                if (Objects.equals(e.getLabel(), symbol)) {
                    MDGNode nxt = getCanonicalByNameOrNull(e.getTo().getName());
                    if (nxt != null) {
                        out.add(nxt);
                    }
                }
            }
        }
        return out;
    }

    private Set<MDGNode> epsilonClosure(Set<MDGNode> seeds) {
        ArrayDeque<MDGNode> q = new ArrayDeque<>(seeds);
        Set<String> visited = new HashSet<>();
        Set<MDGNode> out = new HashSet<>();

        for (MDGNode s : seeds) {
            visited.add(s.getName());
            out.add(s);
        }

        while (!q.isEmpty()) {
            MDGNode cur = q.removeFirst();
            for (MDGEdge e : cur.getEdges()) {
                if (e.getLabel() != null) continue;
                MDGNode nxt = getCanonicalByNameOrNull(e.getTo().getName());
                if (nxt == null) continue;
                if (visited.add(nxt.getName())) {
                    out.add(nxt);
                    q.add(nxt);
                }
            }
        }
        return out;
    }

    private MDGNode ensureState(MDGNode state) throws GraphException, InvalidStateException {
        if (state == null) {
            throw new InvalidStateException("State cannot be null");
        }
        // If already exists, return canonical; otherwise insert.
        MDGNode existing = getCanonicalByNameOrNull(state.getName());
        if (existing != null) {
            return existing;
        }
        graph.putNode(state);
        return state;
    }

    private MDGNode requireExistingState(MDGNode state) throws InvalidStateException {
        if (state == null) {
            throw new InvalidStateException("State cannot be null");
        }
        MDGNode existing = getCanonicalByNameOrNull(state.getName());
        if (existing == null) {
            throw new InvalidStateException("State is not part of S: " + state.getName());
        }
        return existing;
    }

    private MDGNode getCanonicalByNameOrNull(String name) {
        if (name == null) return null;
        for (MDGNode n : graph.getNodes()) {
            if (n.getName().equals(name)) {
                return n;
            }
        }
        return null;
    }

    private void assertSymbolAllowed(char c) throws InvalidSymbolException {
        assertSymbolAllowed(Character.valueOf(c));
    }

    private void assertSymbolAllowed(Character c) throws InvalidSymbolException {
        if (c == null) {
            throw new InvalidSymbolException("Symbol cannot be null");
        }
        if (!alphabet.contains(c)) {
            throw new InvalidSymbolException("Symbol not in Σ: " + c);
        }
    }

    private static Set<Character> validateAlphabet(Set<Character> alphabet) throws InvalidAlphabetException {
        if (alphabet == null) {
            throw new InvalidAlphabetException("Alphabet cannot be null");
        }
        Set<Character> out = new HashSet<>();
        for (Character c : alphabet) {
            if (c == null) {
                throw new InvalidAlphabetException("Alphabet cannot contain null symbols");
            }
            if (!isAllowedAlphabetChar(c)) {
                throw new InvalidAlphabetException("Invalid symbol in alphabet (allowed: [a-z0-9]): " + c);
            }
            out.add(c);
        }
        return out;
    }

    private static boolean isAllowedAlphabetChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }
}
