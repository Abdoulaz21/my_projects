package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class TypeModel {
    private String name;
    public TypeModel(final String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
