package fr.epita.assistants.myide.domain.views.git;

import fr.epita.assistants.myide.domain.views.MainFrame;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitPullClass extends JFrame {
    public JPanel messagePanel;
    public JPanel remotePanel;

    public GitPullClass() {
        super("Update the project"); // "to + Project.getRootNode()); try to find how to retrieve git repository name
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(450, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setup() {
        this.setLayout(new BorderLayout());
        this.messagePanel = new JPanel();
        this.remotePanel = new JPanel();

        JLabel message = new JLabel("Do you really want to update the project ?");
        this.messagePanel.add(message);

        Git git;
        try {
            git = Git.open(new File(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString() + "/.git"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GitListFile list_files = new GitListFile();
        List<String> remotesList = new ArrayList<String>();
        try {
            remotesList = list_files.listRemotes(git);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        String[] listRemotes = new String[remotesList.size()];
        for (int i = 0; i < remotesList.size(); i++) {
            listRemotes[i] = remotesList.get(i);
        }
        JComboBox<String> remotes = new JComboBox<String>(listRemotes);


        remotePanel.setLayout(new FlowLayout());
        remotePanel.add(new JLabel("Choose the remote: "));
        remotePanel.add(remotes);

        Repository repository = git.getRepository();

        JPanel addExitPanel = new JPanel();
        addExitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        ///////////////// CREATE BUTTON PUSH AND EXIT /////////

        JButton pullButton = new JButton("Pull");
        pullButton.setSize(80, 30);
        pullButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(pullButton, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(80, 30);
        exitButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(exitButton, BorderLayout.WEST);

        this.add(addExitPanel, BorderLayout.SOUTH);
        this.add(messagePanel, BorderLayout.NORTH);
        this.add(remotePanel, BorderLayout.CENTER);

        exitButton.addActionListener(e -> {
            this.dispose();
        });
        pullButton.addActionListener(this::pullButtonListener);
    }

    public void pullButtonListener(ActionEvent e) {
        this.dispose();
        PasswordClass passwordClass = new PasswordClass(1, "");
        passwordClass.setup();
    }
}
