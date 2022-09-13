package regexpToolchain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represent an automaton, with states and transitions.
 */
public class Automaton {

    private final Set<State> states;
    private final Set<Transition> transitions;

    {
        states = new HashSet<>();
        transitions = new HashSet<>();
    }

    /**
     * Create a state and add it to the set of states of this automaton.
     *
     * @return the created state
     * @see State
     */
    public State newState() {
        State state = new State();
        states.add(state);
        return state;
    }

    /**
     * Same as {@link #newState()} but also mark the state as being initial.
     *
     * @return the state
     * @see State#isInitial()
     */
    public State newInitialState() {
        State state = newState();
        state.setInitial(true);
        return state;
    }

    /**
     * Same as {@link #newState()} but also mark the state as being final.
     *
     * @return the state
     * @see State#isFinal()
     */
    public State newFinalState() {
        State state = newState();
        state.setFinal(true);
        return state;
    }

    private void addTransition(Transition transition) {
        if (states.contains(transition.getSrc())
                && states.contains(transition.getDst()))
            transitions.add(transition);
        else
            throw new RuntimeException("transition contains unknown states");
    }

    /**
     * Add a transition from state src to state dst with the label character.
     *
     * @param label the label of the transition
     * @param src   the source state of the transition
     * @param dst   the destination state of the transition
     * @return the transition created
     * @throws RuntimeException if either src or dst do not belong to the automaton (i.e.: created using {@link #newState()}).
     */
    public Transition newTransition(final Character label, State src, State dst) {
        Transition transition = new TokenTransition(label, src, dst);
        addTransition(transition);
        return transition;
    }

    /**
     * Add an epsilon-transition from state src to state dst.
     *
     * @param src the source state of the transition
     * @param dst the destination state of the transition
     * @return the transition created
     * @throws RuntimeException if either src or dst do not belong to the automaton (i.e.: created using {@link #newState()}).
     */
    public Transition newEpsilonTransition(State src, State dst) {
        Transition transition = new EpsilonTransition(src, dst);
        addTransition(transition);
        return transition;
    }

    /**
     * Add to this automaton the states and the transitions of another automaton.
     *
     * @param automaton the automaton to embed
     */
    public void combineWith(Automaton automaton) {
        states.addAll(automaton.states);
        transitions.addAll(automaton.transitions);
    }

    /**
     * @return the set of states that belong to this automaton
     */
    public Set<State> getStates() {
        return states;
    }

    /**
     * @return the set of transitions inside this automaton
     */
    public Set<Transition> getTransitions() {
        return transitions;
    }

    /**
     * @return the initial states of this automaton
     */
    public Set<State> getInitialStates() {
        return states.stream()
                .filter(State::isInitial)
                .collect(Collectors.toSet());
    }

    /**
     * @return the final states of this automaton
     */
    public Set<State> getFinalStates() {
        return states.stream()
                .filter(State::isFinal)
                .collect(Collectors.toSet());
    }

    /**
     * Return the initial state of this automaton.
     * Fail if this automaton does not contain any initial state or if it contains several ones.
     *
     * @return the initial state
     * @throws RuntimeException when {@code getInitialStates().size() != 1}
     */
    public State getInitialState() {
        Set<State> ss = getInitialStates();
        if (ss.size() == 0)
            throw new RuntimeException("Automaton::getInitialState: no initial state found");
        if (ss.size() > 1)
            throw new RuntimeException("Automaton::getInitialState: multiple initial states found");
        return ss.stream().findFirst().get();
    }

    /**
     * Return the final state of this automaton.
     * Fail if this automaton does not contain any final state or if it contains several ones.
     *
     * @return the final state
     * @throws RuntimeException when {@code getFinalStates().size() != 1}
     */
    public State getFinalState() {
        Set<State> ss = getFinalStates();
        if (ss.size() == 0)
            throw new RuntimeException("Automaton::getFinalState: no final state found");
        if (ss.size() > 1)
            throw new RuntimeException("Automaton::getFinalState: multiple final states found");
        return ss.stream().findFirst().get();
    }

    /**
     * Return whether this automaton accepts the word.
     *
     * @param word a word
     * @return whether word is accepted or not
     */
    public boolean acceptsWord(String word) {
        for (State s : getInitialStates())
            if (acceptsFromState(word, s, new HashSet<>()))
                return true;
        return false;
    }

    private boolean acceptsFromState(String word, State state, Set<State> visited) {
        visited.add(state);
        // collect outgoing transitions of `state'
        List<Transition> ts = outgoing(state).collect(Collectors.toList());

        // take every e-transition available
        if (ts.stream()
                .filter(t -> t instanceof EpsilonTransition && !visited.contains(t.getDst()))
                .anyMatch(t -> acceptsFromState(word, t.getDst(), visited)))
            return true;

        // all e-transitions have been taken
        if (word.equals(""))
            return state.isFinal();

        // let's inspect non e-transitions
        char c = word.charAt(0);
        String cs = word.substring(1);
        return ts.stream()
                .filter(t -> !(t instanceof EpsilonTransition))
                .filter(t -> t.accept(c))
                .anyMatch(t -> acceptsFromState(cs, t.getDst(), new HashSet<>()));
    }

    private Stream<Transition> outgoing(State q) {
        return transitions.stream().filter(t -> t.getSrc().equals(q));
    }
}
