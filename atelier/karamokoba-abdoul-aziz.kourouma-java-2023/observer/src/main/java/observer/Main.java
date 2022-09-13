package observer;

public class Main {

    public static void main(String[] args) {

        final var swimmer = new Swimmer("Swimmer");
        final var lifeguard = new Lifeguard("Lifeguard");

        swimmer.register(lifeguard);

        swimmer.setStatus(SwimmerStatus.DROWNING);
    }
}
