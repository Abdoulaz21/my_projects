package regexpToolchain;

public class Epsilon extends RegExp {

    @Override
    public String print() {
        return "$";
    }

    @Override
    public Automaton thompson() {
        Automaton automaton = new Automaton();

        State init = automaton.newInitialState();
        State fin = automaton.newFinalState();

        automaton.newEpsilonTransition(init, fin);

        return automaton;
    }
}
