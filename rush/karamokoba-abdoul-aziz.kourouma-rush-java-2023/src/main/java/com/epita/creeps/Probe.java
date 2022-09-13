package com.epita.creeps;

import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.parameter.FireParameter;
import com.epita.creeps.given.vo.parameter.MessageParameter;
import com.epita.creeps.given.vo.parameter.NoParameter;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Probe implements Agent{
    private final ServerCommunication server;
    private final String agentId;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Probe(String agentId, ServerCommunication server) {
        this.agentId = agentId;
        this.server = server;
        logger.info("Probe created!");
    }

    public String getAgentId() {
        return agentId;
    }

    @Override
    public CommandResponse message(MessageParameter text) throws UnirestException {
        return this.server.doCommand(this.agentId, "message", Json.serialize(text));
    }

    @Override
    public CommandResponse release() throws UnirestException {
        logger.info("Probe released!");
        return this.server.doCommand(this.agentId, "release", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse move(String direction) throws UnirestException {
        return this.server.doCommand(this.agentId, "move:" + direction, Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse inspect() throws UnirestException {
        return this.server.doCommand(this.agentId, "inspect", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse unload() throws UnirestException {
        return this.server.doCommand(this.agentId, "unload", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse convert() throws UnirestException {
        return this.server.doCommand(this.agentId, "convert", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse mine(String whatToMine) throws UnirestException {
        return this.server.doCommand(this.agentId, "mine:" + whatToMine, Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse fire(FireParameter destination) throws UnirestException {
        logger.error("Probe cannot fire!");
        return null;
    }

    @Override
    public CommandResponse spawn(UnitType unitType) throws UnirestException {
        return server.doCommand(this.agentId, "spawn:" + unitType.getUnitType(), Json.serialize(NoParameter.INSTANCE));
    }
}
