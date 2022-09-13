package fr.epita.assistants.myide.domain.views.editmenu;

import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.utils.IconColor;
import fr.epita.java.log.Logged;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class EditMenu extends JMenu implements Logged {

    final MainFrame mainFrame = MainFrame.getInstance();

    public EditMenu() {
        super("Edit");
        this.setMnemonic('E');


        this.setup();
    }

    private void setup() {
        this.add(this.getSearchMenu());
        this.add(this.getReplaceMenu());
    }

    private JMenuItem getSearchMenu() {
        JMenuItem searchMenu = new JMenuItem("Search");
        searchMenu.setIcon(FontIcon.of(BootstrapIcons.SEARCH, 16, IconColor.getIconColor()));
        searchMenu.setMnemonic('S');
        searchMenu.setAccelerator(MainFrame.getInstance().searchShortcut);

        searchMenu.addActionListener(this::searchField);

        return searchMenu;
    }

    private void searchField(ActionEvent event) {
        SearchClass searchClass = new SearchClass();
        searchClass.setup();
    }

    private JMenuItem getReplaceMenu() {
        JMenuItem replaceMenu = new JMenuItem("Replace");
        replaceMenu.setIcon(FontIcon.of(BootstrapIcons.EYEGLASSES, 16, IconColor.getIconColor()));
        replaceMenu.setMnemonic('R');
        replaceMenu.setAccelerator(MainFrame.getInstance().replaceShortcut);

        replaceMenu.addActionListener(this::replaceField);

        return replaceMenu;
    }

    private void replaceField(ActionEvent event) {
        ReplaceClass replaceClass = new ReplaceClass();
        replaceClass.setup();
    }
}
