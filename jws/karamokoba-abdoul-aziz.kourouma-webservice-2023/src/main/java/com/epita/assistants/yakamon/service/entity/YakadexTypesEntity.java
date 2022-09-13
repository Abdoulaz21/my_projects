package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class YakadexTypesEntity {
    public Integer id;
    public String yakadex_id;
    public String type_id;

    public YakadexTypesEntity(Integer id, String yakadex_id, String type_id){
        this.id = id;
        this.type_id = type_id;
        this.yakadex_id = yakadex_id;
    }
}
