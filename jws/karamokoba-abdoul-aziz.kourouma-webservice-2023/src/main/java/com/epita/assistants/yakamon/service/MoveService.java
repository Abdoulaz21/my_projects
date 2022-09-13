package com.epita.assistants.yakamon.service;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.service.entity.MoveEntity;
import com.epita.assistants.yakamon.repository.model.MoveModel;
import com.epita.assistants.yakamon.repository.MoveRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoveService {
    private final MoveRepository repo;
    public MoveService(final MoveRepository repo){
        this.repo = repo;
    }

    public static MoveEntity modelToEntity(MoveModel model){
        return new MoveEntity(model.getName());
    }

    public MoveEntity particularMoveget(final String name){
        var model = this.repo.particularMoveModelget(name);
        if (model == null){ return null; }
        return modelToEntity(model);
    }

    public List<MoveEntity> allMovesget(){
        return this.repo.allMovesModelsget()
                .stream()
                .map(MoveService::modelToEntity)
                .collect(Collectors.toList());
    }
}
