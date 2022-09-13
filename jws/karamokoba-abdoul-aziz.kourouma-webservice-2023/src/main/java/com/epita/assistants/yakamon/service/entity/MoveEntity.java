package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class MoveEntity {
    public String name;

    public MoveEntity(final String name){
        this.name = name;
    }
}
