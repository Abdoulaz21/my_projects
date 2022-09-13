package myAssistantLab;

public class Intendant extends Assistant {

    public Intendant(String login) {
        super(login);
    }

    @Override
    public void print() {
        System.out.println("login: " + this.login + ", role: intendant");
    }
}
