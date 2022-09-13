import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;
import org.junit.jupiter.api.Test;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    @Test
    void basic_folder_children() {
        File file = new File("testfolder");

        NodeClass nodeClass = new NodeClass(file.toPath().toAbsolutePath(), Node.Types.FOLDER);

        var list = nodeClass.getChildren();

        assertEquals(2, list.size());
    }
}
