package com.epita.assistants.yakamon.controller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.controller.dto.AllYakadexEntriesResponse;
import com.epita.assistants.yakamon.controller.dto.ParticularYakadexEntryResponse;
import com.epita.assistants.yakamon.misc.Error;
import com.epita.assistants.yakamon.misc.Status;
import com.epita.assistants.yakamon.service.YakadexService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.util.stream.Collectors;

@Controller
public class YakadexController {
    private YakadexService serv;

    public YakadexController(final YakadexService serv){ this.serv = serv; }

    public ParticularYakadexEntryResponse particularYakadexEntryget(String name){
        var res = this.serv.particularEntryget(name);

        if (res == null){
            return new ParticularYakadexEntryResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(),
                    null);
        }

        var entry = new AllYakadexEntriesResponse.YakadexEntry(res.name,
                res.previous,
                res.next,
                res.moveIds,
                res.typeIds,
                res.zoneIds);

        return new ParticularYakadexEntryResponse(Status.SUCCESS.getStatus(), entry);
    }

    public AllYakadexEntriesResponse allYakadexEntriesget(){
        var entries = this.serv.allEntriesget()
                        .stream()
                        .map(entry -> new AllYakadexEntriesResponse.YakadexEntry(entry.name,
                                entry.previous,
                                entry.next,
                                entry.moveIds,
                                entry.typeIds,
                                entry.zoneIds))
                        .collect(Collectors.toList());

        return new AllYakadexEntriesResponse(Status.SUCCESS.getStatus(), entries);
    }

    public void init(){
        Spark.path("/yakadex", () -> {
            Spark.get("/", (request, response) -> {
                final ObjectMapper mapper = new ObjectMapper();
                final AllYakadexEntriesResponse entries = allYakadexEntriesget();
                response.type("application/json");
                return mapper.writeValueAsString(entries);
            });

            Spark.get("/:id", (request, response) -> {
                var name = request.params(":id");
                final ObjectMapper mapper = new ObjectMapper();
                final ParticularYakadexEntryResponse entry = particularYakadexEntryget(name);
                response.type("application/json");
                return mapper.writeValueAsString(entry);
            });
        });
    }
}
