package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

@DtoResponse
public class ParticularZoneResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final String name;

    public ParticularZoneResponse(final String status, final String zone){
        this.status = status;
        this.name = zone;
        this.errorCode = null;
        this.errorMessage = null;
    }

    public ParticularZoneResponse(final String status,
                                  final String errorCode,
                                  final String errorMessage,
                                  final String zone){
        this.status = status;
        this.name = zone;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
