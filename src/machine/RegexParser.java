package machine;

import exceptions.graph.GraphException;
import exceptions.machine.InvalidAlphabetException;
import exceptions.machine.InvalidStateException;
import exceptions.machine.InvalidSymbolException;
import graph.mdg.MDGNode;

import java.util.HashSet;
import java.util.Set;

public class RegexParser {
    private int nc = 0;
    private Set<Character> alphabet = new HashSet<>();

    /**
     * Parse the given regex into a {@link MultiedgeFinateLogicMachine}.\n
     * Alphabet is derived from the regex literals ([a-z0-9]).\n
     * Supported operators: '+', '.', '*', '(', ')'.\n
     * Whitespace is ignored.
     */
    public MultiedgeFinateLogicMachine parse(String regex)
            throws GraphException, InvalidStateException, InvalidAlphabetException, InvalidSymbolException {
        if (regex == null) {
            throw new InvalidStateException("Regex cannot be null");
        }
        String input = stripWhitespace(regex);
        this.alphabet = deriveAlphabet(input);
        this.nc = 0;
        MultiedgeFinateLogicMachine m = expression(input);
        m.setOriginalRegex(input);
        return m;
    }

    private static String stripWhitespace(String s) {
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isWhitespace(c)) {
                out.append(c);
            }
        }
        return out.toString();
    }

    private static Set<Character> deriveAlphabet(String regex) throws InvalidAlphabetException {
        Set<Character> out = new HashSet<>();
        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);
            if (c == '+' || c == '.' || c == '*' || c == '(' || c == ')') {
                continue;
            }
            if (Character.isWhitespace(c)) {
                continue;
            }
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                out.add(c);
                continue;
            }
            throw new InvalidAlphabetException("Invalid symbol in regex/alphabet (allowed: [a-z0-9]): " + c);
        }
        return out;
    }
