package myAssistantLab;

public class Moulette extends Assistant {

    public Moulette(String login) {
        super(login);
    }

    @Override
    public void print() {
        System.out.println("login: " + this.login + ", role: moulette");
    }
}
