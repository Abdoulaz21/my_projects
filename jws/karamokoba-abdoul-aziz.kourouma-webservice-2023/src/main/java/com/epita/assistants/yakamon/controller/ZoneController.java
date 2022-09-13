package com.epita.assistants.yakamon.controller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.controller.dto.AllZonesResponse;
import com.epita.assistants.yakamon.controller.dto.CurrentYakamonsResponse;
import com.epita.assistants.yakamon.controller.dto.ParticularZoneResponse;
import com.epita.assistants.yakamon.controller.dto.PossibleYakamonsResponse;
import com.epita.assistants.yakamon.misc.Error;
import com.epita.assistants.yakamon.misc.Status;
import com.epita.assistants.yakamon.service.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.util.stream.Collectors;

@Controller
public class ZoneController {
    private ZoneService serv;
    public ZoneController(final ZoneService serv){
        this.serv = serv;
    }

    public ParticularZoneResponse particularZoneget(final String name){
        var res = this.serv.particularZoneget(name);
        if (res == null)
            return new ParticularZoneResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(),
                    null);
        return new ParticularZoneResponse(Status.SUCCESS.getStatus(), res.name);
    }

    public AllZonesResponse allZonesget(){
        var res = this.serv.allZonesget()
                                       .stream()
                                       .map(zone -> zone.name)
                                       .collect(Collectors.toList());
        return new AllZonesResponse(Status.SUCCESS.getStatus(), res);
    }

    public CurrentYakamonsResponse currentYakamonsget(String name){
        var res = this.serv.currentYakamonsget(name);

        if (res == null){
            return new CurrentYakamonsResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(),
                    null);
        }

        var newres = res.stream()
                                    .map(tmp -> tmp.yakamon_id)
                                    .collect(Collectors.toList());

        return new CurrentYakamonsResponse(Status.SUCCESS.getStatus(), newres);
    }

    public PossibleYakamonsResponse possibleYakamonsget(String name){
        var res = this.serv.possibleYakamonsget(name);

        if (res == null){
            return new PossibleYakamonsResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(),
                    null);
        }

        var newres = res.stream()
                .map(tmp -> tmp.yakadex_id)
                .collect(Collectors.toList());

        return new PossibleYakamonsResponse(Status.SUCCESS.getStatus(), newres);
    }

    public void init(){
        Spark.path("/zone", () -> {
            Spark.get("/:name", (request, response) -> {
                var name = request.params(":name");
                final ObjectMapper mapper = new ObjectMapper();
                final ParticularZoneResponse zz = particularZoneget(name);
                response.type("application/json");
                return mapper.writeValueAsString(zz);
            });

            Spark.get("/", (request, response) -> {
                final ObjectMapper mapper = new ObjectMapper();
                final AllZonesResponse zz = allZonesget();
                response.type("application/json");
                return mapper.writeValueAsString(zz);
            });

            Spark.get("/:name/currentYakamons", (request, response) -> {
                var name = request.params(":name");
                final ObjectMapper mapper = new ObjectMapper();
                final CurrentYakamonsResponse cc = currentYakamonsget(name);
                response.type("application/json");
                return mapper.writeValueAsString(cc);
            });

            Spark.get("/:name/possibleYakamons", (request, response) -> {
                var name = request.params(":name");
                final ObjectMapper mapper = new ObjectMapper();
                final PossibleYakamonsResponse pp = possibleYakamonsget(name);
                response.type("application/json");
                return mapper.writeValueAsString(pp);
            });
        });
    }
}
