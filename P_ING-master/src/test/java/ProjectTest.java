import fr.epita.assistants.myide.domain.entity.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

public class ProjectTest {
    @Test
    void anyProjectTest(){
        File anyProject = new File("testFolder");
        ProjectClass any = new ProjectClass(anyProject.toPath().toAbsolutePath());

        /*Node root = any.getRootNode();
        System.out.println(root.getPath().toString());*/

        Set<Aspect> anyAspects = any.getAspects();

        assert(anyAspects.size() == 1);
    }

    @Test
    void mavenProjectTest(){
        File mavenProject = new File("testFolder");
        ProjectClass maven = new ProjectClass(mavenProject.toPath().toAbsolutePath());

        /*Node root = maven.getRootNode();
        System.out.println(root.getPath().toString());*/

        Set<Aspect> mavenAspects = maven.getAspects();

        assert(mavenAspects.size() == 2);
    }

    @Test
    void gitProjectTest(){
        File gitProject = new File("testFolder");
        ProjectClass git = new ProjectClass(gitProject.toPath().toAbsolutePath());

        /*Node root = git.getRootNode();
        System.out.println(root.getPath().toString());*/

        Set<Aspect> gitAspects = git.getAspects();

        assert(gitAspects.size() == 3);
    }

    @Test
    void anyFeatureTest(){
        File anyProject = new File("testFolder");
        ProjectClass any = new ProjectClass(anyProject.toPath().toAbsolutePath());

        var features = any.getFeature(Mandatory.Features.Any.CLEANUP);
        assert(features.get().type().equals(Mandatory.Features.Any.CLEANUP));
    }

    @Test
    void anyFeatureButNotGitTest(){
        File anyProject = new File("testFolder");
        ProjectClass any = new ProjectClass(anyProject.toPath().toAbsolutePath());

        var features = any.getFeature(Mandatory.Features.Git.COMMIT);
        assert(features.isEmpty());
    }
}
