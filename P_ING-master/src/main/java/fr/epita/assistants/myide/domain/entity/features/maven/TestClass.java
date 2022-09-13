package fr.epita.assistants.myide.domain.entity.features.maven;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;

public class TestClass extends FeatureClass {
    private static TestClass instance;

    private TestClass(){
        super(Mandatory.Features.Maven.TEST);
    }

    public static TestClass getInstance(){
        if (instance == null){
            instance = new TestClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            ProcessBuilder pb = new ProcessBuilder()
                    .directory(project.getRootNode().getPath().toFile())
                    .command("bash", "-c", "mvn test");
            pb.redirectOutput(new File(project.getRootNode().getPath().toString() + "/log.txt")).start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return () -> false;
        }
        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.TEST;
    }
}
