package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class YakadexZonesEntity {
    public Integer id;
    public String yakadex_id;
    public String zone_id;

    public YakadexZonesEntity(Integer id, String yakadex_id, String zone_id){
        this.id = id;
        this.zone_id = zone_id;
        this.yakadex_id = yakadex_id;
    }
}
