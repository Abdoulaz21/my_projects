package fr.epita.assistants.myide.domain.views.texteditor;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.java.log.Logged;
import org.fife.ui.rsyntaxtextarea.*;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class TextEditorTab extends JPanel implements Logged {
    private Node file;
    private RSyntaxTextArea content;

    public TextEditorTab(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public RSyntaxTextArea getContent() {
        return content;
    }

    public void setContent(RSyntaxTextArea content) {
        content.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        content.setCodeFoldingEnabled(true);
        content.setAntiAliasingEnabled(true);
        content.setAnimateBracketMatching(true);
        content.setAutoIndentEnabled(true);
        content.setBracketMatchingEnabled(true);

        if (MainFrame.getInstance().isDarkTheme()) {
            content.setBackground(new Color(0, 69, 92));
            content.setForeground(new Color(220, 220, 220));
        } else {
            content.setBackground(new Color(255, 255, 255));
            content.setForeground(new Color(45, 45, 45));
        }

        content.setCurrentLineHighlightColor(new Color(77, 175, 173));

        SyntaxScheme syntaxScheme = content.getSyntaxScheme();

        syntaxScheme.getStyle(Token.RESERVED_WORD).foreground = new Color(249, 38, 114);
        syntaxScheme.getStyle(Token.RESERVED_WORD_2).foreground = new Color(249, 38, 114);

        syntaxScheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(253, 151, 31);

        syntaxScheme.getStyle(Token.FUNCTION).foreground = new Color(102, 217, 239);
        syntaxScheme.getStyle(Token.SEPARATOR).foreground = new Color(110, 110, 110);

        // Comments
        syntaxScheme.getStyle(Token.COMMENT_MULTILINE).foreground = new Color(130, 130, 130);
        syntaxScheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground = new Color(130, 130, 130);
        syntaxScheme.getStyle(Token.ANNOTATION).foreground = new Color(130, 130, 130);

        // Immediate
        syntaxScheme.getStyle(Token.LITERAL_BOOLEAN).foreground = new Color(102, 217, 239);
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(102, 217, 239);
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = new Color(102, 217, 239);
        syntaxScheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = new Color(102, 217, 239);
        syntaxScheme.getStyle(Token.LITERAL_CHAR).foreground = new Color(102, 217, 239);

        this.content = content;
    }

    public Node getFile() {
        return file;
    }

    public void setFile(Node file) {
        this.file = file;
    }
}
