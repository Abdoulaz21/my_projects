package regexpToolchain;

public class EpsilonTransition extends Transition {

    public EpsilonTransition(State src, State dst) {
        super(src, dst);
    }

    @Override
    public boolean accept(Character character) {
        return true;
    }

    @Override
    public String label() {
        return "$";
    }
}
