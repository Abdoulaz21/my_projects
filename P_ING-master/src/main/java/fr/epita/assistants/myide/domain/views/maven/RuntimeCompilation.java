package fr.epita.assistants.myide.domain.views.maven;

import fr.epita.assistants.myide.domain.views.MainFrame;
import fr.epita.java.log.Logged;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RuntimeCompilation implements Logged {
    private class LogThread extends Thread {
        @Override
        public void run() {
            try {
                MainFrame.getInstance().getBottomTabbedPane().getLogTextArea().setText("\n");
                BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while((line=br.readLine())!=null)
                    MainFrame.getInstance()
                            .getBottomTabbedPane()
                            .getLogTextArea()
                            .append(line + '\n');

                while (process.isAlive())
                    Thread.sleep(100);
                exitCode = process.exitValue();

                MainFrame.getInstance()
                        .getBottomTabbedPane()
                        .getLogTextArea()
                        .append("\nProcess exited with code " + exitCode);

                if (exitCode != 0) {
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("cat_hello.png"));
                    JOptionPane.showMessageDialog(
                            null,
                            "Courage ! Je suis sur que le prochain essai sera le bon.",
                            "Runtime Compilation",
                            JOptionPane.ERROR_MESSAGE,
                            icon
                    );
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private Process process;
    private LogThread logThread;
    private int exitCode;

    public int getExitCode() {
        return exitCode;
    }

    public void launch(String packageName) {
        try {
            ProcessBuilder mavenCompile = new ProcessBuilder()
                    .directory(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toFile())
                    .command("bash", "-c", "mvn compile");
            mavenCompile.start().waitFor();

            ProcessBuilder processBuilder = new ProcessBuilder()
                    .directory(MainFrame.getInstance().getLoadedProject().getRootNode().getPath().toFile())
                    .command("bash", "-c", "mvn exec:java -Dexec.mainClass=\"" + packageName + "\"");

            this.process = processBuilder.start();

            logThread = new LogThread();
            logThread.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void killProcess() throws InterruptedException {
        this.process.destroy();
        while (this.process.isAlive())
            Thread.sleep(100);
        exitCode = this.process.exitValue();
    }
}
