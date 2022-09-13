package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.UUID;

@DtoResponse
public class AllYakamonsOfTrainerResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public List<UUID> trainerYakamonIds;

    public AllYakamonsOfTrainerResponse(String status, String errorCode, String errorMessage, List<UUID> yakamons){
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.trainerYakamonIds = yakamons;
    }
}
