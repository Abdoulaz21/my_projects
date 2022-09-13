package myAssistantLab;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Assistant assistant = new Assistant("login_x");
        Moulette moulette = new Moulette("login_y");
        Moulette moulette2 = new Moulette("login_-");
        Intendant intendant = new Intendant("login_z");

        Laboratory laboratory = new Laboratory();
        laboratory.addAssistant(assistant);
        laboratory.addAssistant(intendant);

        ArrayList<Moulette> moulettes = new ArrayList<>();
        moulettes.add(moulette);
        moulettes.add(moulette2);

        laboratory.addAssistants(moulettes);
        laboratory.removeAssistant("login_-");
        laboratory.print();
    }
}
