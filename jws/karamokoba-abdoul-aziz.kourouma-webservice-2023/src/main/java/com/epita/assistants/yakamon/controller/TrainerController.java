package com.epita.assistants.yakamon.controller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.controller.dto.*;
import com.epita.assistants.yakamon.misc.Error;
import com.epita.assistants.yakamon.misc.Status;
import com.epita.assistants.yakamon.repository.model.TrainerModel;
import com.epita.assistants.yakamon.service.TrainerService;
import com.epita.assistants.yakamon.service.entity.TrainerEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class TrainerController {
    private TrainerService serv;

    public TrainerController(TrainerService serv){ this.serv = serv; }

    public AllTrainersResponse allTrainers(){
        var res = this.serv.allTrainersget()
                .stream()
                .map(tr -> new AllTrainersResponse.ParticularTrainer(tr.name, tr.uuid))
                .collect(Collectors.toList());
        return new AllTrainersResponse(Status.SUCCESS.getStatus(),
                null,
                null,
                res);
    }

    public ParticularTrainerResponse particularTrainer(UUID uuid){
        var res = this.serv.particularTrainerget(uuid);

        if (res == null){
            return new ParticularTrainerResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(),
                    null);
        }

        return new ParticularTrainerResponse(Status.SUCCESS.getStatus(),
                null, null,
                new AllTrainersResponse.ParticularTrainer(res.name, res.uuid));
    }

    public CreateTrainerResponse createTrainer(CreateTrainerRequest req){
        var res = this.serv.createTrainer(UUID.fromString(req.uuid), req.name);

        if (res == null){
            return new CreateTrainerResponse(Status.FAILURE.getStatus(),
                    Error.CREATION_FAILED.getErrorCode(),
                    Error.CREATION_FAILED.getErrorMessage(),
                    null);
        }

        return new CreateTrainerResponse(Status.SUCCESS.getStatus(),
                null, null,
                new AllTrainersResponse.ParticularTrainer(res.name, res.uuid));
    }

    public RenameTrainerResponse renameTrainer(UUID uuid, String name){
        var res = this.serv.renameTrainer(uuid, name);

        if (!res){
            return new RenameTrainerResponse(Status.FAILURE.getStatus(),
                    Error.UPDATE_FAILED.getErrorCode(),
                    Error.UPDATE_FAILED.getErrorMessage());
        }

        return new RenameTrainerResponse(Status.SUCCESS.getStatus(), null, null);
    }

    public DeleteTrainerResponse deleteTrainer(UUID uuid){
        var res = this.serv.deleteTrainer(uuid);

        if (!res){
            return new DeleteTrainerResponse(Status.FAILURE.getStatus(),
                    Error.DELETION_FAILED.getErrorCode(),
                    Error.DELETION_FAILED.getErrorMessage());
        }

        return new DeleteTrainerResponse(Status.SUCCESS.getStatus(), null, null);
    }

    public AllYakamonsOfTrainerResponse allYakamonsOfTrainer(UUID uuid){
        var res = this.serv.yakamonsOfTrainerget(uuid);

        if (res == null){
            return new AllYakamonsOfTrainerResponse(Status.FAILURE.getStatus(),
                    Error.OBJECT_NOT_FOUND.getErrorCode(),
                    Error.OBJECT_NOT_FOUND.getErrorMessage(), null);
        }

        return new AllYakamonsOfTrainerResponse(Status.SUCCESS.getStatus(),
                null, null, res);
    }

    public AddYakamonToTrainerResponse addYakamonToTrainer(UUID uuid, UUID yakId){
        var res = this.serv.addYakamonToTrainer(uuid, yakId);

        if (!res){
            return new AddYakamonToTrainerResponse(Status.FAILURE.getStatus(),
                    Error.CREATION_FAILED.getErrorCode(),
                    Error.CREATION_FAILED.getErrorMessage());
        }

        return new AddYakamonToTrainerResponse(Status.SUCCESS.getStatus(), null, null);
    }

    public FreeYakamonOfTrainerInZoneResponse freeYakamonOfTrainerInZone(UUID uuid, UUID yakId, String zone){
        var res = this.serv.deleteTrainer(uuid);

        if (!res){
            return new FreeYakamonOfTrainerInZoneResponse(Status.FAILURE.getStatus(),
                    Error.CREATION_FAILED.getErrorCode(),
                    Error.CREATION_FAILED.getErrorMessage());
        }

        return new FreeYakamonOfTrainerInZoneResponse(Status.SUCCESS.getStatus(), null, null);
    }

    public void init(){
        Spark.path("/trainer", () -> {
            Spark.get("/", (request, response) -> {
                final ObjectMapper mapper = new ObjectMapper();
                final AllTrainersResponse res = allTrainers();
                response.type("application/json");
                return mapper.writeValueAsString(res);
            });
            Spark.get("/:uuid", (request, response) -> {
                UUID uuid = UUID.fromString(request.params(":uuid"));
                final ObjectMapper mapper = new ObjectMapper();
                final ParticularTrainerResponse res = particularTrainer(uuid);
                response.type("application/json");
                return mapper.writeValueAsString(res);
            });
            Spark.put("/", (request, response) -> {
                final ObjectMapper mapper = new ObjectMapper();
                response.type("application/json");
                try {
                    final CreateTrainerRequest req = mapper.readValue(request.body(), CreateTrainerRequest.class);
                    final CreateTrainerResponse res = createTrainer(req);
                    return mapper.writeValueAsString(res);
                } catch (Exception e){
                    LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
                    return mapper.writeValueAsString(new CreateTrainerResponse(Status.FAILURE.getStatus(),
                            Error.BAD_BODY.getErrorCode(),
                            Error.BAD_BODY.getErrorMessage(),
                            null));
                }
            });
            Spark.patch("/:uuid", (request, response) -> {
                UUID uuid = UUID.fromString(request.params(":uuid"));
                final ObjectMapper mapper = new ObjectMapper();
                response.type("application/json");
                try {
                    final RenameTrainerRequest req = mapper.readValue(request.body(), RenameTrainerRequest.class);
                    final RenameTrainerResponse res = renameTrainer(uuid, req.name);
                    return mapper.writeValueAsString(res);
                } catch (Exception e){
                    LoggerFactory.getLogger(this.getClass()).error(e.getMessage());
                    return mapper.writeValueAsString(new RenameTrainerResponse(Status.FAILURE.getStatus(),
                            Error.BAD_BODY.getErrorCode(),
                            Error.BAD_BODY.getErrorMessage()));
                }
            });
            Spark.delete("/:uuid", (request, response) -> {
                UUID uuid = UUID.fromString(request.params(":uuid"));
                final ObjectMapper mapper = new ObjectMapper();
                final DeleteTrainerResponse res = deleteTrainer(uuid);
                response.type("application/json");
                return mapper.writeValueAsString(res);
            });
            Spark.get("/:uuid/yakamons", (request, response) -> {
                UUID uuid = UUID.fromString(request.params(":uuid"));
                final ObjectMapper mapper = new ObjectMapper();
                final AllYakamonsOfTrainerResponse res = allYakamonsOfTrainer(uuid);
                response.type("application/json");
                return mapper.writeValueAsString(res);
            });
            Spark.post("/:uuid/yakamons/:yakamon", (request, response) -> {
                UUID uuid = UUID.fromString(request.params(":uuid"));
                UUID yakId = UUID.fromString(request.params(":yakamon"));
                final ObjectMapper mapper = new ObjectMapper();
                final AddYakamonToTrainerResponse res = addYakamonToTrainer(uuid, yakId);
                response.type("application/json");
                return mapper.writeValueAsString(res);
            });
            Spark.post("/:uuid/free/:yakamon/:zoneName", (request, response) -> {
                UUID uuid = UUID.fromString(request.params(":uuid"));
                UUID yakId = UUID.fromString(request.params(":yakamon"));
                String zone = request.params(":zoneName");
                final ObjectMapper mapper = new ObjectMapper();
                final FreeYakamonOfTrainerInZoneResponse res = freeYakamonOfTrainerInZone(uuid, yakId, zone);
                response.type("application/json");
                return mapper.writeValueAsString(res);
            });
        });
    }
}
