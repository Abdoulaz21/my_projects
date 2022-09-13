package fr.epita.assistants.myide.domain.entity.features.git;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import dorkbox.notify.Notify;
import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.util.FS;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class PullClass extends FeatureClass {
    private static PullClass instance;

    private PullClass(){
        super(Mandatory.Features.Git.PULL);
    }

    public static PullClass getInstance(){
        if (instance == null){
            instance = new PullClass();
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
                    } catch (JSchException | IllegalArgumentException jException) {
                        Notify.create()
                                .title("Git Pull Failed")
                                .text("Permission denied (publickey). \nCould not read from remote repository. \nPlease make sure you have the correct access rights \nand the repository exists.")
                                .darkStyle()
                                .hideAfter(5000)
                                .showError();
                    }
                    return null;
                }
            };

            Git git = Git.open(project.getRootNode().getPath().toFile());
            PullCommand pullCommand = git.pull();
            pullCommand.setTransportConfigCallback(transport -> {
                SshTransport sshTransport = (SshTransport) transport;
                sshTransport.setSshSessionFactory(sshSessionFactory);
            });
            if (finalRemote == null)
                pullCommand.call();
            else
                pullCommand.setRemote(finalRemote).call();
            URL pathToFile = getClass().getClassLoader().getResource("notifications/success.png");
            Image image = new ImageIcon(pathToFile).getImage();
            Notify.create()
                    .title("Git Pull Success")
                    .text("The project has been successfully updated")
                    .darkStyle()
                    .hideAfter(5000)
                    .image(image)
                    .show();
            return () -> true;
        } catch (GitAPIException | IOException ioException) {
            ioException.printStackTrace();
            Notify.create()
                    .title("Git Pull Failed")
                    .text("The project could not be updated")
                    .darkStyle()
                    .hideAfter(5000)
                    .showError();
            return () -> false;
        }
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.PULL;
    }
}
