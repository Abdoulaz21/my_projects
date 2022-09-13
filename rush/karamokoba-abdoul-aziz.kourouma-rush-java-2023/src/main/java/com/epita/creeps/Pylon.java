package com.epita.creeps;

import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.parameter.*;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pylon implements Structure {
    private final String agentId;
    private final ServerCommunication server;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Pylon(String agentId, ServerCommunication server){
        this.agentId = agentId;
        this.server = server;
        logger.info("Pylon created!");
    }

    public String getAgentId() {
        return agentId;
    }

    public CommandResponse teleport(TeleportParameter body) throws UnirestException {
        return this.server.doCommand(this.agentId, "teleport", Json.serialize(body));
    }

    @Override
    public CommandResponse fetch() throws UnirestException {
        this.logger.error("Pylon cannot fetch!");
        return null;
    }

    @Override
    public CommandResponse credit(CreditParameter creditparam) throws UnirestException {
        this.logger.error("Pylon cannot credit!");
        return null;
    }

    @Override
    public CommandResponse message(MessageParameter text) throws UnirestException {
        return this.server.doCommand(this.agentId, "message", Json.serialize(text));
    }

    @Override
    public CommandResponse release() throws UnirestException {
        logger.info("Pylon released!");
        return this.server.doCommand(this.agentId, "release", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse inspect(String direction) throws UnirestException {
        return this.server.doCommand(this.agentId, "inspect", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse fire(FireParameter destination) throws UnirestException {
        this.logger.error("Photon Cannon cannot fire!");
        return null;
    }

    @Override
    public CommandResponse spawn(UnitType unitType) throws UnirestException {
        this.logger.error("Photon Cannon cannot spawn!");
        return null;
    }
}
