package regexpToolchain;

public abstract class Transition {

    private State src, dst;

    public Transition(State src, State dst) {
        this.src = src;
        this.dst = dst;
    }

    public State getSrc() {
        return src;
    }

    public void setSrc(State src) {
        this.src = src;
    }

    public State getDst() {
        return dst;
    }

    public void setDst(State dst) {
        this.dst = dst;
    }

    public abstract boolean accept(Character character);

    public abstract String label();
}
