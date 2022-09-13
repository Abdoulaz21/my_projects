package observer;

public class Lifeguard implements Observable.Observer<Swimmer> {
    private final String name;

    public Lifeguard(String name) {
        this.name = name;
        System.out.println(name + " begins to keep an eye on the swimmers.");
    }

    @Override
    public void onEvent(Swimmer event) {
        if (event.getStatus() == SwimmerStatus.DROWNING) {
            System.out.println(name + ": I will save you " + event.getName() + "!");
        } else if (event.getStatus() == SwimmerStatus.TOO_FAR) {
            System.out.println(name + ": " + event.getName() + "! You are too far, come back!");
        }
    }
}
