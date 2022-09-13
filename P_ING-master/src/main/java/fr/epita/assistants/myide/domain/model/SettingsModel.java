package fr.epita.assistants.myide.domain.model;

import java.util.ArrayList;
import java.util.List;

public class SettingsModel {
    public static class Shortcut {
        private char key;
        private boolean isCtrlMaskDown;
        private boolean isAltMaskDown;
        private boolean isShiftMaskDown;

        public Shortcut() {
        }

        public Shortcut(char key, boolean isCtrlMaskDown, boolean isAltMaskDown, boolean isShiftMaskDown) {
            this.key = key;
            this.isCtrlMaskDown = isCtrlMaskDown;
            this.isAltMaskDown = isAltMaskDown;
            this.isShiftMaskDown = isShiftMaskDown;
        }

        public char getKey() {
            return key;
        }

        public boolean isCtrlMaskDown() {
            return isCtrlMaskDown;
        }

        public boolean isAltMaskDown() {
            return isAltMaskDown;
        }

        public boolean isShiftMaskDown() {
            return isShiftMaskDown;
        }

        public void setKey(char key) {
            this.key = key;
        }

        public void setCtrlMaskDown(boolean ctrlMaskDown) {
            isCtrlMaskDown = ctrlMaskDown;
        }

        public void setAltMaskDown(boolean altMaskDown) {
            isAltMaskDown = altMaskDown;
        }

        public void setShiftMaskDown(boolean shiftMaskDown) {
            isShiftMaskDown = shiftMaskDown;
        }
    }

    private boolean isDarkTheme;

    private String weatherCity;
    private int breakDelay;
    private boolean isBreakActive;

    private String font;
    private int fontSize;
    private int fontStyle;

    private List<Shortcut> shortcuts;

    public SettingsModel() {
        this.shortcuts = new ArrayList<>();
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

    public String getWeatherCity() {
        return weatherCity;
    }

    public void setWeatherCity(String weatherCity) {
        this.weatherCity = weatherCity;
    }

    public int getBreakDelay() {
        return breakDelay;
    }

    public void setBreakDelay(int breakDelay) {
        this.breakDelay = breakDelay;
    }

    public boolean isBreakActive() {
        return isBreakActive;
    }

    public void setBreakActive(boolean breakActive) {
        isBreakActive = breakActive;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void addShortcut(fr.epita.assistants.utils.Shortcut shortcut) {
        char key = shortcut.getKey().getText().charAt(0);
        boolean ctrl = shortcut.getCtrl().isSelected();
        boolean alt = shortcut.getAlt().isSelected();
        boolean shift = shortcut.getShift().isSelected();

        this.shortcuts.add(new Shortcut(key, ctrl, alt, shift));
    }

    public List<Shortcut> getShortcuts() {
        return shortcuts;
    }
}
