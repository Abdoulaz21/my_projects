package fr.epita.assistants.myide.domain.views.git;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.java.log.Logged;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GitMenu extends JMenu implements Logged {

    public GitMenu() {
        super("Cookie");
        this.setMnemonic('C');

        this.setup();
    }

    private void setup() {
        this.add(this.getAddItem());
        this.add(this.getCommitItem());

        this.addSeparator();

        this.add(this.getPushItem());

        this.addSeparator();

        this.add(this.getPullItem());
    }

    private JMenuItem getAddItem() {
        JMenuItem addItem = new JMenuItem("Add");
        addItem.setAccelerator(MainFrame.getInstance().gitAddShortcut);
        addItem.setIcon(FontIcon.of(BootstrapIcons.FOLDER_PLUS, 20, new Color(106, 153, 78)));
        addItem.addActionListener(this::gitAddListener);

        return addItem;
    }

    private void gitAddListener(ActionEvent event) {
        GitAddClass addFrame = new GitAddClass();
        addFrame.setup();
    }


    private JMenuItem getCommitItem() {
        JMenuItem commitItem = new JMenuItem("Commit");
        commitItem.setAccelerator(MainFrame.getInstance().gitCommitShortcut);
        commitItem.setIcon(FontIcon.of(BootstrapIcons.CHECK, 20, new Color(106, 153, 78)));
        commitItem.addActionListener(this::gitCommitListener);

        return commitItem;
    }

    private void gitCommitListener(ActionEvent event) {
        GitCommitClass commitFrame = new GitCommitClass();
        commitFrame.setup();
    }

    private JMenuItem getPushItem() {
        JMenuItem pushItem = new JMenuItem("Push");
        pushItem.setAccelerator(MainFrame.getInstance().gitPushShortcut);
        pushItem.setIcon(FontIcon.of(BootstrapIcons.ARROW_UP_RIGHT, 20, new Color(106, 153, 78)));
        pushItem.addActionListener(this::gitPushListener);

        return pushItem;
    }

    private void gitPushListener(ActionEvent event) {
        GitPushClass gitPushFiles = new GitPushClass();
        gitPushFiles.setup();
    }

    private JMenuItem getPullItem() {
        JMenuItem pullItem = new JMenuItem("Pull");
        pullItem.setAccelerator(MainFrame.getInstance().gitPullShortcut);
        pullItem.setIcon(FontIcon.of(BootstrapIcons.ARROW_DOWN_CIRCLE, 20, new Color(54, 152, 187)));
        pullItem.addActionListener(this::gitPullListener);

        return pullItem;
    }

    private void gitPullListener(ActionEvent event) {
        GitPullClass gitPullFiles = new GitPullClass();
        gitPullFiles.setup();
    }

}
