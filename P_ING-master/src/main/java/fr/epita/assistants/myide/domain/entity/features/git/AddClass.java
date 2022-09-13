package fr.epita.assistants.myide.domain.entity.features.git;

import dorkbox.notify.Notify;
import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddClass extends FeatureClass {
    private static AddClass instance;

    private AddClass(){
        super(Mandatory.Features.Git.ADD);
    }

    public static AddClass getInstance(){
        if (instance == null){
            instance = new AddClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            Git git = Git.open(project.getRootNode().getPath().toFile());
            for (var p: params) {
                    git.add().addFilepattern(p.toString()).call();
            }
            URL pathToFile = getClass().getClassLoader().getResource("notifications/success.png");
            Image image = new ImageIcon(pathToFile).getImage();
            Notify.create()
                    .title("Git Add Success")
                    .text("The files has been successfully added to the repository")
                    .darkStyle()
                    .hideAfter(5000)
                    .image(image)
                    .show();
            return () -> true;
        } catch (IOException | GitAPIException ioException) {
            ioException.printStackTrace();
            Notify.create()
                    .title("Git Add Failed")
                    .text("The files could not be added")
                    .darkStyle()
                    .hideAfter(5000)
                    .showError();
            return () -> false;
        }
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.ADD;
    }
}
