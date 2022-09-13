package memento;

public interface Originator<MEMENTO_T extends Memento<STATE_T>, STATE_T> {

    MEMENTO_T saveState();

    void restoreState(final MEMENTO_T state);
}
