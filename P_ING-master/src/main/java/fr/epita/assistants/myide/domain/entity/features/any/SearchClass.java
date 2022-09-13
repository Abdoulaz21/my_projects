package fr.epita.assistants.myide.domain.entity.features.any;

import fr.epita.assistants.myide.domain.entity.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;

public class SearchClass extends FeatureClass {
    private static SearchClass instance;

    private SearchClass(){
        super(Mandatory.Features.Any.SEARCH);
    }

    public static SearchClass getInstance(){
        if (instance == null){
            instance = new SearchClass();
        }
        return instance;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        File projectFile = new File(project.getRootNode().getPath().toString());
        File[] allFiles = projectFile.listFiles();
        /*if (allFiles.length == 0)
            return () -> false;
        for (File file : allFiles)
        {
            if (file.isDirectory())
            {

            }
            else


        }*/

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.SEARCH;
    }
}
