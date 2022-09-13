package fr.epita.assistants.utils;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Shortcut{
    private final JTextField key;
    private final JCheckBox ctrl;
    private final JCheckBox alt;
    private final JCheckBox shift;

    public Shortcut() {
        this.key = new JTextField();
        this.key.setDocument(new JTextFieldLimit(1));

        this.ctrl = new JCheckBox("CTRL");
        this.ctrl.setSelected(true);
        this.ctrl.setEnabled(false);

        this.alt = new JCheckBox("ALT");
        this.shift = new JCheckBox("SHIFT");
    }

    public JTextField getKey() {
        return key;
    }

    public JCheckBox getCtrl() {
        return ctrl;
    }

    public JCheckBox getAlt() {
        return alt;
    }

    public JCheckBox getShift() {
        return shift;
    }

    private int getKeyEvent(char key) {
        return switch (key) {
            case '0' -> KeyEvent.VK_0;
            case '1' -> KeyEvent.VK_1;
            case '2' -> KeyEvent.VK_2;
            case '3' -> KeyEvent.VK_3;
            case '4' -> KeyEvent.VK_4;
            case '5' -> KeyEvent.VK_5;
            case '6' -> KeyEvent.VK_6;
            case '7' -> KeyEvent.VK_7;
            case '8' -> KeyEvent.VK_8;
            case '9' -> KeyEvent.VK_9;

            case 'A' -> KeyEvent.VK_A;
            case 'B' -> KeyEvent.VK_B;
            case 'C' -> KeyEvent.VK_C;
            case 'D' -> KeyEvent.VK_D;
            case 'E' -> KeyEvent.VK_E;
            case 'F' -> KeyEvent.VK_F;
            case 'G' -> KeyEvent.VK_G;
            case 'H' -> KeyEvent.VK_H;
            case 'I' -> KeyEvent.VK_I;
            case 'J' -> KeyEvent.VK_J;
            case 'K' -> KeyEvent.VK_K;
            case 'L' -> KeyEvent.VK_L;
            case 'M' -> KeyEvent.VK_M;
            case 'N' -> KeyEvent.VK_N;
            case 'O' -> KeyEvent.VK_O;
            case 'P' -> KeyEvent.VK_P;
            case 'Q' -> KeyEvent.VK_Q;
            case 'R' -> KeyEvent.VK_R;
            case 'S' -> KeyEvent.VK_S;
            case 'T' -> KeyEvent.VK_T;
            case 'U' -> KeyEvent.VK_U;
            case 'V' -> KeyEvent.VK_V;
            case 'X' -> KeyEvent.VK_X;
            case 'Y' -> KeyEvent.VK_Y;
            case 'Z' -> KeyEvent.VK_Z;
            default -> throw new RuntimeException("Unsupported Key for shortcut.");
        };
    }

    public KeyStroke getKeyStroke() {
        int mainKey = getKeyEvent(getKey().getText().charAt(0));
        int ctrlMask = getCtrl().isSelected() ? KeyEvent.CTRL_DOWN_MASK : 0;
        int altMask = getAlt().isSelected() ? KeyEvent.ALT_DOWN_MASK : 0;
        int shiftMask = getShift().isSelected() ? KeyEvent.SHIFT_DOWN_MASK : 0;

        return KeyStroke.getKeyStroke(mainKey, ctrlMask | altMask | shiftMask);
    }
}
