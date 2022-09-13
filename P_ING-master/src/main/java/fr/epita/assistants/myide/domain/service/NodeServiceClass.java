package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;

import java.io.*;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class NodeServiceClass implements NodeService {
    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent)  {
        if (!node.isFile())
            throw new IllegalArgumentException("Node is not a file");

        assert(from <= to); //make sure we have good index
        try {
            String path = node.getPath().toString();

            File newFile = new File(path);

            long fileSize = newFile.length();

            RandomAccessFile source = new RandomAccessFile(newFile, "rw");
            RandomAccessFile tmp = new RandomAccessFile(new File(path + "~"), "rw");

            FileChannel sourceChannel = source.getChannel();
            FileChannel tmpChannel = tmp.getChannel();

            sourceChannel.transferTo(to, fileSize - to, tmpChannel);
            sourceChannel.truncate(from);

            source.seek(from);
            source.write(insertedContent);

            long newOffset = source.getFilePointer();

            tmpChannel.position(0L);

            sourceChannel.transferFrom(tmpChannel, newOffset, fileSize - to);

            sourceChannel.close();
            tmpChannel.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Could not update the file");
        }

        return node;
    }

    private boolean deleteRec(File file) {
        var files = file.listFiles();
        if (files != null) {
            for (var f : files) {
                deleteRec(f);
            }
        }
        return file.delete();
    }

    @Override
    public boolean delete(Node node) {
        Path path = node.getPath();
        return deleteRec(new File(path.toString()));
    }

    @Override
    public Node create(Node folder, String name, Node.Type type) {
        if (folder.getType() != Node.Types.FOLDER)
            throw new RuntimeException("FIXME");

        String fullPath = folder.getPath().toAbsolutePath().toString() + '/' + name;

        File newFile = new File(fullPath);
        if (type == Node.Types.FILE) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("File Creation Failed.");
            }
        }
        if (type == Node.Types.FOLDER)
            newFile.mkdirs();

        return new NodeClass(Path.of(fullPath), type);
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        Path fileName = nodeToMove.getPath().getFileName();
        String newPath = destinationFolder.getPath().toAbsolutePath().toString() + '/' + fileName.toString();

        try {
            Files.move(nodeToMove.getPath().toAbsolutePath(), Path.of(newPath));
        } catch (IOException e) {
            throw new RuntimeException("Unable to move the file.");
        }

        return new NodeClass(Path.of(newPath), nodeToMove.getType());
    }
}
