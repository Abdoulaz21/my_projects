package fr.epita.assistants.myide.domain.entity;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodeClass implements Node {

    private Path path;
    private final Type type;

    public NodeClass(Path path, Type type) {
        this.path = path;
        this.type = type;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public @NotNull Path getPath() {
        return this.path;
    }

    @Override
    public @NotNull Type getType() {
        return this.type;
    }

    private static List<Node> getFolderChildren(File folder) {
        File[] files = folder.listFiles();
        List<Node> children = new ArrayList<>();

        if (files != null) {
            for (var f : files) {
                if (f.isFile())
                    children.add(new NodeClass(f.toPath(), Types.FILE));
                else
                    children.add(new NodeClass(f.toPath(), Types.FOLDER));
            }
        }

        return children;
    }

    @Override
    public @NotNull List<@NotNull Node> getChildren() {
        if (isFile())
            return Collections.emptyList();

        // FIXED: Return list of children's folder
        return getFolderChildren(this.path.toFile());
    }

    @Override
    public String toString() {
        return path.getFileName().toString();
    }
}
