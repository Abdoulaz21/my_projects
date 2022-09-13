package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.UUID;

@DtoResponse
public class ParticularTrainerResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final String name;
    public final UUID uuid;

    public ParticularTrainerResponse(String status, AllTrainersResponse.ParticularTrainer trainer){
        this.status = status;
        this.name = trainer.name;
        this.uuid = trainer.uuid;
        this.errorCode = null;
        this.errorMessage = null;
    }

    public ParticularTrainerResponse(String status,
                                     String errorCode,
                                     String errorMessage,
                                     AllTrainersResponse.ParticularTrainer trainer){
        this.status = status;
        this.name = trainer.name;
        this.uuid = trainer.uuid;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
