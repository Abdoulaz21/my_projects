package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

@DtoResponse
public class ParticularTypeResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final String type;

    public ParticularTypeResponse(final String status, final String type){
        this.status = status;
        this.type = type;
        this.errorCode = null;
        this.errorMessage = null;
    }

    public ParticularTypeResponse(final String status,
                                  final String code,
                                  final String message){
        this.status = status;
        this.type = null;
        this.errorCode = code;
        this.errorMessage = message;
    }
}
