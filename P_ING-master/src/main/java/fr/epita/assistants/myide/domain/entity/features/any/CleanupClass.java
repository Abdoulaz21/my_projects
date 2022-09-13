package fr.epita.assistants.myide.domain.entity.features.any;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.java.log.Logged;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CleanupClass extends FeatureClass implements Logged {
    private static CleanupClass instance;

    private CleanupClass(){
        super(Mandatory.Features.Any.CLEANUP);
    }

    public static CleanupClass getInstance(){
        if (instance == null){
            instance = new CleanupClass();
        }
        return instance;
    }

    public void deleteDir(File dir) {
        var files = dir.listFiles();
        if (files == null)
            return;

        for (var file : files) {
            if (file.isDirectory())
                deleteDir(file);
            else
                file.delete();
        }
    }

    public ExecutionReport cleanupFeature (Project project) {
        File ignoreFile = new File(project.getRootNode().getPath().resolve(".myideignore").toString());
        if (!ignoreFile.exists())
            return () -> false;

        String projectPath = project.getRootNode().getPath().toString();
        try(BufferedReader buff = new BufferedReader(new FileReader(ignoreFile.getPath()))) {
            String filename;
            while((filename = buff.readLine()) != null)
            {
                File fileToDelete = new File(projectPath + '/' + filename);
                if (fileToDelete.isDirectory())
                    deleteDir(fileToDelete);
                fileToDelete.delete();
                logger().info("{} is deleted", fileToDelete );
            }
        } catch (Exception e) {
            return () -> false;
        }
        return () -> true;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        return cleanupFeature(project);
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.CLEANUP;
    }
}
