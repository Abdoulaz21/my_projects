package observer;

public class Person implements Observable.Observer<Swimmer>{
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public void onEvent(Swimmer event) {
        if (event.getStatus() == SwimmerStatus.WAVING) {
            System.out.println(name + ": Wave at " + event.getName() + ".");
        }
    }
}
