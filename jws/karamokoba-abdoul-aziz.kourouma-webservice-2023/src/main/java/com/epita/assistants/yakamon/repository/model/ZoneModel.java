package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class ZoneModel {
    private String name;
    public ZoneModel(String name){ this.name = name; }

    public String getName() {
        return name;
    }
}
