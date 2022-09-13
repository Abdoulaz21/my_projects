package fr.epita.assistants.myide.domain.entity.features.maven;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;

public class InstallClass extends FeatureClass {
    private static InstallClass instance;

    private InstallClass(){
        super(Mandatory.Features.Maven.INSTALL);
    }

    public static InstallClass getInstance(){
        if (instance == null){
            instance = new InstallClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            ProcessBuilder pb = new ProcessBuilder()
                    .directory(project.getRootNode().getPath().toFile())
                    .command("bash", "-c", "mvn install");
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
        return Mandatory.Features.Maven.INSTALL;
    }
}
