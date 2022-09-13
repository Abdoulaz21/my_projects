package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class TypeEntity {
    public String name;

    public TypeEntity(final String name){
        this.name = name;
    }
}
