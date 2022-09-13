package fr.epita.assistants.myide.domain.entity.features.maven;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;

public class PackageClass extends FeatureClass {
    private static PackageClass instance;

    private PackageClass(){
        super(Mandatory.Features.Maven.PACKAGE);
    }

    public static PackageClass getInstance(){
        if (instance == null){
            instance = new PackageClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            ProcessBuilder pb = new ProcessBuilder()
                    .directory(project.getRootNode().getPath().toFile())
                    .command("bash", "-c", "mvn package");
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
        return Mandatory.Features.Maven.PACKAGE;
    }
}
