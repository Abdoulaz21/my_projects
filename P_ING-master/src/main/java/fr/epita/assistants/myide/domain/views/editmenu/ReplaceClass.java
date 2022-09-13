package fr.epita.assistants.myide.domain.views.editmenu;

import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.myide.domain.views.texteditor.TextEditorTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ReplaceClass extends JFrame {

    private final MainFrame mainFrame = MainFrame.getInstance();
    public JPanel pathPanel;
    public JPanel textPanel;
    public JTextField searchText;
    public JTextField replaceText;

    public ReplaceClass() {
        super("Replace menu");

        if (this.mainFrame.getTextEditor() != null && this.mainFrame.getTextEditor().getTabCount() != 0) {
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setSize(500, 300);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
    }

    public void setup() {
        if (this.mainFrame.getTextEditor() == null) {
            mainFrame.getStatusBar().setNotificationMessage("No file(s) for replace !");
            return;
        }

        int totalTab = this.mainFrame.getTextEditor().getTabCount();
        if (totalTab == 0) {
            mainFrame.getStatusBar().setNotificationMessage("No file(s) for replace !");
            return;
        }

        this.pathPanel = new JPanel();
        this.textPanel = new JPanel();

        this.searchText = new JTextField();
        this.replaceText = new JTextField();

        textPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 8, 8, 8);

        constraints.gridx = 0;
        constraints.gridy = 0;
        textPanel.add(new JLabel("Search: "), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        textPanel.add(searchText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        textPanel.add(new JLabel("Replace: "), constraints);

        constraints.gridx = 1;
        constraints.gridwidth = 3;
        textPanel.add(replaceText, constraints);

        pathPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JCheckBox allAdd = new JCheckBox("All");
        pathPanel.add(allAdd);

        for (int i = 0; i < totalTab; i++) {
            TextEditorTab currentTab = (TextEditorTab) mainFrame.getTextEditor().getComponentAt(i);
            JCheckBox pathCheckBox = new JCheckBox(currentTab.getFile().toString());
            pathPanel.add(pathCheckBox);
        }

        JPanel addExitPanel = new JPanel();
        addExitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton doneButton = new JButton("Done");
        addExitPanel.add(doneButton, BorderLayout.WEST);

        JButton exitButton = new JButton("Exit");
        addExitPanel.add(exitButton, BorderLayout.WEST);

        this.add(addExitPanel, BorderLayout.SOUTH);
        this.add(textPanel, BorderLayout.CENTER);
        this.add(pathPanel, BorderLayout.NORTH);

        exitButton.addActionListener(event -> this.dispose());
        doneButton.addActionListener(this::doneButtonListener);

        allAdd.addActionListener(this::checkAllBoxes);
    }

    public void checkAllBoxes(ActionEvent e) {
        Component[] components;
        components = this.pathPanel.getComponents();

        boolean isSelected = ((JCheckBox) e.getSource()).isSelected();
        for (Component comp : components) {

            if (comp instanceof JCheckBox) {
                JCheckBox box = (JCheckBox) comp;
                box.setSelected(isSelected);
            }
        }
    }

    public void doneButtonListener(ActionEvent event) {
        Component[] components = this.pathPanel.getComponents();
        List<String> paths = new ArrayList<String>();

        for (Component comp : components) {
            if (comp instanceof JCheckBox) {
                JCheckBox box = (JCheckBox) comp;

                if (box.isSelected())
                    if (!box.getText().equals("All"))
                        paths.add(box.getText());
            }
        }

        String search = searchText.getText();
        String replace = replaceText.getText();

        if (search == null || replace == null)
            return;

        int totalTab = mainFrame.getTextEditor().getTabCount();

        for (int i = 0; i < totalTab; i++) {
            TextEditorTab currentTab = (TextEditorTab) mainFrame.getTextEditor().getComponentAt(i);
            if (paths.contains(currentTab.getFile().toString())) {
                String content = currentTab.getContent().getText();
                String newContent = content.replace(search, replace);

                currentTab.getContent().setText(newContent);
            }
        }

        mainFrame.getStatusBar().setNotificationMessage("Replace apply, replace all occurrences of '" + search + "' by '" + replace + "'.");
        this.dispose();
    }
}
