package memento;

public interface Caretaker<MEMENTO_T extends Memento<?>> {

    void storeState(final MEMENTO_T memento);
}
