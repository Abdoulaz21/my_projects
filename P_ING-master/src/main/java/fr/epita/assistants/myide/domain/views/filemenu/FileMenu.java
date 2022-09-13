package fr.epita.assistants.myide.domain.views.filemenu;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.myide.domain.views.MainMenu;
import fr.epita.assistants.myide.domain.views.fileexplorer.FileExplorerNode;
import fr.epita.assistants.myide.domain.views.texteditor.TextEditorTab;
import fr.epita.assistants.utils.IconColor;
import fr.epita.java.log.Logged;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileMenu extends JMenu implements Logged {
    public FileMenu() {
        super("File");
        this.setMnemonic('F');

        this.setup();
    }

    private void setup() {
        this.add(this.getNewMenu());
        this.add(this.getOpenItem());

        this.addSeparator();

        this.add(this.getSettingsItem());

        this.addSeparator();

        this.add(this.getSaveAll());

        this.addSeparator();

        this.add(this.getExitItem());
    }

    private JMenu getNewMenu() {
        JMenu newMenu = new JMenu("New");
        newMenu.setMnemonic('N');

        JMenuItem fileItem = new JMenuItem("File");
        fileItem.addActionListener(this::newFileListener);
        newMenu.add(fileItem);

        JMenuItem folderItem = new JMenuItem("Folder");
        folderItem.addActionListener(this::newFolderListener);
        newMenu.add(folderItem);

        return newMenu;
    }

    private void newFileListener(ActionEvent event) {
        this.newNode(false);
    }

    private void newFolderListener(ActionEvent event) {
        this.newNode(true);
    }

    private void newNode(boolean isFolder) {
        MainFrame mainFrame = MainFrame.getInstance();

        if (!mainFrame.isProjectLoad()) {
            String noProjectMsg = "You must open a project before trying to create a File or a Folder.";
            JOptionPane.showMessageDialog(mainFrame, noProjectMsg);
            return;
        }

        String fileName = JOptionPane.showInputDialog(mainFrame, "Name:");
        FileExplorerNode parentNode = (FileExplorerNode) mainFrame.getFileExplorer().getLastSelectedPathComponent();

        if (!parentNode.getAllowsChildren())
            parentNode = (FileExplorerNode) parentNode.getParent();

        logger().info("Try to create: {}/{}", parentNode.getNode().toString(), fileName);

        Node.Types nodeType = (isFolder) ? Node.Types.FOLDER : Node.Types.FILE;
        Node newNode = mainFrame.getProjectService().getNodeService().create(parentNode.getNode(), fileName, nodeType);

        mainFrame.getFileExplorer().addNode(parentNode, new FileExplorerNode(newNode));

        logger().info("Created successfully: {}/{}", parentNode.getNode().toString(), fileName);
    }

    private JMenuItem getOpenItem() {
        JMenuItem openItem = new JMenuItem("Open...");

        openItem.setIcon(FontIcon.of(BootstrapIcons.FOLDER2_OPEN, 16, IconColor.getIconColor()));

        openItem.setMnemonic('O');
        openItem.setAccelerator(MainFrame.getInstance().openProjectShortcut);

        openItem.addActionListener(this::openListener);

        return openItem;
    }

    private void openListener(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int userAction = fileChooser.showOpenDialog(null);

        if (userAction == JFileChooser.APPROVE_OPTION) {
            Path projectPath = Path.of(fileChooser.getSelectedFile().getAbsolutePath());

            logger().info("Try to open: {}", projectPath.toString());

            MainFrame.getInstance().getContentPane().removeAll();

            Project project = MainFrame.getInstance().getProjectService().load(projectPath);
            MainFrame.getInstance().setupContentPane(project);

            MainFrame.getInstance().setJMenuBar(new MainMenu());

            MainFrame.getInstance().getContentPane().revalidate();
            MainFrame.getInstance().getContentPane().repaint();

            logger().info("Project opened successfully: {}", projectPath);
        } else {
            logger().info("No file has been selected.");
        }
    }

    private JMenuItem getSettingsItem() {
        JMenuItem settingsItem = new JMenuItem("Settings...");

        settingsItem.setIcon(FontIcon.of(BootstrapIcons.WRENCH, 16, IconColor.getIconColor()));

        settingsItem.setMnemonic('t');
        settingsItem.setAccelerator(MainFrame.getInstance().settingsShortcut);

        settingsItem.addActionListener(this::SettingsListener);
        return settingsItem;
    }

    private void SettingsListener(ActionEvent event) {
        MainFrame.getInstance().getSettingsMenu().displaySettings();
    }

    private JMenuItem getSaveAll() {
        JMenuItem saveAllItem = new JMenuItem("Save All");

        saveAllItem.setIcon(FontIcon.of(BootstrapIcons.SAVE, 16, IconColor.getIconColor()));

        saveAllItem.setMnemonic('S');
        saveAllItem.setAccelerator(MainFrame.getInstance().saveAllShortcut);

        saveAllItem.addActionListener(this::saveAllListener);

        return saveAllItem;
    }

    private void saveAllListener(ActionEvent event) {
        MainFrame mainFrame = MainFrame.getInstance();

        if (mainFrame.getTextEditor() == null) {
            mainFrame.getStatusBar().setNotificationMessage("No file(s) to save !");
            return;
        }

        int totalTab = mainFrame.getTextEditor().getTabCount();

        if (!mainFrame.isProjectLoad())
            return;

        for (int i = 0; i < totalTab; i++) {
            logger().info("Tab[{}]: {}", i, mainFrame.getTextEditor().getTitleAt(i));

            TextEditorTab currentTab = (TextEditorTab) mainFrame.getTextEditor().getComponentAt(i);
            try (BufferedWriter buff =
                         new BufferedWriter(new FileWriter(currentTab.getFile().getPath().toString(), false))) {
                buff.write(currentTab.getContent().getText());
            } catch (IOException e) {
                logger().error(e.getMessage());
            }
        }

        mainFrame.getStatusBar().setNotificationMessage("Saved all files successfully.");
    }

    private JMenuItem getExitItem() {
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');

        exitItem.addActionListener(e -> MainFrame.getInstance().dispose());

        return exitItem;
    }
}
