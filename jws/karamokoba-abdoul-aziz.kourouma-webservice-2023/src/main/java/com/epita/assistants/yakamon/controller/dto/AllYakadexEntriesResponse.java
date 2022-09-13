package com.epita.assistants.yakamon.controller.dto;

import com.epita.assistants.yakamon.arch.DtoResponse;

import java.util.List;

@DtoResponse
public class AllYakadexEntriesResponse {
    public static class YakadexEntry{
        public String id;
        public String previousEvolutionYakadexId;
        public String nextEvolutionYakadexId;
        public List<String> moveIds;
        public List<String> typeIds;
        public List<String> zoneIds;

        public YakadexEntry(String name,
                            String previous,
                            String next,
                            List<String> moveIds,
                            List<String> typeIds,
                            List<String> zoneIds){
            this.id = name;
            this.previousEvolutionYakadexId = previous;
            this.nextEvolutionYakadexId = next;
            this.moveIds = moveIds;
            this.typeIds = typeIds;
            this.zoneIds = zoneIds;
        }
    }
    public final String status;
    public final String errorCode;
    public final String errorMessage;
    public final List<YakadexEntry> yakadexEntries;

    public AllYakadexEntriesResponse(String status,
                                          List<YakadexEntry> entries){
        this.status = status;
        this.yakadexEntries = entries;
        this.errorCode = null;
        this.errorMessage = null;
    }
}