//    expression → term ( '|' term )*          // Lowest precedence: union/alternation
    private MultiedgeFinateLogicMachine expression(String input) throws GraphException, InvalidStateException, InvalidAlphabetException, InvalidSymbolException {
        MDGNode newStart;
        MDGNode newAccept;
        nc++;
        for(int i=0;i<input.length();i++){
            Character c = input.charAt(i);
            if (c=='(')
                for(int j = i; j<input.length();j++)
                    if(input.charAt(j)==')') {
                        i = j;
                        break;
                    }
//                    else throw new IllegalArgumentException("Unmatched parentheses");

            c = input.charAt(i);
            if(c=='+'){
                var term = term(input.substring(0,i));
                var expression = expression(input.substring(i+1));
                newStart = expression.getStartStates().iterator().next();
                term.getStartStates().forEach(
                        s-> {
                            try {
                                term.cutState(s,newStart);
                            } catch (GraphException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidStateException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                newAccept = expression.getAcceptStates().iterator().next();
                term.getAcceptStates().forEach(
                        f-> {
                            try {
                                term.cutState(f,newAccept);
                            } catch (GraphException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidStateException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );



                
                MultiedgeFinateLogicMachine unitedMachine = MultiedgeFinateLogicMachine.internalMerge(term,expression);
                unitedMachine.addStartState(newStart);
                unitedMachine.addAcceptState(newAccept);
                return unitedMachine;
                //+
            }
        }
        return term(input);
    }

    //    term      → factor ( factor )*           // Concatenation (implicit ·)
    private MultiedgeFinateLogicMachine term(String input) throws GraphException, InvalidStateException, InvalidAlphabetException, InvalidSymbolException {
        MDGNode newStart;
        MDGNode oldAccept;
        for(int i=0;i<input.length();i++){
            Character c = input.charAt(i);
            if (c=='(')
                for(int j = i; j<input.length();j++)
                    if(input.charAt(j)==')'){
                        i = j;
                        break;
                    }
//                    else throw new IllegalArgumentException("Unmatched parentheses");

            c = input.charAt(i);
            if(c=='.'){
                var factor = factor(input.substring(0,i));
                oldAccept = factor.getAcceptStates().iterator().next();
                factor.getAcceptStates().forEach(
                        f-> {
                            try {
                                factor.cutState(f,oldAccept);
                            } catch (GraphException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidStateException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                var term = term(input.substring(i+1));
                newStart = term.getStartStates().iterator().next();
                term.getStartStates().forEach(
                        s-> {
                            try {
                                term.cutState(s,newStart);
                            } catch (GraphException e) {
                                throw new RuntimeException(e);
                            } catch (InvalidStateException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

                factor.cutState(oldAccept,newStart);

                MultiedgeFinateLogicMachine concatinatedMachine = MultiedgeFinateLogicMachine.internalMerge(factor,term);
                factor.getStartStates().forEach(
                        s->
                        {
                            try {
                                concatinatedMachine.addStartState(s);
                            } catch (InvalidStateException e) {
                                throw new RuntimeException(e);
                            } catch (GraphException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                term.getAcceptStates().forEach(
                        f-> {
                            try {
                                concatinatedMachine.addAcceptState(f);
                            } catch (InvalidStateException e) {
                                throw new RuntimeException(e);
                            } catch (GraphException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                return concatinatedMachine;
            }
        }
        return factor(input);
    }

    //    factor→ literal | '(' expression ')' | factor '*' | factor '+' | factor '?'
    private MultiedgeFinateLogicMachine factor(String input) throws GraphException, InvalidAlphabetException, InvalidStateException, InvalidSymbolException {
        if (input.length()>1) {
            for(int i = input.length()-1; i >= 0; i--){
                int next = i-1;
                Character c = input.charAt(i);
                switch (c){
                    case '*':
                        MDGNode node;
                        nc++;
//                        if (input.charAt(next)==')') {
//                            node = expression(input.substring(0,next));
//                        }
//                        else {
//                            node = factor(input.substring(0,next));
//                        }
                        var loop = factor(input.substring(0,i));
                        node = loop.getStartStates().iterator().next();
                        loop.getStartStates().forEach(
                                s-> {
                                    try {
                                        loop.cutState(s,node);
                                    } catch (GraphException e) {
                                        throw new RuntimeException(e);
                                    } catch (InvalidStateException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );
                        loop.getAcceptStates().forEach(
                                f-> {
                                    try {
                                        loop.cutState(f,node);
                                    } catch (GraphException e) {
                                        throw new RuntimeException(e);
                                    } catch (InvalidStateException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );
                        loop.addStartState(node);
                        loop.addAcceptState(node);
                        return loop;
                    case ')':
                        int depth = 1;
                        for (int j = i - 1; j >= 0; j--) {
                            char ch = input.charAt(j);
                            if (ch == ')') depth++;
                            if (ch == '(') depth--;
                            if (depth == 0) {
                                return expression(input.substring(j + 1, i));
                            }
                        }
                        throw new InvalidStateException("Unmatched ')': " + input);
                }
            }
        }
        return literal(input);
    }

    private MultiedgeFinateLogicMachine literal(String input) throws GraphException, InvalidAlphabetException, InvalidStateException, InvalidSymbolException {
        nc++;
        //new MDGNode("q"+(nc-1))
        //System.out.println("s->"+input);
        Character l = input.charAt(0);
        MultiedgeFinateLogicMachine m = new MultiedgeFinateLogicMachine(alphabet);
        var s = new MDGNode("q"+nc);
        m.addState(s);
        nc++;
        var f = new MDGNode("q"+nc);
        m.addState(f);
        m.addTransition(s,f,l);
        m.addAcceptState(f);
        m.addStartState(s);
        return m;
    }

//    private static void sendImpulse(MDGNode currentState, Set<MDGNode> visited, Set<MDGNode> acceptStates) {
//        if (visited.contains(currentState)) {
//            return;
//        }
//        visited.add(currentState);
//
//        System.out.println("State: " + currentState.getName());
//        for (MDGEdge edge : currentState.getEdges()) {
//            System.out.println( currentState.getName() + "  -> Sending impulse via edge labeled '" + edge.getLabel() + "'");
//            sendImpulse(edge.getTo(), visited, acceptStates);
//        }
//        if (acceptStates.contains(currentState)) {
//            System.out.println("  -> End state reached");
//            return;
//        }
//
//
//    }




//expression: (a+b)*c
//↓ splits on |
//    term: (a+b)*c
//  ↓   implicit concat: [(a+b)*] [c]
//          factor: (a+b)*     factor: c
//              factor: (a+b)         literal: c
//                  expression: a+b
//                  ↓   split union: [a] [b]
//                      term: a         term: b
//                          factor: a       factor: b
    //                          literal: a      literal: b
}