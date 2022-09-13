package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.UUID;

@DtoResponse
public class AllTrainersResponse {
    public static class ParticularTrainer{
        public String name;
        public UUID uuid;

        public ParticularTrainer(String name, UUID uuid){
            this.name = name;
            this.uuid = uuid;
        }
    }
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<ParticularTrainer> trainers;

    public AllTrainersResponse(String status,
                               String errorCode,
                               String errorMessage,
                               List<ParticularTrainer> trainers){
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.trainers = trainers;
    }
}
