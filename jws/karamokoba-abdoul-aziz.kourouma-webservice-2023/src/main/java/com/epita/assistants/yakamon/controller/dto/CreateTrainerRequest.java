package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoRequest;

import java.util.UUID;

@DtoRequest
public class CreateTrainerRequest {
    public String name;
    public String uuid;

    public CreateTrainerRequest(){}

    public CreateTrainerRequest(String name, String uuid){
        this.name = name;
        this.uuid = uuid;
    }
}
