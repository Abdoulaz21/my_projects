package fr.epita.assistants.myide.domain.entity.features.maven;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

public class TreeClass extends FeatureClass {
    private static TreeClass instance;

    private TreeClass(){
        super(Mandatory.Features.Maven.TREE);
    }

    public static TreeClass getInstance(){
        if (instance == null){
            instance = new TreeClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        StringJoiner cmd = new StringJoiner(" ");
        cmd.add("mvn");
        cmd.add("dependency:tree");

        if (params != null) {
            for (var param : params) {
                cmd.add((String) param);
            }
        }

        try {
            ProcessBuilder pb = new ProcessBuilder()
                    .directory(project.getRootNode().getPath().toFile())
                    .command("bash", "-c", cmd.toString());
            pb.redirectOutput(new File(project.getRootNode().getPath().toString() + "/log.txt")).start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return () -> false;
        }
        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.TREE;
    }
}
