package fr.epita.assistants.myide.domain.entity.features.maven;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;

import java.io.File;
import java.io.IOException;

public class CleanClass extends FeatureClass {
    private static CleanClass instance;

    private CleanClass(){
        super(Mandatory.Features.Maven.CLEAN);
    }

    public static CleanClass getInstance(){
        if (instance == null){
            instance = new CleanClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            ProcessBuilder pb = new ProcessBuilder()
                    .directory(project.getRootNode().getPath().toFile())
                    .command("bash", "-c", "mvn clean");
            pb.redirectOutput(new File(project.getRootNode().getPath().toString() + "/log.txt")).start().waitFor();
        }
        catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
            return () -> false;
        }
        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.CLEAN;
    }
}
