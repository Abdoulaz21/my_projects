package com.epita.assistants.yakamon.service;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.service.entity.TypeEntity;
import com.epita.assistants.yakamon.repository.model.TypeModel;
import com.epita.assistants.yakamon.repository.TypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {
    private final TypeRepository repo;
    public TypeService(final TypeRepository repo){
        this.repo = repo;
    }

    public static TypeEntity modelToEntity(TypeModel model){
        return new TypeEntity(model.getName());
    }

    public TypeEntity particularTypeget(final String name){
        var model = this.repo.particularTypeModelget(name);
        if (model == null){ return null; }
        return modelToEntity(model);
    }

    public List<TypeEntity> allTypesget(){
        return this.repo.allTypesModelsget()
                        .stream()
                        .map(TypeService::modelToEntity)
                        .collect(Collectors.toList());
    }
}
