package fr.epita.assistants.myide.domain.views.fileexplorer;

import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.java.log.Logged;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileExplorer extends JTree implements Logged {
    private final FileExplorerNode treeRoot;
    private final DefaultTreeModel treeModel;

    public FileExplorer(FileExplorerNode projectRoot, DefaultTreeModel treeModel) {
        super(treeModel);

        this.treeRoot = projectRoot;
        this.treeModel = treeModel;

        this.setCellRenderer(new FileExplorerNodeRenderer());
        this.setEditable(true);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setShowsRootHandles(true);

        FileExplorer fileExplorer = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    FileExplorerNode node = (FileExplorerNode) fileExplorer.getLastSelectedPathComponent();

                    if (node == null || node.getAllowsChildren())
                        return;

                    logger().info("DoubleClick on: " + node.getNode().toString());
                    MainFrame.getInstance().getTextEditor().openFile(node.getNode(), node.getIcon());
                }
            }
        });
    }

    public void addNode(FileExplorerNode parent, FileExplorerNode child) {
        if (parent == null)
            parent = this.treeRoot;

        this.treeModel.insertNodeInto(child, parent, parent.getChildCount());
        this.scrollPathToVisible(new TreePath(child.getPath()));
    }

    public void updateTree(FileExplorerNode treeNode) {
        for (var children : treeNode.getNode().getChildren()) {
            FileExplorerNode newTreeNode = new FileExplorerNode(children);
            treeNode.add(newTreeNode);
            this.updateTree(newTreeNode);
        }
    }
}
