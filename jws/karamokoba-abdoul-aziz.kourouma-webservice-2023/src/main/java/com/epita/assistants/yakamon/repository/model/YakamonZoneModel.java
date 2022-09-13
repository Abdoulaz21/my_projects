package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

import java.util.UUID;

@Model
public class YakamonZoneModel {
    private UUID yakamon_id;

    public YakamonZoneModel(UUID yakamon_id){
        this.yakamon_id = yakamon_id;
    }

    public UUID getYakamon_id() {
        return yakamon_id;
    }
}
