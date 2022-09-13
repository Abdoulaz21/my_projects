package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;

@DtoResponse
public class PossibleYakamonsResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<String> yakadexIds;

    public PossibleYakamonsResponse(final String status, final List<String> possibleYakamons){
        this.status = status;
        this.yakadexIds = possibleYakamons;
        this.errorCode = null;
        this.errorMessage = null;
    }

    public PossibleYakamonsResponse(final String status,
                                  final String errorCode,
                                  final String errorMessage,
                                  final List<String> possibleYakamons){
        this.status = status;
        this.yakadexIds = possibleYakamons;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
