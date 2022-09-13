package fr.epita.assistants.myide.domain.entity;


import fr.epita.assistants.myide.domain.entity.features.any.CleanupClass;
import fr.epita.assistants.myide.domain.entity.features.any.DistClass;
import fr.epita.assistants.myide.domain.entity.features.any.SearchClass;
import fr.epita.assistants.myide.domain.entity.features.git.AddClass;
import fr.epita.assistants.myide.domain.entity.features.git.CommitClass;
import fr.epita.assistants.myide.domain.entity.features.git.PullClass;
import fr.epita.assistants.myide.domain.entity.features.git.PushClass;
import fr.epita.assistants.myide.domain.entity.features.maven.*;

import java.util.ArrayList;
import java.util.List;

public class AspectClass implements Aspect {

    private final Type type;
    private List<Feature> features;

    void computeAnyAspect(List<Feature> actual){
        if (type.equals(Mandatory.Aspects.ANY)){
            actual.add(CleanupClass.getInstance());
            actual.add(DistClass.getInstance());
            actual.add(SearchClass.getInstance());
        }
    }

    void computeGitAspect(List<Feature> actual){
        if (type.equals(Mandatory.Aspects.GIT)){
            actual.add(AddClass.getInstance());
            actual.add(CommitClass.getInstance());
            actual.add(PullClass.getInstance());
            actual.add(PushClass.getInstance());
        }
    }

    void computeMavenAspect(List<Feature> actual){
        if (type.equals(Mandatory.Aspects.MAVEN)){
            actual.add(CleanClass.getInstance());
            actual.add(CompileClass.getInstance());
            actual.add(ExecClass.getInstance());
            actual.add(InstallClass.getInstance());
            actual.add(PackageClass.getInstance());
            actual.add(TestClass.getInstance());
            actual.add(TreeClass.getInstance());
        }
    }

    public AspectClass(Type type) {
        this.type = type;
        List<Feature> actualFeatures = new ArrayList<>();
        computeAnyAspect(actualFeatures);
        computeGitAspect(actualFeatures);
        computeMavenAspect(actualFeatures);
        features = actualFeatures;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public List<Feature> getFeatureList() {
        return features;
    }
}
