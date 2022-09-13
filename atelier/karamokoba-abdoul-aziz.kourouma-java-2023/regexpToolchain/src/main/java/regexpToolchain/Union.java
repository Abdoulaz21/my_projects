package regexpToolchain;

public class Union extends RegExp {

    private final RegExp left;
    private final RegExp right;

    public Union(RegExp left, RegExp right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return String.format("(%s|%s)", left.print(), right.print());
    }

    @Override
    public Automaton thompson() {
        Automaton A1 = left.thompson(), // build the automation of the left-hand side operand
                A2 = right.thompson(), // and the right
                automaton = new Automaton(); // creation of the Automaton to be returned
        automaton.combineWith(A1); // self explanatory
        automaton.combineWith(A2); // ditto

        /* Take a look at how the automaton should look like (figure 4.18 THLR).
        Basically,
        - we create a new initial state Q0 and a new final state QF
        - we connect Q0 to the initial state of each sub-automaton (using an e-transition)
        - we connect the final state of each sub-automaton to QF (using an e-transition)
        - we mark the initial and final states of each sub-automaton as non-initial and non-final
          (it is important so that the new automaton only has one initial and one final state). */

        State initA1 = A1.getInitialState(),
                initA2 = A2.getInitialState(),
                finalA1 = A1.getFinalState(),
                finalA2 = A2.getFinalState(),
                init = automaton.newInitialState(),
                fin = automaton.newFinalState();

        automaton.newEpsilonTransition(init, initA1);
        automaton.newEpsilonTransition(init, initA2);
        automaton.newEpsilonTransition(finalA1, fin);
        automaton.newEpsilonTransition(finalA2, fin);

        initA1.setInitial(false);
        initA2.setInitial(false);
        finalA1.setFinal(false);
        finalA2.setFinal(false);

        return automaton;
    }
}
