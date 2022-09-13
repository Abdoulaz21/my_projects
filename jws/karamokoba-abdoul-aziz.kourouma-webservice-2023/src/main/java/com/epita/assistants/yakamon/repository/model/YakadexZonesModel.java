package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class YakadexZonesModel {
    private Integer id;
    private String yakadex_id;
    private String zone_id;

    public YakadexZonesModel(Integer id, String yakadex_id, String zone_id){
        this.id = id;
        this.zone_id = zone_id;
        this.yakadex_id = yakadex_id;
    }

    public Integer getId() { return id; }

    public String getYakadex_id() {
        return yakadex_id;
    }

    public String getZone_id() { return zone_id; }
}
