package com.epita.assistants.yakamon.controller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.controller.dto.AllTypesResponse;
import com.epita.assistants.yakamon.controller.dto.ParticularTypeResponse;
import com.epita.assistants.yakamon.misc.Error;
import com.epita.assistants.yakamon.misc.Status;
import com.epita.assistants.yakamon.service.TypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.util.stream.Collectors;

@Controller
public class TypeController {
    final TypeService serv;
    public TypeController(final TypeService serv){
        this.serv = serv;
    }

    public ParticularTypeResponse particularTypeget(final String name){
        var res = this.serv.particularTypeget(name);
        if (res == null)
            return new ParticularTypeResponse(Status.FAILURE.getStatus(),
                                            Error.OBJECT_NOT_FOUND.getErrorCode(),
                                            Error.OBJECT_NOT_FOUND.getErrorMessage());
        return new ParticularTypeResponse(Status.SUCCESS.getStatus(), res.name);
    }

    public AllTypesResponse allTypesget(){
        var res = this.serv.allTypesget()
                                            .stream()
                                            .map(type -> type.name)
                                            .collect(Collectors.toList());
        return new AllTypesResponse(Status.SUCCESS.getStatus(), res);
    }

    public void init(){
        Spark.path("/type", () -> {
            Spark.get("/:name", (request, response) -> {
                var name = request.params(":name");
                final ObjectMapper mapper = new ObjectMapper();
                final ParticularTypeResponse tt = particularTypeget(name);
                response.type("application/json");
                return mapper.writeValueAsString(tt);
            });

            Spark.get("/", (request, response) -> {
                final ObjectMapper mapper = new ObjectMapper();
                final AllTypesResponse tt = allTypesget();
                response.type("application/json");
                return mapper.writeValueAsString(tt);
            });
        });
    }
}
