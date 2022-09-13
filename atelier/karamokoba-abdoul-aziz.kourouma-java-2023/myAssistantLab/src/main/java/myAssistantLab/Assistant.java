package myAssistantLab;


public class Assistant {
    protected String login;

    public Assistant(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Assistant x) {
            return this.login.equals(x.login);
        }
        return false;
    }

    /**
     * Prints the Assistant's information as shown in the subject.
     * Adds a new line.
     */
    public void print() {
        System.out.println("login: " + this.login);
    }
}
