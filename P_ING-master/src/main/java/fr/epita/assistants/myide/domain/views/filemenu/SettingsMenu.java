package fr.epita.assistants.myide.domain.views.filemenu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import fr.epita.assistants.myide.domain.model.SettingsModel;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.myide.domain.views.MainMenu;
import fr.epita.assistants.utils.Shortcut;
import fr.epita.java.log.Logged;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SettingsMenu extends JFrame implements Logged {
    private final List<Shortcut> shortcuts;
    private JComboBox<String> theme;
    private JTextField breakWeatherCity;
    private JSpinner breakDelay;
    private JCheckBox breakActive;
    private JComboBox<String> font;
    private JComboBox<String> fontStyle;
    private JSpinner fontSize;

    public SettingsMenu() throws HeadlessException {
        super(MainFrame.getInstance().getTitle() + " - Settings");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.shortcuts = new ArrayList<>();
    }

    public boolean isDarkTheme() {
        if (this.theme != null)
            return this.theme.getSelectedIndex() == 0;
        return false;
    }

    public void displaySettings() {
        this.getUserSettings();
        this.pack();
        this.setVisible(true);
    }

    public void setup() {
        JPanel defaultContent = this.setupContent();
        this.getUserSettings();
        this.getContentPane().add(defaultContent, BorderLayout.CENTER);

        JPanel bottomButtons = new JPanel();
        bottomButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveSettingsListener);
        bottomButtons.add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this::cancelSettingsListener);
        bottomButtons.add(cancelButton);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(this::applySettingsListener);
        bottomButtons.add(applyButton);

        this.getContentPane().add(bottomButtons, BorderLayout.SOUTH);
    }

    public JPanel setupContent() {
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(8, 8, 8, 8);

        constraints.gridx = 0;
        constraints.gridy = 0;
        content.add(new JLabel("Themes:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        String[] themeChoices = {"Light", "Dark"};
        this.theme = new JComboBox<>(themeChoices);
        content.add(this.theme, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        content.add(new JLabel("Weather:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        this.breakWeatherCity = new JTextField();
        this.breakWeatherCity.setToolTipText("City that will be watch for Weather.");
        content.add(this.breakWeatherCity, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        this.breakDelay = new JSpinner(new SpinnerNumberModel(60, 1, 1440, 1));
        this.breakDelay.setToolTipText("Delay between two breaks in minutes.");
        content.add(this.breakDelay, constraints);

        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        this.breakActive = new JCheckBox("Active");
        content.add(this.breakActive, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        content.add(new JLabel("Fonts:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        String[] fontChoices = {"Comic Sans MS", "Arial", "Joker"};
        this.font = new JComboBox<>(fontChoices);
        content.add(this.font, constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        String[] styleChoices = {"Plain", "Bold", "Italic"};
        this.fontStyle = new JComboBox<>(styleChoices);
        content.add(this.fontStyle, constraints);

        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        this.fontSize = new JSpinner(new SpinnerNumberModel(16, 1, 64, 1));
        this.fontSize.setToolTipText("Size of font in pixel.");
        content.add(fontSize, constraints);

        /* SHORTCUTS SETUP */
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;

        String[] shortcuts = {
                "Open",
                "Settings",
                "Save All",
                "Search",
                "Replace",
                "Add",
                "Commit",
                "Push",
                "Pull"
        };

        for (String shortcutLabel : shortcuts) {
            content.add(new JLabel(shortcutLabel + ":"), constraints);
            Shortcut shortcut = new Shortcut();

            constraints.gridx++;
            content.add(shortcut.getKey(), constraints);

            constraints.gridx++;
            content.add(shortcut.getCtrl(), constraints);

            constraints.gridx++;
            content.add(shortcut.getAlt(), constraints);

            constraints.gridx++;
            content.add(shortcut.getShift(), constraints);

            this.shortcuts.add(shortcut);

            constraints.gridx = 0;
            constraints.gridy++;
        }

        return content;
    }

    private void getUserSettings() {
        File settingsFile = this.setupSettingsFile();

        SettingsModel settingsModel;
        try {
            ObjectMapper mapper = new ObjectMapper();

            String raw = Files.readString(settingsFile.toPath(), StandardCharsets.UTF_8);
            settingsModel = mapper.readValue(raw, SettingsModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        this.theme.setSelectedIndex(settingsModel.isDarkTheme() ? 1 : 0);

        this.breakWeatherCity.setText(settingsModel.getWeatherCity());
        this.breakDelay.setValue(settingsModel.getBreakDelay());
        this.breakActive.setSelected(settingsModel.isBreakActive());

        this.font.setSelectedItem(settingsModel.getFont());
        this.fontStyle.setSelectedIndex(settingsModel.getFontStyle());
        this.fontSize.setValue(settingsModel.getFontSize());

        for (int index = 0; index < shortcuts.size(); index++) {
            SettingsModel.Shortcut shortcutJson = settingsModel.getShortcuts().get(index);

            Shortcut shortcut = shortcuts.get(index);
            shortcut.getKey().setText(String.valueOf(shortcutJson.getKey()));
            shortcut.getCtrl().setSelected(shortcutJson.isCtrlMaskDown());
            shortcut.getAlt().setSelected(shortcutJson.isAltMaskDown());
            shortcut.getShift().setSelected(shortcutJson.isShiftMaskDown());
        }
    }

    private File setupSettingsFile() {
        File settingsFile = new File(System.getProperty("user.home") + "/.gorosei-settings.json");
        if (!settingsFile.exists()) {
            MainFrame.getInstance()
                    .getStatusBar()
                    .setNotificationMessage("No settings have been found. Created default .gorosei-settings.json");

            try {
                settingsFile.createNewFile();

                // DEFAULT JSON SETTINGS FILE
                try (BufferedWriter buff =
                             new BufferedWriter(new FileWriter(settingsFile, false))) {
                    buff.write("{\"weatherCity\":\"Paris\",\"breakDelay\":60,\"font\":\"Comic Sans MS\",\"fontSize\":16,\"fontStyle\":0,\"shortcuts\":[{\"key\":\"O\",\"ctrlMaskDown\":true,\"altMaskDown\":false,\"shiftMaskDown\":false},{\"key\":\"S\",\"ctrlMaskDown\":true,\"altMaskDown\":true,\"shiftMaskDown\":false},{\"key\":\"S\",\"ctrlMaskDown\":true,\"altMaskDown\":false,\"shiftMaskDown\":false},{\"key\":\"F\",\"ctrlMaskDown\":true,\"altMaskDown\":false,\"shiftMaskDown\":false},{\"key\":\"F\",\"ctrlMaskDown\":true,\"altMaskDown\":true,\"shiftMaskDown\":false},{\"key\":\"K\",\"ctrlMaskDown\":true,\"altMaskDown\":true,\"shiftMaskDown\":false},{\"key\":\"K\",\"ctrlMaskDown\":true,\"altMaskDown\":false,\"shiftMaskDown\":false},{\"key\":\"K\",\"ctrlMaskDown\":true,\"altMaskDown\":false,\"shiftMaskDown\":true},{\"key\":\"K\",\"ctrlMaskDown\":true,\"altMaskDown\":true,\"shiftMaskDown\":true}],\"darkTheme\":false,\"breakActive\":true}");
                } catch (IOException e) {
                    logger().error(e.getMessage());
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create default settings file");
            }
        }
        return settingsFile;
    }

    private void saveSettingsListener(ActionEvent event) {
        MainFrame mainFrame = MainFrame.getInstance();
        File settingsFile = this.setupSettingsFile();
        SettingsModel settingsModel = new SettingsModel();

        boolean isDarkTheme = ((String) this.theme.getSelectedItem()).contains("Dark");
        settingsModel.setDarkTheme(isDarkTheme);

        String weatherCity = this.breakWeatherCity.getText();
        settingsModel.setWeatherCity(weatherCity);

        int breakDelay = (int) this.breakDelay.getValue();
        settingsModel.setBreakDelay(breakDelay);

        boolean isBreakActive = this.breakActive.isSelected();
        settingsModel.setBreakActive(isBreakActive);

        String selectedFont = (String) this.font.getSelectedItem();
        settingsModel.setFont(selectedFont);

        int selectedFontStyle = fontStyle.getSelectedIndex();
        settingsModel.setFontStyle(selectedFontStyle);

        int selectedFontSize = (int) this.fontSize.getValue();
        settingsModel.setFontSize(selectedFontSize);

        for (Shortcut shortcut : shortcuts)
            settingsModel.addShortcut(shortcut);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonSettings = objectMapper.writeValueAsString(settingsModel);
            try (BufferedWriter buff =
                         new BufferedWriter(new FileWriter(settingsFile, false))) {
                buff.write(jsonSettings);
            } catch (IOException e) {
                logger().error(e.getMessage());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to save settings");
        }

        mainFrame.getStatusBar()
                .setNotificationMessage("Saved settings successfully");

        this.applySettingsListener(event);
        this.cancelSettingsListener(event);
    }

    private void cancelSettingsListener(ActionEvent event) {
        this.dispose();
    }

    private void applySettingsListener(ActionEvent event) {
        this.applySettings();

        MainFrame.getInstance()
                .getStatusBar()
                .setNotificationMessage("Applied settings successfully");
    }

    public void updateFont() {
        Font selectedFont = MainFrame.getInstance().getFontManager().getFontFromIndex(this.font.getSelectedIndex());
        int selectedFontStyle = this.fontStyle.getSelectedIndex();
        int selectedFontSize = (int) this.fontSize.getValue();
        MainFrame.getInstance().getTextEditor().setTabsFont(selectedFont, selectedFontStyle, selectedFontSize);
    }

    public void applySettings() {
        MainFrame mainFrame = MainFrame.getInstance();

        // Switching Light/Dark Theme
        mainFrame.setDarkTheme(!isDarkTheme());
        LookAndFeel theme = !mainFrame.isDarkTheme() ? new FlatCyanLightIJTheme() : new FlatCobalt2IJTheme();
        try {
            logger().info("Selected theme: " + theme);
            UIManager.setLookAndFeel(theme);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        for (Frame frame : Frame.getFrames())
            SwingUtilities.updateComponentTreeUI(frame);

        // WEATHER CITY
        mainFrame.getBreakManager().setWeatherCity(this.breakWeatherCity.getText());
        if (this.breakActive.isSelected()) {
            mainFrame.getBreakManager().stop();
            mainFrame.getBreakManager().init((Integer) this.breakDelay.getValue());
        } else {
            mainFrame.getBreakManager().stop();
        }

        // Update Font
        if (mainFrame.isProjectLoad()) {
            updateFont();
        }

        // SHORTCUTS
        mainFrame.openProjectShortcut = this.shortcuts.get(0).getKeyStroke();
        mainFrame.settingsShortcut = this.shortcuts.get(1).getKeyStroke();
        mainFrame.saveAllShortcut = this.shortcuts.get(2).getKeyStroke();
        mainFrame.searchShortcut = this.shortcuts.get(3).getKeyStroke();
        mainFrame.replaceShortcut = this.shortcuts.get(4).getKeyStroke();
        mainFrame.gitAddShortcut = this.shortcuts.get(5).getKeyStroke();
        mainFrame.gitCommitShortcut = this.shortcuts.get(6).getKeyStroke();
        mainFrame.gitPushShortcut = this.shortcuts.get(7).getKeyStroke();
        mainFrame.gitPullShortcut = this.shortcuts.get(8).getKeyStroke();

        mainFrame.setJMenuBar(new MainMenu());

        mainFrame.setupContentPane(mainFrame.getLoadedProject());
    }
}