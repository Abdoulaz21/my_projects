package regexpToolchain;

public class Concatenation extends RegExp {

    private final RegExp left;
    private final RegExp right;

    public Concatenation(RegExp left, RegExp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return String.format("(%s.%s)", left.print(), right.print());
    }

    @Override
    public Automaton thompson() {
        Automaton A1 = left.thompson();
        Automaton A2 = right.thompson();
        Automaton automaton = new Automaton();
        automaton.combineWith(A1);
        automaton.combineWith(A2);

        State initA2 = A2.getInitialState(),
                finalA1 = A1.getFinalState();

        automaton.newEpsilonTransition(finalA1, initA2);

        initA2.setInitial(false);
        finalA1.setFinal(false);

        return automaton;
    }
}
