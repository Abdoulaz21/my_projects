package com.epita.assistants.yakamon.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class YakadexModel {
    private String name;
    private String previous;
    private String next;

    public YakadexModel(String name, String previous, String next){
        this.name = name;
        this.previous = previous;
        this.next = next;
    }

    public String getName() {
        return name;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }
}
