package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

import java.util.UUID;

@Entity
public class TrainerEntity {
    public UUID uuid;
    public String name;

    public TrainerEntity(UUID uuid, String name){
        this.name = name;
        this.uuid = uuid;
    }
}
