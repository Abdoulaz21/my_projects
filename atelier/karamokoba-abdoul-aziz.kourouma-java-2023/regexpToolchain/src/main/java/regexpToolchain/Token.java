package regexpToolchain;

public class Token extends RegExp {

    private final Character character;

    public Token(Character character) {
        this.character = character;
    }

    @Override
    public String print() {
        return String.format("%c", character);
    }

    @Override
    public Automaton thompson() {
        Automaton automaton = new Automaton();

        State init = automaton.newInitialState();
        State fin = automaton.newFinalState();

        automaton.newTransition(character, init, fin);

        return automaton;
    }
}
