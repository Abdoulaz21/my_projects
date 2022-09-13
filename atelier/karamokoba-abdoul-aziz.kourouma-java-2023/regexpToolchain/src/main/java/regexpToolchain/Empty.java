package regexpToolchain;

public class Empty extends RegExp {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Empty;
    }

    @Override
    public String print() {
        return "";
    }

    @Override
    public Automaton thompson() {
        Automaton automaton = new Automaton();
        State init = automaton.newInitialState();
        State fin = automaton.newFinalState();
        return automaton;
    }
}
