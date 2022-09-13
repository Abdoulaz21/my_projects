package fr.epita.assistants;

import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.myide.domain.views.MainMenu;
import fr.epita.assistants.myide.domain.views.filemenu.SettingsMenu;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MainClass {
    public static void main(final String[] args) {
        Logger.getRootLogger().setLevel(Level.DEBUG);

        MainFrame mainFrame = MainFrame.getInstance();

        mainFrame.setProjectService(MyIde.init(null));

        mainFrame.setJMenuBar(new MainMenu());
        mainFrame.setupContentPane(null);

        SettingsMenu settingsMenu = new SettingsMenu();
        settingsMenu.setup();
        settingsMenu.applySettings();
        mainFrame.setSettingsMenu(settingsMenu);

        mainFrame.setVisible(true);
    }
}
