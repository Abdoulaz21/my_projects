package fr.epita.assistants.myide.domain.views.maven;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.features.maven.*;
import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.assistants.utils.IconColor;
import fr.epita.java.log.Logged;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.swing.FontIcon;

import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MavenMenu extends JMenu implements Logged {
    private final Icon icon;

    public MavenMenu() {
        super("Macadamia");
        this.setMnemonic('M');

        this.icon = FontIcon.of(BootstrapIcons.GEAR_FILL, 16, IconColor.getIconColor());

        this.setup();
    }

    private void setup() {
        this.add(this.getCleanItem());
        this.add(this.getCompileItem());
        this.add(this.getExecItem());
        this.add(this.getInstallItem());
        this.add(this.getPackageItem());
        this.add(this.getTestItem());
        this.add(this.getTreeItem());
    }

    private JMenuItem getCleanItem() {
        JMenuItem cleanItem = new JMenuItem("Clean");
        cleanItem.setIcon(this.icon);
        cleanItem.addActionListener(this::mavenCleanListener);

        return cleanItem;
    }

    private void displayLogs(Feature.ExecutionReport report, String op){
        StringBuilder logBuilder = new StringBuilder();

        try {
            var br = new BufferedReader(new FileReader(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString() + "/log.txt"));
            String line;
            while ((line = br.readLine()) != null)
                logBuilder.append(line).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }

        var file = new File(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toString() + "/log.txt");
        file.delete();

        String logs = logBuilder.toString();
        MainFrame.getInstance().getBottomTabbedPane().getLogTextArea().setText(logs);
    }

    private void mavenCleanListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        CleanClass clean = CleanClass.getInstance();
        var cleanReport = clean.execute(project);
        displayLogs(cleanReport, "Clean");
    }

    private JMenuItem getCompileItem() {
        JMenuItem compileItem = new JMenuItem("Compile");
        compileItem.setIcon(this.icon);
        compileItem.addActionListener(this::mavenCompileListener);

        return compileItem;
    }

    private void mavenCompileListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        CompileClass compile = CompileClass.getInstance();
        var compileReport = compile.execute(project);
        displayLogs(compileReport, "Compile");
    }

    private JMenuItem getExecItem() {
        JMenuItem execItem = new JMenuItem("Exec");
        execItem.setIcon(this.icon);
        execItem.addActionListener(this::mavenExecListener);

        return execItem;
    }

    private void mavenExecListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        ExecClass exec = ExecClass.getInstance();
        var execReport = exec.execute(project);
        displayLogs(execReport, "Exec");
    }

    private JMenuItem getInstallItem() {
        JMenuItem installItem = new JMenuItem("Install");
        installItem.setIcon(this.icon);
        installItem.addActionListener(this::mavenInstallListener);

        return installItem;
    }

    private void mavenInstallListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        InstallClass install = InstallClass.getInstance();
        var installReport = install.execute(project);
        displayLogs(installReport, "Install");
    }

    private JMenuItem getPackageItem() {
        JMenuItem packageItem = new JMenuItem("Package");
        packageItem.setIcon(this.icon);
        packageItem.addActionListener(this::mavenPackageListener);

        return packageItem;
    }

    private void mavenPackageListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        PackageClass mavenpackage = PackageClass.getInstance();
        var packageReport = mavenpackage.execute(project);
        displayLogs(packageReport, "Package");
    }

    private JMenuItem getTestItem() {
        JMenuItem testItem = new JMenuItem("Test");
        testItem.setIcon(this.icon);
        testItem.addActionListener(this::mavenTestListener);

        return testItem;
    }

    private void mavenTestListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        TestClass test = TestClass.getInstance();
        var testReport = test.execute(project);
        displayLogs(testReport, "Test");
    }

    private JMenuItem getTreeItem() {
        JMenuItem treeItem = new JMenuItem("Tree");
        treeItem.setIcon(this.icon);
        treeItem.addActionListener(this::mavenTreeListener);

        return treeItem;
    }

    private void mavenTreeListener(ActionEvent event) {
        Project project = MainFrame.getInstance().getLoadedProject();
        TreeClass tree = TreeClass.getInstance();
        var treeReport = tree.execute(project);
        displayLogs(treeReport, "Tree");
    }
}
