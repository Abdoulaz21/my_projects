package fr.epita.assistants.myide.domain.views.git;

import fr.epita.assistants.myide.domain.views.MainFrame;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.kordamp.ikonli.simpleicons.SimpleIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitPushClass extends JFrame {
    public JPanel commitPanel;
    public JPanel branchPanel;

    public GitPushClass() {
        super("Push Commits"); // "to + Project.getRootNode()); try to find how to retrieve git repository name
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setup() {
        this.commitPanel = new JPanel();
        this.branchPanel = new JPanel();

        Git git;
        try {
            git = Git.open(new File(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString() + "/.git"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Repository repository = git.getRepository();
        branchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

        JLabel gitIcon = new JLabel();
        gitIcon.setIcon(FontIcon.of(SimpleIcons.GIT, 20, new Color(106, 153, 78)));
        branchPanel.add(gitIcon);
        JLabel actualBranchTitle = new JLabel("Actual Branch: ");
        branchPanel.add(actualBranchTitle);

        try {
            JLabel branchLabelShort = new JLabel(repository.getBranch() + " ");
            branchPanel.add(branchLabelShort);
            JLabel branchLabelLong = new JLabel(repository.getFullBranch());
            branchPanel.add(branchLabelLong);
        } catch (IOException e) {
            e.printStackTrace();
        }

        commitPanel.setLayout(new BorderLayout());
        JLabel branchLabel = new JLabel("Commits:");
        commitPanel.add(branchLabel, BorderLayout.NORTH);

        GitListFile list_files = new GitListFile();

        List<String> unpushedCommits = new ArrayList<String>();
        List<String> remotesList = new ArrayList<String>();
        try {
            unpushedCommits = list_files.getUnpushedCommits(repository, git);
            remotesList = list_files.listRemotes(git);
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }

        String[] listRemotes = new String[remotesList.size()];
        for (int i = 0; i < remotesList.size(); i++) {
            listRemotes[i] = remotesList.get(i);
        }
        JComboBox<String> remotes = new JComboBox<String>(listRemotes);
        branchPanel.add(remotes, FlowLayout.TRAILING);


        DefaultListModel listCommits = new DefaultListModel();
        for (var commit : unpushedCommits) {
            listCommits.addElement(" - " + commit);
        }
        JList commitList = new JList(listCommits);
        commitPanel.add(commitList, BorderLayout.CENTER);

        JPanel addExitPanel = new JPanel();
        addExitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        ///////////////// CREATE BUTTON PUSH AND EXIT /////////

        JButton pushButton = new JButton("Push");
        pushButton.setSize(80, 30);
        pushButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(pushButton, BorderLayout.CENTER);

        JButton exitButton = new JButton("Exit");
        exitButton.setSize(80, 30);
        exitButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(exitButton, BorderLayout.WEST);

        this.add(addExitPanel, BorderLayout.SOUTH);
        this.add(commitPanel, BorderLayout.CENTER);
        this.add(branchPanel, BorderLayout.NORTH);

        exitButton.addActionListener(e -> {
            this.dispose();
        });
        pushButton.addActionListener(this::pushButtonListener);
    }

    public void pushButtonListener(ActionEvent e) {
        this.dispose();
        Component[] components;
        components = this.branchPanel.getComponents();
        String remote = null;
        JLabel remoteLabel = new JLabel("origin");

        for (Component comp : components) {

            if (comp instanceof JComboBox box) {
                remoteLabel.setText("" + box.getSelectedItem());
            }
        }
        remote = remoteLabel.getText();
        PasswordClass passwordClass = new PasswordClass(0, remote);
        passwordClass.setup();
    }

}
