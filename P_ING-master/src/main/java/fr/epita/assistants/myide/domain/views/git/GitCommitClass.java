package fr.epita.assistants.myide.domain.views.git;

import fr.epita.assistants.myide.domain.entity.ProjectClass;
import fr.epita.assistants.myide.domain.entity.features.git.AddClass;
import fr.epita.assistants.myide.domain.entity.features.git.CommitClass;
import fr.epita.assistants.myide.domain.views.MainFrame;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GitCommitClass extends JFrame {
    public JPanel pathPanel;
    public JPanel allModifiedPathPanel;
    public JPanel allAddPathPanel;
    public JPanel textPanel;
    public JPanel addExitPanel;
    public JTextArea textCommit;

    public GitCommitClass() {
        super("Changes not staged for commit");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setup() {
        this.pathPanel = new JPanel();
        this.textPanel = new JPanel();
        this.allAddPathPanel = new JPanel();
        this.allModifiedPathPanel = new JPanel();
        this.textCommit = new JTextArea(10, 80);

        this.setLayout(new GridBagLayout());

        // CREATE MAIN PANEL CONSTRAINTS

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 0.4;
        constraints.gridy = 0;

        // TO GET THE MODIFIED FILES
        Git git;
        try {
            git = Git.open(new File(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString() + "/.git"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Repository repository = git.getRepository();
        RevCommit latestCommit;
        try {
            latestCommit = new Git(repository).log().setMaxCount(1).call().iterator().next();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        String latestCommitHash = latestCommit.getName();
        GitListFile list_files = new GitListFile();
        List<List<String>> paths = new ArrayList<>();
        List<String> remotesList = new ArrayList<String>();
        try {
            paths = list_files.listDiff(repository, git,
                    latestCommitHash);
            remotesList = list_files.listRemotes(git);
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }

        String[] listRemotes = new String[remotesList.size()];
        for (int i = 0; i < remotesList.size(); i++) {
            listRemotes[i] = remotesList.get(i);
        }
        JComboBox<String> remotes = new JComboBox<String>(listRemotes);


        // CREATE THE PATH PANEL AND ALL ASSOCIATED PANELS

        pathPanel.setBackground(new Color(165, 199, 255));
        allModifiedPathPanel.setBackground(new Color(90, 113, 157));
        allAddPathPanel.setBackground(new Color(90, 113, 157));
        pathPanel.setLayout(new GridBagLayout());

        // CREATE PATH PANEL CONSTRAINTS

        GridBagConstraints pathConstraints = new GridBagConstraints();
        pathConstraints.fill = GridBagConstraints.BOTH;
        pathConstraints.weightx = 1;
        pathConstraints.weighty = 0.1;
        pathConstraints.gridy = 0;

        JCheckBox allAdd = new JCheckBox("All unversioned files");
        JCheckBox allModify = new JCheckBox("All modifications");

        allModifiedPathPanel.setLayout(new FlowLayout());
        allAddPathPanel.setLayout(new FlowLayout());
        for (var path : paths.get(0)) {
            System.out.println(path);
            JCheckBox pathCheckBox = new JCheckBox(path);
            allModifiedPathPanel.add(pathCheckBox);
        }
        for (var path : paths.get(1)) {
            System.out.println(path);
            JCheckBox pathCheckBox = new JCheckBox(path);
            allAddPathPanel.add(pathCheckBox);
        }

        pathPanel.add(allModify, pathConstraints);


        pathConstraints.gridx = 0;
        pathConstraints.weighty = 0.4;
        pathConstraints.gridy = 1;

        pathPanel.add(allModifiedPathPanel, pathConstraints);

        pathConstraints.weighty = 0.1;
        pathConstraints.gridy = 2;

        pathPanel.add(allAdd, pathConstraints);

        pathConstraints.weighty = 0.4;
        pathConstraints.gridy = 3;
        pathPanel.add(allAddPathPanel, pathConstraints);

        this.add(pathPanel, constraints);

        constraints.weighty = 0.585;
        constraints.gridy = 1;

        // CREATE THE TEXT LAYOUT

        textPanel.setLayout(new BorderLayout());
        textPanel.add(new JLabel("Commit Message:"), BorderLayout.NORTH);
        //textCommit.setPreferredSize(new Dimension(250,100));
        textCommit.setBounds(400, 400, 250, 150);

        textCommit.setBackground(new Color(238, 234, 228));
        textPanel.add(textCommit, BorderLayout.CENTER);

        this.add(textPanel, constraints);

        ///////////////// CREATE BUTTON SAVE AND EXIT /////////

        this.addExitPanel = new JPanel();
        addExitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        addExitPanel.add(new JLabel("Choose the Remote: "));
        addExitPanel.add(remotes);

        JButton commitButton = new JButton("Commit");
        //commitButton.setSize(80,30);
        commitButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(commitButton);

        JButton commitPushButton = new JButton("Commit and Push...");
        commitButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(commitPushButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(100, 100, 100, 40);
        addExitPanel.add(exitButton);

        constraints.gridy = 2;
        constraints.weighty = 0.015;

        this.add(addExitPanel, constraints);

        // ASSIGN THE LISTENERS TO THE BUTTONS

        exitButton.addActionListener(e -> {
            this.dispose();
        });
        commitButton.addActionListener(this::commitButtonListener);
        commitPushButton.addActionListener(this::commitPushButtonListener);

        // ASSIGN THE LISTENERS TO THE CHECKBOXES

        allAdd.addActionListener(this::checkAllAddBoxes);
        allModify.addActionListener(this::checkAllModifyBoxes);
    }

    // Listeners

    public void checkAllAddBoxes(ActionEvent e) {
        Component[] components;
        components = this.allAddPathPanel.getComponents();

        boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
        for (Component comp : components) {

            if (comp instanceof JCheckBox box) {
                box.setSelected(isSelected);
            }
        }
    }

    public void checkAllModifyBoxes(ActionEvent e) {
        Component[] components;
        components = this.allModifiedPathPanel.getComponents();

        boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
        for (Component comp : components) {

            if (comp instanceof JCheckBox box) {
                box.setSelected(isSelected);
            }
        }
    }

    public void addButtonListener(ActionEvent e) {
        this.dispose();
        Component[] componentsModified, componentsAdded;
        componentsAdded = this.allAddPathPanel.getComponents();
        componentsModified = this.allModifiedPathPanel.getComponents();
        List<String> paths = new ArrayList<String>();

        for (Component comp : componentsAdded) {
            if (comp instanceof JCheckBox box) {
                if (box.isSelected()) {
                    paths.add(box.getText());
                }
            }
        }

        for (Component comp : componentsModified) {
            if (comp instanceof JCheckBox box) {
                if (box.isSelected()) {
                    paths.add(box.getText());
                }
            }
        }

        AddClass gitAddFiles = AddClass.getInstance();
        gitAddFiles.execute(new ProjectClass(Path.of(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString())), paths.toArray(new String[paths.size()]));
    }

    public void commitButtonListener(ActionEvent e) {
        this.dispose();
        this.addButtonListener(e);
        String commitMessage = this.textCommit.getText();
        CommitClass gitCommitFiles = CommitClass.getInstance();
        gitCommitFiles.execute(new ProjectClass(Path.of(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString())), commitMessage);
    }

    public void commitPushButtonListener(ActionEvent e) {
        this.dispose();
        this.commitButtonListener(e);
        Component[] components;
        components = this.addExitPanel.getComponents();
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
