package com.epita.creeps;

import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.geometry.Point;
import com.epita.creeps.given.vo.parameter.*;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotonCannon implements Structure{
    private final String agentId;
    private final ServerCommunication server;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PhotonCannon(String agentId, ServerCommunication server){
        this.agentId = agentId;
        this.server = server;
        logger.info("Photon Cannon created!");
    }

    public String getAgentId() {
        return agentId;
    }

    @Override
    public CommandResponse fire(FireParameter destination) throws UnirestException {
        return this.server.doCommand(this.agentId, "fire:photon-cannon", Json.serialize(destination));
    }

    @Override
    public CommandResponse message(MessageParameter text) throws UnirestException {
        return this.server.doCommand(this.agentId, "message", Json.serialize(text));
    }

    @Override
    public CommandResponse release() throws UnirestException {
        logger.info("Photon Cannon released!");
        return this.server.doCommand(this.agentId, "release", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse inspect(String direction) throws UnirestException {
        return this.server.doCommand(this.agentId, "inspect", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse spawn(UnitType unitType) throws UnirestException {
        this.logger.error("Photon Cannon cannot spawn!");
        return null;
    }

    @Override
    public CommandResponse teleport(TeleportParameter body) throws UnirestException {
        this.logger.error("Photon Cannon cannot teleport!");
        return null;
    }

    @Override
    public CommandResponse fetch() throws UnirestException {
        this.logger.error("Photon Cannon cannot fetch!");
        return null;
    }

    @Override
    public CommandResponse credit(CreditParameter creditparam) throws UnirestException {
        this.logger.error("Photon Cannon cannot credit!");
        return null;
    }
}
