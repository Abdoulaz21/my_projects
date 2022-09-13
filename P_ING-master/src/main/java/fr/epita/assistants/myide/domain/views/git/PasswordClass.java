package fr.epita.assistants.myide.domain.views.git;

import fr.epita.assistants.myide.domain.entity.ProjectClass;
import fr.epita.assistants.myide.domain.entity.features.git.PullClass;
import fr.epita.assistants.myide.domain.entity.features.git.PushClass;
import fr.epita.assistants.myide.domain.views.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class PasswordClass extends JFrame {
    private final int option;
    private final String remote;
    public JPanel passwordPanel;
    public JPasswordField passwordField;

    public PasswordClass(int option, String remote) {
        super("Enter your password");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(350, 250);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.option = option;
        this.remote = remote;
    }

    public int getOption() {
        return option;
    }

    public String getRemote() {
        return remote;
    }


    public void setup() {
        this.setLayout(new BorderLayout());
        this.passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridBagLayout());
        JLabel titleLabel = new JLabel("Password for the SSH key \"id_rsa\":");
        titleLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.insets = new Insets(8, 8, 8, 8);

        passwordPanel.add(titleLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;

        this.passwordField = new JPasswordField();
        passwordPanel.add(passwordField, constraints);

        this.add(passwordPanel, BorderLayout.CENTER);

        JPanel addExitPanel = new JPanel();
        addExitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("Ok");
        addExitPanel.add(okButton, BorderLayout.CENTER);

        JButton cancelButton = new JButton("Cancel");
        addExitPanel.add(cancelButton, BorderLayout.WEST);

        this.add(addExitPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            this.dispose();
        });
        if (getOption() == 0)
            okButton.addActionListener(this::pushButtonListener);
        else
            okButton.addActionListener(this::pullButtonListener);
    }

    public void pushButtonListener(ActionEvent e) {
        this.dispose();
        PushClass gitPushFiles = PushClass.getInstance();
        String password = new String(passwordField.getPassword());
        gitPushFiles.execute(new ProjectClass(Path.of(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString())), password, getRemote());
    }

    public void pullButtonListener(ActionEvent e) {
        this.dispose();
        PullClass gitPullFiles = PullClass.getInstance();
        String password = new String(passwordField.getPassword());
        gitPullFiles.execute(new ProjectClass(Path.of(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString())), password);
    }
}
