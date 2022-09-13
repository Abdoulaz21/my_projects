package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;

@DtoResponse
public class ParticularYakadexEntryResponse {
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public String id;
    public String previousEvolutionYakadexId;
    public String nextEvolutionYakadexId;
    public List<String> moveIds;
    public List<String> typeIds;
    public List<String> zoneIds;

    public ParticularYakadexEntryResponse(String status,
                                          AllYakadexEntriesResponse.YakadexEntry entry){
        this.status = status;
        this.errorCode = null;
        this.errorMessage = null;
        this.id = entry.id;
        this.previousEvolutionYakadexId = entry.nextEvolutionYakadexId;
        this.nextEvolutionYakadexId = entry.nextEvolutionYakadexId;
        this.moveIds = entry.moveIds;
        this.typeIds = entry.typeIds;
        this.zoneIds = entry.zoneIds;
    }

    public ParticularYakadexEntryResponse(String status,
                                          String errorCode,
                                          String errorMessage,
                                          AllYakadexEntriesResponse.YakadexEntry entry){
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.id = entry.id;
        this.previousEvolutionYakadexId = entry.nextEvolutionYakadexId;
        this.nextEvolutionYakadexId = entry.nextEvolutionYakadexId;
        this.moveIds = entry.moveIds;
        this.typeIds = entry.typeIds;
        this.zoneIds = entry.zoneIds;
    }
}
