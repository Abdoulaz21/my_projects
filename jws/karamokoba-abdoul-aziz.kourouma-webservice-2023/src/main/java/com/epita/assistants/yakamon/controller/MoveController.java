package com.epita.assistants.yakamon.controller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.controller.dto.AllMovesResponse;
import com.epita.assistants.yakamon.controller.dto.ParticularMoveResponse;
import com.epita.assistants.yakamon.misc.Error;
import com.epita.assistants.yakamon.misc.Status;
import com.epita.assistants.yakamon.service.MoveService;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.util.stream.Collectors;

@Controller
public class MoveController {
    private MoveService serv;
    public MoveController(MoveService serv){ this.serv = serv; }

    public ParticularMoveResponse particularMoveget(final String name){
        var res = this.serv.particularMoveget(name);
        if (res == null)
            return new ParticularMoveResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(),
                    null);
        return new ParticularMoveResponse(Status.SUCCESS.getStatus(), res.name);
    }

    public AllMovesResponse allMovesget(){
        var res = this.serv.allMovesget()
                                       .stream()
                                       .map(move -> move.name)
                                       .collect(Collectors.toList());
        return new AllMovesResponse("SUCCESS", res);
    }

    public void init(){
        Spark.path("/move", () -> {
            Spark.get("/:name", (request, response) -> {
                var name = request.params(":name");
                final ObjectMapper mapper = new ObjectMapper();
                final ParticularMoveResponse mm = particularMoveget(name);
                response.type("application/json");
                return mapper.writeValueAsString(mm);
            });

            Spark.get("/", (request, response) -> {
                final ObjectMapper mapper = new ObjectMapper();
                final AllMovesResponse mm = allMovesget();
                response.type("application/json");
                return mapper.writeValueAsString(mm);
            });
        });
    }
}
