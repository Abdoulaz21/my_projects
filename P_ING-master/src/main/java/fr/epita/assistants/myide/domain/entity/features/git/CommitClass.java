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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class CommitClass extends FeatureClass {
    private static CommitClass instance;

    private CommitClass(){
        super(Mandatory.Features.Git.COMMIT);
    }

    public static CommitClass getInstance(){
        if (instance == null){
            instance = new CommitClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            Git git = Git.open(project.getRootNode().getPath().toFile());
            if (params.length == 0)
                git.commit().setMessage("Empty commit message").call();
            else if (params.length == 1)
                git.commit().setMessage(params[0].toString()).call();
            else
                return () -> false;
            URL pathToFile = getClass().getClassLoader().getResource("notifications/success.png");
            Image image = new ImageIcon(pathToFile).getImage();
            Notify.create()
                    .title("Git Commit Success")
                    .text("The files added has been successfully commited")
                    .darkStyle()
                    .hideAfter(5000)
                    .image(image)
                    .show();
            return () -> true;
        } catch (IOException | GitAPIException ioException) {
            ioException.printStackTrace();
            Notify.create()
                    .title("Git Commit Failed")
                    .text("The files could not be commited")
                    .darkStyle()
                    .hideAfter(5000)
                    .showError();
            return () -> false;
        }
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.COMMIT;
    }
}
