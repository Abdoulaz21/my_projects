package fr.epita.assistants.myide.domain.views.texteditor;

import com.formdev.flatlaf.FlatClientProperties;
import com.google.common.io.Files;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.java.log.Logged;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

public class TextEditor extends JTabbedPane implements Logged {
    private Font currentFont;

    public TextEditor() {
        this.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSABLE, true);
        this.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_TOOLTIPTEXT, "Close");
        this.putClientProperty(FlatClientProperties.TABBED_PANE_TAB_CLOSE_CALLBACK,
                (BiConsumer<JTabbedPane, Integer>) JTabbedPane::removeTabAt
        );

        this.currentFont = MainFrame.getInstance().getFontManager().getFontFromIndex(0);
    }

    public void openFile(Node file, Icon icon) {
        TextEditorTab tab = new TextEditorTab(false);
        tab.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;

        try {
            tab.setContent(new RSyntaxTextArea(
                    Files.asCharSource(new File(file.getPath().toString()), StandardCharsets.UTF_8).read()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tab.setFile(file);

        RSyntaxTextArea content = tab.getContent();
        content.setFont(currentFont);

        tab.setContent(content);

        RTextScrollPane scrollPane = new RTextScrollPane(tab.getContent());
        tab.add(scrollPane, constraints);

        this.addTab(file.toString(), icon, tab, file.getPath().toString());
    }

    public void setTabsFont(Font font, int fontStyle, int fontSize) {
        currentFont = font.deriveFont(fontStyle, fontSize);

        int totalTab = this.getTabCount();
        for (int i = 0; i < totalTab; i++) {
            logger().info("Tab[{}]: {}", i, this.getTitleAt(i));

            TextEditorTab currentTab = (TextEditorTab) this.getComponentAt(i);
            RSyntaxTextArea content = currentTab.getContent();
            content.setFont(currentFont);
            currentTab.setContent(content);
        }
    }
}
