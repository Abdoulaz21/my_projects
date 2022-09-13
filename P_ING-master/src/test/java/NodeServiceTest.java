import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.NodeServiceClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class NodeServiceTest {
    private static NodeService nodeService;

    @BeforeAll
    static void beforeAll() {
        nodeService = new NodeServiceClass();
    }

   /* @AfterEach
    void tearDown() {
        try {
            Files.walk(Path.of("testfolder/nodeCreation")).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Test
    void fileNodeCreation() {
        File file = new File("testfolder/nodeCreation");
        NodeClass folder = new NodeClass(file.toPath().toAbsolutePath(), Node.Types.FOLDER);

        nodeService.create(folder, "simpleFile.txt", Node.Types.FILE);

        var list = folder.getChildren();
        assertEquals(1, list.size());
    }

    @Test
    void folderNodeCreation() {
        File file = new File("testfolder/nodeCreation");
        NodeClass folder = new NodeClass(file.toPath().toAbsolutePath(), Node.Types.FOLDER);

        nodeService.create(folder, "simpleFolder", Node.Types.FOLDER);

        var list = folder.getChildren();
        assertEquals(1, list.size());
    }

    @Test
    void deleteFolderAndFile() throws IOException {
        File folder = new File("testfolder/deleteFolder");
        assertTrue(folder.mkdir());

        File file = new File("testfolder/deleteFolder/deleteFile.txt");
        assertTrue(file.createNewFile());

        Node folderNode = new NodeClass(folder.toPath().toAbsolutePath(), Node.Types.FOLDER);

        File root = new File("testfolder");

        nodeService.delete(folderNode);

        Node parentFolder = new NodeClass(root.toPath().toAbsolutePath(), Node.Types.FOLDER);
        assertEquals(1, parentFolder.getChildren().size());
    }

    @Test
    void deleteFile() throws IOException {
        File file = new File("testfolder/deleteFile.txt");
        assertTrue(file.createNewFile());

        Node fileNode = new NodeClass(file.toPath().toAbsolutePath(), Node.Types.FILE);

        File root = new File("testfolder");

        nodeService.delete(fileNode);

        Node parentFolder = new NodeClass(root.toPath().toAbsolutePath(), Node.Types.FOLDER);
        assertEquals(1, parentFolder.getChildren().size());
    }

    @Test
    void moveSimpleFile() {
        File dstFolder = new File("testfolder/destinationMove");
        Node dstFolderNode = new NodeClass(dstFolder.toPath().toAbsolutePath(), Node.Types.FOLDER);

        File toMove = new File("testfolder/toMove.txt");
        Node toMoveNode = new NodeClass(toMove.toPath().toAbsolutePath(), Node.Types.FILE);

        nodeService.move(toMoveNode, dstFolderNode);
    }

    @Test
    void moveEmptyDirectoryFile() {
        File dstFolder = new File("testfolder/destinationMove");
        Node dstFolderNode = new NodeClass(dstFolder.toPath().toAbsolutePath(), Node.Types.FOLDER);

        File toMove = new File("testfolder/toMoveFolder");
        Node toMoveNode = new NodeClass(toMove.toPath().toAbsolutePath(), Node.Types.FOLDER);

        nodeService.move(toMoveNode, dstFolderNode);
    }

    @Test
    void updateFile() throws IOException {
        File toUpdate = new File("testfolder/toUpdate.txt");
        Node toUpdateNode = new NodeClass(toUpdate.toPath().toAbsolutePath(), Node.Types.FILE);

        nodeService.update(toUpdateNode, 10, 15, ("i love this project").getBytes());
    }

    @Test
    void updateFileFail() throws IOException {
        File toUpdate = new File("testfolder/toUpdate.txt");
        Node toUpdateNode = new NodeClass(toUpdate.toPath().toAbsolutePath(), Node.Types.FILE);

        nodeService.update(toUpdateNode, 2, 1, ("i love this project").getBytes());
    }

    @Test
    void updateFile2() throws IOException {
        String input = "this is a test input";

        FileOutputStream fout = new FileOutputStream("testfolder/toUpdateBis.txt");
        FileChannel channel = fout.getChannel();

        ByteBuffer buff = ByteBuffer.wrap(input.getBytes());
        channel.write(buff);
        buff.flip();

        channel = channel.truncate(5);
        assertEquals(5, channel.size());

        fout.close();
        channel.close();
    }
}
