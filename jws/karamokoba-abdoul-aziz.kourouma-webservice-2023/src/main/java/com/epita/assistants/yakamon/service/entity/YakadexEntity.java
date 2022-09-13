package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class YakadexEntity {
    public String name;
    public String previous;
    public String next;

    public YakadexEntity(String name, String previous, String next){
        this.name = name;
        this.previous = previous;
        this.next = next;
    }
}
