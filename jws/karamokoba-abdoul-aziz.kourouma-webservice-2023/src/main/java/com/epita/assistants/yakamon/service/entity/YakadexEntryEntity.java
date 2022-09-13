package com.epita.assistants.yakamon.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

import java.util.List;

@Entity
public class YakadexEntryEntity {
    public String name;
    public String previous;
    public String next;
    public List<String> moveIds;
    public List<String> typeIds;
    public List<String> zoneIds;

    public YakadexEntryEntity(String name,
                              String previous,
                              String next,
                              List<String> moveIds,
                              List<String> typeIds,
                              List<String> zoneIds){
        this.name = name;
        this.previous = previous;
        this.next = next;
        this.moveIds = moveIds;
        this.typeIds = typeIds;
        this.zoneIds = zoneIds;
    }
}
