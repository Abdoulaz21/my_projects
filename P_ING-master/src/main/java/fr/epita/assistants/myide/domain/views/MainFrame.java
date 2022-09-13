package fr.epita.assistants.myide.domain.views;

import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectService;
import fr.epita.assistants.myide.domain.views.bottompane.BottomTabbedPane;
import fr.epita.assistants.myide.domain.views.bottompane.TerminalClass;
import fr.epita.assistants.myide.domain.views.breakmanager.BreakManager;
import fr.epita.assistants.myide.domain.views.fileexplorer.FileExplorer;
import fr.epita.assistants.myide.domain.views.fileexplorer.FileExplorerNode;
import fr.epita.assistants.myide.domain.views.filemenu.SettingsMenu;
import fr.epita.assistants.myide.domain.views.texteditor.TextEditor;
import fr.epita.assistants.utils.FontManager;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainFrame extends JFrame {
    private static volatile MainFrame instance;
    private final FontManager fontManager;
    private final BreakManager breakManager;
    public KeyStroke openProjectShortcut;
    public KeyStroke settingsShortcut;
    public KeyStroke saveAllShortcut;
    public KeyStroke searchShortcut;
    public KeyStroke replaceShortcut;
    public KeyStroke gitAddShortcut;
    public KeyStroke gitCommitShortcut;
    public KeyStroke gitPushShortcut;
    public KeyStroke gitPullShortcut;
    private ProjectService projectService;
    private boolean isProjectLoad;
    private SettingsMenu settingsMenu;
    private FileExplorer fileExplorer;
    private TextEditor textEditor;
    private StatusBar statusBar;
    private Project loadedProject;
    private boolean isDarkTheme;
    private TerminalClass terminal;
    private BottomTabbedPane bottomTabbedPane;

    private MainFrame() throws HeadlessException {
        super("Gorōsei IDE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        fontManager = new FontManager();

        isProjectLoad = false;

        breakManager = new BreakManager();
    }

    public static MainFrame getInstance() {
        MainFrame result = instance;
        if (result != null)
            return result;

        synchronized (MainFrame.class) {
            if (instance == null)
                instance = new MainFrame();
            return instance;
        }
    }

    @Override
    public void dispose() {
        this.breakManager.stop();
        super.dispose();
    }

    public Project getLoadedProject() {
        return loadedProject;
    }

    public void setLoadedProject(Project loadedProject) {
        this.loadedProject = loadedProject;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        isDarkTheme = darkTheme;
    }

    public boolean isProjectLoad() {
        return isProjectLoad;
    }

    public BreakManager getBreakManager() {
        return breakManager;
    }

    public FileExplorer getFileExplorer() {
        return fileExplorer;
    }

    public TextEditor getTextEditor() {
        return textEditor;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public SettingsMenu getSettingsMenu() {
        return settingsMenu;
    }

    public void setSettingsMenu(SettingsMenu settingsMenu) {
        this.settingsMenu = settingsMenu;
    }

    public TerminalClass getTerminal() {
        return terminal;
    }

    public void setTerminal(TerminalClass terminal) {
        this.terminal = terminal;
    }

    public BottomTabbedPane getBottomTabbedPane() {
        return bottomTabbedPane;
    }

    public void setupContentPane(Project project) {
        this.getContentPane().removeAll();

        if (project != null) {
            this.setLoadedProject(project);
            isProjectLoad = true;

            FileExplorerNode fileExplorerRoot = new FileExplorerNode(project.getRootNode());
            fileExplorer = new FileExplorer(fileExplorerRoot, new DefaultTreeModel(fileExplorerRoot));
            fileExplorer.updateTree((FileExplorerNode) fileExplorer.getModel().getRoot());

            JScrollPane scrollPaneFileExplorer = new JScrollPane(fileExplorer);

            textEditor = new TextEditor();
            this.getSettingsMenu().updateFont();

            JSplitPane fileExplorerAndTextEditorPane =
                    new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneFileExplorer, textEditor);
            fileExplorerAndTextEditorPane.setOrientation(SwingConstants.VERTICAL);
            fileExplorerAndTextEditorPane.setResizeWeight(0.20);

            this.terminal = new TerminalClass(this.isDarkTheme());
            bottomTabbedPane = new BottomTabbedPane();
            bottomTabbedPane.invalidate();
            bottomTabbedPane.validate();
            bottomTabbedPane.repaint();

            JSplitPane bottomPane =
                    new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileExplorerAndTextEditorPane, bottomTabbedPane);
            bottomPane.setOrientation(SwingConstants.HORIZONTAL);
            bottomPane.setResizeWeight(0.75);

            this.getContentPane().add(bottomPane, BorderLayout.CENTER);
        } else {
            List<String> happies = new ArrayList<String>();
            happies.add("N'oubliez pas de fréquenter des gens heureux car le bonheur c'est contagieux");
            happies.add("Rien n'est plus important que le bonheur");
            happies.add("Soyez heureux, le bonheur souris aux audacieux");
            happies.add("La confiance est le plus court chemin vers le bonheur");
            happies.add("Respirez, souriez, le bonheur vous guette");
            happies.add("Partez à la rencontre du bonheur");
            happies.add("Merci de prendre une bonne dose de bohneur avant toute utilisation");
            happies.add("Tout le bonheur du monde est dans l'innatendu");
            happies.add("Transmission du bonheur en cours... vous allez le recevoir sous peu");
            happies.add("Sheeeeeesh tu es heureux fraté ?");
            happies.add("Les gens heureux n'ont pas besoin d'être pressé");
            happies.add("Abonnez-vous au bonheur");
            happies.add("Joie, amour, paix, amitié, famille, bonheur");
            happies.add("Le plus important, c'est le bonheur de ma famille - Dominic Toretto");
            happies.add("Ici, le bonheur est fait maison");

            Random rand = new Random();
            String random = happies.get(rand.nextInt(happies.size()));

            JLabel welcomeMessage = new JLabel(random);

            welcomeMessage.setFont(new Font(welcomeMessage.getFont().getName(), Font.PLAIN, 25));

            this.getContentPane().add(welcomeMessage, BorderLayout.CENTER);
        }

        statusBar = new StatusBar();
        statusBar.setGitActualBranch();
        this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
    }
}
