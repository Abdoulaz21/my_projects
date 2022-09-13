package com.epita.assistants.yakamon.service;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.repository.TrainerYakamonRepository;
import com.epita.assistants.yakamon.service.entity.TrainerEntity;
import com.epita.assistants.yakamon.repository.model.TrainerModel;
import com.epita.assistants.yakamon.repository.TrainerRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrainerService {
    private final TrainerRepository trainerrepo;
    private final TrainerYakamonRepository yakamonrepo;

    public TrainerService(TrainerRepository repo, TrainerYakamonRepository yak){
        this.trainerrepo = repo;
        this.yakamonrepo = yak;
    }

    public TrainerEntity modelToEntity_trainer(TrainerModel model){
        if (model == null){ return null; }
        return new TrainerEntity(model.getUuid(), model.getName());
    }

    public List<TrainerEntity> allTrainersget(){
        return this.trainerrepo.allTrainerModelsget()
                        .stream()
                        .map(this::modelToEntity_trainer)
                        .collect(Collectors.toList());
    }

    public TrainerEntity particularTrainerget(UUID uuid){
        return modelToEntity_trainer(this.trainerrepo.particularTrainerModelget(uuid));
    }

    public TrainerEntity createTrainer(UUID uuid, String name){
        return modelToEntity_trainer(this.trainerrepo.createTrainerModel(uuid, name));
    }

    public boolean renameTrainer(UUID uuid, String name){
        return this.trainerrepo.renameTrainerModel(uuid, name);
    }

    public boolean deleteTrainer(UUID uuid){
        return this.trainerrepo.deleteTrainerModel(uuid);
    }

    public List<UUID> yakamonsOfTrainerget(UUID uuid){
        return this.yakamonrepo.yakamonsOfThisTrainerget(uuid);
    }

    public boolean addYakamonToTrainer(UUID uuid, UUID yakId){
        return this.yakamonrepo.addYakamonToTrainerModel(uuid, yakId);
    }

    public boolean freeYakamonOfTrainerInZone(UUID uuid, UUID yakId, String zone){
        return this.trainerrepo.freeYakamonOfTrainerInZoneModel(uuid, yakId, zone);
    }
}
