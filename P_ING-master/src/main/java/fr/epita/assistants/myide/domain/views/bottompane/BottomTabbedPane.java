package fr.epita.assistants.myide.domain.views.bottompane;

import com.formdev.flatlaf.FlatClientProperties;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.utils.IconColor;
import fr.epita.java.log.Logged;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class BottomTabbedPane extends JTabbedPane implements Logged {
    JTextArea logTextArea;

    public BottomTabbedPane() {
        this.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, false);

        Icon terminalIcon = FontIcon.of(BootstrapIcons.TERMINAL, 16, IconColor.getIconColor());
        this.addTab("Terminal", terminalIcon, MainFrame.getInstance().getTerminal());

        Icon logIcon = FontIcon.of(BootstrapIcons.NEWSPAPER, 16, IconColor.getIconColor());
        this.logTextArea = new JTextArea();

        if (MainFrame.getInstance().isDarkTheme())
            this.logTextArea.setBackground(new Color(0, 69, 92));
        else
            this.logTextArea.setBackground(new Color(202, 240, 248));
        this.logTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(this.logTextArea);
        this.addTab("Logs", logIcon, scrollPane);

    }

    public JTextArea getLogTextArea() {
        return logTextArea;
    }
}
