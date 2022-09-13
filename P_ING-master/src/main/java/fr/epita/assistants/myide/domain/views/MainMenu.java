package fr.epita.assistants.myide.domain.views;

import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.views.editmenu.EditMenu;
import fr.epita.assistants.myide.domain.views.filemenu.FileMenu;
import fr.epita.assistants.myide.domain.views.git.GitCommitClass;
import fr.epita.assistants.myide.domain.views.git.GitMenu;
import fr.epita.assistants.myide.domain.views.git.GitPullClass;
import fr.epita.assistants.myide.domain.views.git.GitPushClass;
import fr.epita.assistants.myide.domain.views.maven.RuntimeCompilation;
import fr.epita.assistants.myide.domain.views.maven.MavenMenu;
import fr.epita.java.log.Logged;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainMenu extends JMenuBar implements Logged {
    private final FileMenu fileMenu;
    private final EditMenu editMenu;
    private final GitMenu gitMenu;
    private final MavenMenu mavenMenu;

    private JTextField packageName = null;
    private RuntimeCompilation compileProject;

    public MainMenu() throws HeadlessException {
        this.fileMenu = new FileMenu();
        this.editMenu = new EditMenu();
        this.gitMenu = new GitMenu();
        this.mavenMenu = new MavenMenu();

        this.add(this.fileMenu);
        this.add(this.editMenu);
        this.add(this.gitMenu);
        this.add(this.mavenMenu);

        this.gitMenu.setEnabled(false);
        this.mavenMenu.setEnabled(false);

        boolean isGit = false;
        boolean isMaven = false;
        if (MainFrame.getInstance().isProjectLoad()) {
            Project project = MainFrame.getInstance().getLoadedProject();
            for (var aspect : project.getAspects()) {
                if (aspect.getType().equals(Mandatory.Aspects.MAVEN))
                    isMaven = true;
                if (aspect.getType().equals(Mandatory.Aspects.GIT))
                    isGit = true;
            }
        }

        this.add(Box.createGlue());

        if (isMaven) {
            this.mavenMenu.setEnabled(true);
            this.setupMavenCompilationButtons();
        }

        if (isGit) {
            this.gitMenu.setEnabled(true);
            this.setupGitButtons();
        }
    }

    public void setupGitButtons() {
        JButton gitPullButton = new JButton();
        gitPullButton.setIcon(FontIcon.of(BootstrapIcons.ARROW_DOWN_LEFT, 20, new Color(54, 152, 187)));
        gitPullButton.addActionListener(this::gitPullListener);
        gitPullButton.setToolTipText("Pull...");
        this.add(gitPullButton);

        JButton gitCommitButton = new JButton();
        gitCommitButton.setIcon(FontIcon.of(BootstrapIcons.CHECK, 20, new Color(106, 153, 78)));
        gitCommitButton.addActionListener(this::gitCommitListener);
        gitCommitButton.setToolTipText("Commit...");
        this.add(gitCommitButton);

        JButton gitPushButton = new JButton();
        gitPushButton.setIcon(FontIcon.of(BootstrapIcons.ARROW_UP_RIGHT, 20, new Color(106, 153, 78)));
        gitPushButton.addActionListener(this::gitPushListener);
        gitPushButton.setToolTipText("Push...");
        this.add(gitPushButton);
    }

    public void setupMavenCompilationButtons() {
        JButton compileCodeButton = new JButton();
        compileCodeButton.setIcon(FontIcon.of(BootstrapIcons.CARET_RIGHT_FILL, 20, new Color(106, 153, 78)));
        compileCodeButton.addActionListener(this::compileListener);
        compileCodeButton.setToolTipText("Run...");
        this.add(compileCodeButton);

        JButton stopButton = new JButton();
        stopButton.setIcon(FontIcon.of(BootstrapIcons.SQUARE_FILL, 20, new Color(255, 0, 0)));
        stopButton.addActionListener(this::stopCodeListener);
        stopButton.setToolTipText("Stop...");
        this.add(stopButton);

        packageName = new JTextField(10);
        packageName.setToolTipText("Package of main()");
        this.add(packageName);
    }

    private void gitPullListener(ActionEvent event) {
        GitPullClass gitPullFiles = new GitPullClass();
        gitPullFiles.setup();
    }

    private void gitPushListener(ActionEvent event) {
        GitPushClass gitPushFiles = new GitPushClass();
        gitPushFiles.setup();
    }

    private void gitCommitListener(ActionEvent event) {
        GitCommitClass commitFrame = new GitCommitClass();
        commitFrame.setup();
    }

    private void compileListener(ActionEvent event) {
        this.compileProject = new RuntimeCompilation();
        this.compileProject.launch(this.packageName.getText());
    }

    private void stopCodeListener(ActionEvent event) {
        try {
            this.compileProject.killProcess();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
