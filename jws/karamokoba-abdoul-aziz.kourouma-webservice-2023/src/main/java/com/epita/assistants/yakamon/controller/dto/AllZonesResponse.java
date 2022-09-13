package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;
import java.util.stream.Collectors;

@DtoResponse
public class AllZonesResponse {
    static class Zones {
        public final String name;
        public Zones(String name){ this.name = name; }
    }
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<Zones> zones;

    public AllZonesResponse(final String status, final List<String> zones){
        this.status = status;
        this.zones = zones.stream()
                          .map(Zones::new)
                          .collect(Collectors.toList());
        this.errorCode = null;
        this.errorMessage = null;
    }
}
