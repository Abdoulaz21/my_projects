package fr.epita.assistants.myide.domain.entity.features.git;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import dorkbox.notify.Notify;
import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.java.log.Logged;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.util.FS;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class PushClass extends FeatureClass implements Logged {
    private static PushClass instance;

    private PushClass(){
        super(Mandatory.Features.Git.PUSH);
    }

    public static PushClass getInstance(){
        if (instance == null){
            instance = new PushClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        String password = null;
        String remote = null;
        for (var p : params)
        {
            if (password == null)
                password = p.toString();
            else if (remote == null)
                remote = p.toString();
        }

        try {
            String finalPassword = password;
            String finalRemote = remote;
            SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
                @Override
                protected void configure(OpenSshConfig.Host host, Session session) {
                    session.setConfig("StrictHostKeyChecking", "no");
                }

                @Override
                protected JSch getJSch(OpenSshConfig.Host hc, FS fs) {
                    try {
                        JSch defaultJSch = super.getJSch(hc, fs);
                        defaultJSch.removeAllIdentity();
                        if (finalPassword.trim().length() != 0)
                            defaultJSch.addIdentity(Paths.get(System.getProperty("user.home"), "/.ssh/id_rsa").toString(), finalPassword);
                        else
                            defaultJSch.addIdentity(Paths.get(System.getProperty("user.home"), "/.ssh/id_rsa").toString());
                        return defaultJSch;
                    }
                    catch (JSchException | IllegalArgumentException jException)
                    {
                        Notify.create()
                                .title("Git Push Failed")
                                .text("Permission denied (publickey). \nCould not read from remote repository. \nPlease make sure you have the correct access rights \nand the repository exists.")
                                .darkStyle()
                                .hideAfter(5000)
                                .showError();
                    }
                    return null;
                }
            };

            Git git = Git.open(project.getRootNode().getPath().toFile());
            PushCommand pushCommand = git.push();
            pushCommand.setTransportConfigCallback(transport -> {
                SshTransport sshTransport = (SshTransport) transport;
                sshTransport.setSshSessionFactory(sshSessionFactory);
            });
            if (finalRemote == null)
                pushCommand.call();
            else
                pushCommand.setRemote(finalRemote).call();
            URL pathToFile = getClass().getClassLoader().getResource("notifications/success.png");
            Image image = new ImageIcon(pathToFile).getImage();
            Notify.create()
                    .title("Git Push Success")
                    .text("All the files were successefully pushed")
                    .darkStyle()
                    .hideAfter(5000)
                    .image(image)
                    .show();
            return () -> true;
        } catch (GitAPIException | IOException ioException) {
            ioException.printStackTrace();
            Notify.create()
                    .title("Git Push Failed")
                    .text("The files could not be pushed")
                    .darkStyle()
                    .hideAfter(5000)
                    .showError();
            return () -> false;
        }
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.PUSH;
    }
}
