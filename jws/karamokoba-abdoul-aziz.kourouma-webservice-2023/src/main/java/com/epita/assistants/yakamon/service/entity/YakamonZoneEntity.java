package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

import java.util.UUID;

@Entity
public class YakamonZoneEntity {
    public UUID yakamon_id;

    public YakamonZoneEntity(UUID yakamon_id){
        this.yakamon_id = yakamon_id;
    }
}
