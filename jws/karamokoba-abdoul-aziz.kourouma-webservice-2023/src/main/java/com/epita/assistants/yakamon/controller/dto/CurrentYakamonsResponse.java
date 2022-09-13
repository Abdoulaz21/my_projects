package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.UUID;

@DtoResponse
public class CurrentYakamonsResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<UUID> yakamonIds;

    public CurrentYakamonsResponse(final String status, final List<UUID> currentYakamons){
        this.status = status;
        this.yakamonIds = currentYakamons;
        this.errorCode = null;
        this.errorMessage = null;
    }

    public CurrentYakamonsResponse(final String status,
                                  final String errorCode,
                                  final String errorMessage,
                                  final List<UUID> currentYakamons){
        this.status = status;
        this.yakamonIds = currentYakamons;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
