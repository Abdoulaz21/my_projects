package fr.epita.assistants.myide.domain.views.editmenu;

import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.myide.domain.views.texteditor.TextEditorTab;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchClass extends JFrame {

    private final MainFrame mainFrame = MainFrame.getInstance();
    public JPanel pathPanel;
    public JPanel textPanel;
    public JTextField searchText;
    public JSpinner redText;
    public JSpinner greenText;
    public JSpinner blueText;

    public SearchClass() {
        super("Search menu");

        if (this.mainFrame.getTextEditor() != null && this.mainFrame.getTextEditor().getTabCount() != 0) {
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setSize(500, 300);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
    }

    public void setup() {
        if (this.mainFrame.getTextEditor() == null) {
            mainFrame.getStatusBar().setNotificationMessage("No file(s) for searching !");
            return;
        }

        int totalTab = this.mainFrame.getTextEditor().getTabCount();
        if (totalTab == 0) {
            mainFrame.getStatusBar().setNotificationMessage("No file(s) for searching !");
            return;
        }

        this.pathPanel = new JPanel();
        this.textPanel = new JPanel();

        this.searchText = new JTextField();

        this.redText = new JSpinner(new SpinnerNumberModel(102, 1, 255, 1));
        this.greenText = new JSpinner(new SpinnerNumberModel(217, 1, 255, 1));
        this.blueText = new JSpinner(new SpinnerNumberModel(239, 1, 255, 1));

        textPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 8, 8, 8);

        constraints.gridx = 0;
        constraints.gridy = 0;
        textPanel.add(new JLabel("Search: "), constraints);

        constraints.gridx = 1;
        textPanel.add(searchText, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        textPanel.add(new JLabel("Highlight Color:"), constraints);

        constraints.gridx = 1;
        textPanel.add(redText, constraints);

        constraints.gridx = 2;
        textPanel.add(greenText, constraints);

        constraints.gridx = 3;
        textPanel.add(blueText, constraints);

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

        redText.setBackground(new Color(246, 73, 73));
        redText.setForeground(new Color(0, 0, 0));

        greenText.setBackground(new Color(77, 252, 77));
        greenText.setForeground(new Color(0, 0, 0));

        blueText.setBackground(new Color(91, 91, 252));
        blueText.setForeground(new Color(0, 0, 0));

        this.add(addExitPanel, BorderLayout.SOUTH);
        this.getContentPane().add(textPanel);
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

        int red = (int) redText.getValue();
        int green = (int) greenText.getValue();
        int blue = (int) blueText.getValue();

        Color color = new Color(red, green, blue);

        if (search == null)
            return;

        int totalTab = mainFrame.getTextEditor().getTabCount();

        for (int i = 0; i < totalTab; i++) {
            TextEditorTab currentTab = (TextEditorTab) mainFrame.getTextEditor().getComponentAt(i);
            if (paths.contains(currentTab.getFile().toString())) {
                String content = currentTab.getContent().getText();

                StringJoiner joiner = new StringJoiner("|", "\\b(?:", ")\\b");
                joiner.add(Pattern.quote(search));

                String regex = joiner.toString();

                List<Integer> indexes = new ArrayList<Integer>();
                for (Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(content); m.find(); )
                    indexes.add(m.start());

                addHighlight(currentTab.getContent(), indexes, search, color);
            }
        }

        mainFrame.getStatusBar().setNotificationMessage("Search apply on '" + search + "'.");
        this.dispose();
    }

    private void addHighlight(JTextArea textArea, List<Integer> indexes, String search, Color color) {
        removeHighlight(textArea);
        Highlighter highlighter = textArea.getHighlighter();

        for (var elt : indexes) {
            try {
                highlighter.addHighlight(elt, elt + search.length(), new DefaultHighlighter.DefaultHighlightPainter(color));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeHighlight(JTextArea textArea) {
        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();
        System.out.println(highlights.length);

        for (Highlighter.Highlight highlight : highlights) {
            highlighter.removeHighlight(highlight);
        }
    }
}
