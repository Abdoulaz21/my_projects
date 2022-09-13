package fr.epita.assistants.myide.domain.entity;

import javax.validation.constraints.NotNull;

public abstract class FeatureClass implements Feature {

    private final Type featureType;

    public FeatureClass(Type type) {
        featureType = type;
    }

    @Override
    public @NotNull ExecutionReport execute (Project project, Object...params){ return null; }

    @Override
    public @NotNull Type type ()
    {
        return featureType;
    }

}
