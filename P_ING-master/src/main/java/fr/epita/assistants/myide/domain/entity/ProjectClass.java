package fr.epita.assistants.myide.domain.entity;

import fr.epita.java.log.Logged;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project, Logged {

    final private Path path;
    private Set<Aspect> aspects;

    void computeGitProject(File project, Set<Aspect> aspects){
        FilenameFilter filterGit = (f, name) -> name.equals(".git");

        File[] filesGit = project.listFiles(filterGit);
        if (Arrays.stream(filesGit).toList().size() > 0)
            aspects.add(new AspectClass(Mandatory.Aspects.GIT));
    }

    void computeMavenProject(File project, Set<Aspect> aspects){
        FilenameFilter filterMaven = (f, name) -> name.equals("pom.xml");

        File[] filesMaven = project.listFiles(filterMaven);
        if (Arrays.stream(filesMaven).toList().size() > 0)
            aspects.add(new AspectClass(Mandatory.Aspects.MAVEN));
    }

    public ProjectClass(final Path path) {
        this.path = path;
        File project = new File(path.toString());
        Set<Aspect> aspects = new HashSet<Aspect>();
        aspects.add(new AspectClass(Mandatory.Aspects.ANY));

        computeGitProject(project, aspects);
        computeMavenProject(project, aspects);
        this.aspects = aspects;
    }

    @Override
    public @NotNull Node getRootNode() {
        return new NodeClass(this.path, Node.Types.FOLDER);
    }

    @Override
    public @NotNull Set<Aspect> getAspects() {
        return aspects;
    }

    @Override
    public @NotNull Optional<Feature> getFeature(Feature.@NotNull Type featureType) {
        var features = this.getFeatures();
        for (var feature : features){
            if (feature.type().equals(featureType)) {
                return Optional.of(feature);
            }
        }
        return Optional.empty();
    }
}
