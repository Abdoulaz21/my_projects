package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoRequest;

import java.util.UUID;

@DtoRequest
public class RenameTrainerRequest {
    public String name;

    public RenameTrainerRequest(){}

    public RenameTrainerRequest(String name){
        this.name = name;
    }
}
