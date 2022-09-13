package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class MoveModel {
    private String name;
    public MoveModel(final String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }
}
