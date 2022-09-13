package fr.epita.assistants.myide.domain.views.fileexplorer;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileExplorerNode extends DefaultMutableTreeNode {
    private final Node node;
    private Icon icon;

    public FileExplorerNode(Node node) {
        super(node.toString(), (node.getType() == Node.Types.FOLDER));

        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
