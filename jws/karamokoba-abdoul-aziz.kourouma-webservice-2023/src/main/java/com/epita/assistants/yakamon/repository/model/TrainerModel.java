package com.epita.assistants.yakamon.repository.model;

import java.util.UUID;

public class TrainerModel {
    private UUID uuid;
    private String name;

    public TrainerModel(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
