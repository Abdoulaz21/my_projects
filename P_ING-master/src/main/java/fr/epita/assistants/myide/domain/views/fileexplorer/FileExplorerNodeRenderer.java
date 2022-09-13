package fr.epita.assistants.myide.domain.views.fileexplorer;

import fr.epita.assistants.utils.IconColor;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.fileicons.FileIcons;
import org.kordamp.ikonli.simpleicons.SimpleIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class FileExplorerNodeRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        FileExplorerNode node = (FileExplorerNode) value;
        String filename = node.getNode().toString();

        if (filename.endsWith(".java")) {
            Icon icon = FontIcon.of(SimpleIcons.JAVA, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else if (filename.endsWith(".c") || filename.endsWith(".h")) {
            Icon icon = FontIcon.of(SimpleIcons.C, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else if (filename.endsWith(".cc") || filename.endsWith(".hh")) {
            Icon icon = FontIcon.of(FileIcons.C3, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else if (filename.endsWith(".sh")) {
            Icon icon = FontIcon.of(SimpleIcons.GNUBASH, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else if (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".bmp")) {
            Icon icon = FontIcon.of(BootstrapIcons.FILE_EARMARK_IMAGE, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else if (leaf && !node.getAllowsChildren()) {
            Icon icon = FontIcon.of(BootstrapIcons.FILE_EARMARK_CODE_FILL, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else if (expanded) {
            Icon icon = FontIcon.of(BootstrapIcons.FOLDER2_OPEN, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        } else {
            Icon icon = FontIcon.of(BootstrapIcons.FOLDER, 16, IconColor.getIconColor());
            this.setIcon(icon);
            node.setIcon(icon);
        }

        return this;
    }
}
