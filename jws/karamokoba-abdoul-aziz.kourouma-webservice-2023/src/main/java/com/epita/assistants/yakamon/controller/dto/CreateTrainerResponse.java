package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.UUID;

@DtoResponse
public class CreateTrainerResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final String name;
    public final UUID uuid;

    public CreateTrainerResponse(String status,
                                 String errorCode,
                                 String errorMessage,
                                 AllTrainersResponse.ParticularTrainer trainer){
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        if (trainer == null){
            this.name = null;
            this.uuid = null;
        } else {
            this.name = trainer.name;
            this.uuid = trainer.uuid;
        }
    }
}
