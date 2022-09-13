package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.stream.Collectors;

@DtoResponse
public class AllTypesResponse {
    static class Types {
        public final String name;
        public Types(String name){ this.name = name; }
    }
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<Types> types;

    public AllTypesResponse(final String status, final List<String> types){
        this.status = status;
        this.types = types.stream()
                .map(Types::new)
                .collect(Collectors.toList());;
        this.errorCode = null;
        this.errorMessage = null;
    }
}
