package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;

public class ProjectServiceClass implements ProjectService {

    final private NodeService nodeService;

    public ProjectServiceClass(final NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public @NotNull Project load(@NotNull Path root) {
        return new ProjectClass(root);
    }

    @Override
    public @NotNull Feature.ExecutionReport execute(@NotNull Project project,
                                                    Feature.@NotNull Type featureType,
                                                    Object... params) {
        return null;
    }

    @Override
    public @NotNull NodeService getNodeService() {
        return this.nodeService;
    }
}
