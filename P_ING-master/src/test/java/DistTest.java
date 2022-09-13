import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;
import fr.epita.assistants.myide.domain.entity.features.any.DistClass;
import org.junit.jupiter.api.Test;

import java.io.File;

public class DistTest {
    @Test
    void zipEasy() {
        File projectFile = new File("testfolder/project");
        Project project = new ProjectClass(projectFile.toPath().toAbsolutePath());

        DistClass distClass = DistClass.getInstance();
        var distReport = distClass.execute(project);

        assert(distReport.isSuccess());
    }
}
