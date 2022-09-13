package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

@DtoResponse
public class ParticularMoveResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final String name;

    public ParticularMoveResponse(final String status, final String move) {
        this.status = status;
        this.name = move;
        this.errorCode = null;
        this.errorMessage = null;
    }

    public ParticularMoveResponse(final String status,
                                  final String errorCode,
                                  final String errorMessage,
                                  final String move){
        this.status = status;
        this.name = move;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
