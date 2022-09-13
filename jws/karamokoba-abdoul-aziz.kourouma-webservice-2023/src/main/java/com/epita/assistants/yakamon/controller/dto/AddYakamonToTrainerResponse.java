package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

@DtoResponse
public class AddYakamonToTrainerResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;

    public AddYakamonToTrainerResponse(String status, String errorCode, String errorMessage){
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
