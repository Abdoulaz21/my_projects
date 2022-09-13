package observer;

import java.util.HashSet;
import java.util.Set;

public class Swimmer implements Observable<Swimmer> {

    private final Set<Observer<Swimmer>> swimmers = new HashSet<>();
    private final String name;
    private SwimmerStatus status = SwimmerStatus.OK;

    public Swimmer(String name) {
        this.name = name;
        System.out.println(name + " goes into the sea.");
    }

    public String getName() {
        return name;
    }

    public SwimmerStatus getStatus() {
        return status;
    }

    public void setStatus(SwimmerStatus status) {
        this.status = status;
        if (SwimmerStatus.DROWNING == status)
            System.out.println(name + ": I'm drowning, help!!");
        fire(this);
    }


    @Override
    public Set<Observer<Swimmer>> getObservers() {
        return swimmers;
    }

    @Override
    public void register(Observer<Swimmer>... observers) {
        for (Observer<Swimmer> swimmer : observers) {
            swimmers.add(swimmer);
        }
    }

    @Override
    public void unregister(Observer<Swimmer> observer) {
        swimmers.remove(observer);
    }

    @Override
    public void fire(Swimmer event) {
        for (Observer observer : swimmers) {
            observer.onEvent(event);
        }
    }
}
