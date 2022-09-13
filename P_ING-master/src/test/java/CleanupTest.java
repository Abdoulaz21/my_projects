import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;
import fr.epita.assistants.myide.domain.entity.features.any.CleanupClass;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class CleanupTest {
    @Test
    void noCleanUpFileTest(){
        File projectFile = new File("testFolder");
        ProjectClass project = new ProjectClass(projectFile.toPath().toAbsolutePath());

        CleanupClass cleanup = CleanupClass.getInstance();
        var cleanupReport = cleanup.execute(project);

        assert(!cleanupReport.isSuccess());
    }

    @Test
    void basicCleanUpTest(){
        File projectFile = new File("testFolder");
        ProjectClass project = new ProjectClass(projectFile.toPath().toAbsolutePath());

        CleanupClass cleanup = CleanupClass.getInstance();
        var cleanupReport = cleanup.execute(project);

        assert(cleanupReport.isSuccess());
    }

    @Test
    void basic2CleanUpTest() {
        File projectFile = new File("testfolder");
        Project project = new ProjectClass(projectFile.toPath().toAbsolutePath());

        CleanupClass cleanup = CleanupClass.getInstance();
        var cr = cleanup.execute(project);

        assert(cr.isSuccess());
    }
}
