package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class YakadexTypesModel {
    private Integer id;
    private String yakadex_id;
    private String type_id;

    public YakadexTypesModel(Integer id, String yakadex_id, String type_id){
        this.id = id;
        this.yakadex_id = yakadex_id;
        this.type_id = type_id;
    }

    public Integer getId() {
        return id;
    }

    public String getYakadex_id() {
        return yakadex_id;
    }

    public String getType_id() {
        return type_id;
    }
}
