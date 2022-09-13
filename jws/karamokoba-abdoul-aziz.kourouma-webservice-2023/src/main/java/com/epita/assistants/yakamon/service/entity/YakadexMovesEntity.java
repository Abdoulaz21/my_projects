package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class YakadexMovesEntity {
    public Integer id;
    public String yakadex_id;
    public String move_id;

    public YakadexMovesEntity(Integer id, String yakadex_id, String move_id){
        this.id = id;
        this.move_id = move_id;
        this.yakadex_id = yakadex_id;
    }
}
