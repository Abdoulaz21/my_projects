package regexpToolchain;

public class Kleene extends RegExp {

    private final RegExp regExp;

    public Kleene(RegExp regExp) {
        this.regExp = regExp;
    }

    @Override
    public String print() {
        return String.format("%s*", regExp.print());
    }

    @Override
    public Automaton thompson() {
        Automaton A1 = regExp.thompson();
        Automaton automaton = new Automaton();
        automaton.combineWith(A1);

        State initA1 = A1.getInitialState();
        State finalA1 = A1.getFinalState();
        State init = automaton.newInitialState();
        State fina = automaton.newFinalState();

        automaton.newEpsilonTransition(init, fina);
        automaton.newEpsilonTransition(init, initA1);
        automaton.newEpsilonTransition(finalA1, fina);
        automaton.newEpsilonTransition(finalA1, initA1);

        initA1.setInitial(false);
        finalA1.setFinal(false);

        return automaton;
    }
}
