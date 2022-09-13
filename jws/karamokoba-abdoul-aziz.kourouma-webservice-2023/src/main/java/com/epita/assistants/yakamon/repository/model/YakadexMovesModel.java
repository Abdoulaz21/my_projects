package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class YakadexMovesModel {
    private Integer id;
    private String yakadex_id;
    private String move_id;

    public YakadexMovesModel(Integer id, String yakadex_id, String move_id){
        this.id = id;
        this.yakadex_id = yakadex_id;
        this.move_id = move_id;
    }

    public Integer getId() {
        return id;
    }

    public String getYakadex_id() {
        return yakadex_id;
    }

    public String getMove_id() {
        return move_id;
    }
}
