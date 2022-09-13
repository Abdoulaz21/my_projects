package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.stream.Collectors;

@DtoResponse
public class AllMovesResponse {
    static class Moves {
        public final String name;
        public Moves(String name){ this.name = name; }
    }
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<Moves> moves;

    public AllMovesResponse(final String status, final List<String> moves) {
        this.status = status;
        this.moves = moves.stream()
                          .map(Moves::new)
                          .collect(Collectors.toList());
        this.errorCode = null;
        this.errorMessage = null;
    }
}
