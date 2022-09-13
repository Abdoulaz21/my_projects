package com.epita.creeps;

import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.parameter.FireParameter;
import com.epita.creeps.given.vo.parameter.MessageParameter;
import com.epita.creeps.given.vo.parameter.NoParameter;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Observer implements Agent{
    private final ServerCommunication server;
    private final String agentId;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Observer(String agentId, ServerCommunication server){
        this.agentId = agentId;
        this.server = server;
        logger.info("Observer created!");
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
        logger.info("Observer released!");
        return this.server.doCommand(this.agentId, "release", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse move(String direction) throws UnirestException {
        return this.server.doCommand(this.agentId, "move:" + direction, Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse fire(FireParameter destination) throws UnirestException {
        logger.error("Observer cannot fire!");
        return null;
    }

    @Override
    public CommandResponse spawn(UnitType unitType) throws UnirestException {
        this.logger.error("Observer cannot spawn!");
        return null;
    }

    @Override
    public CommandResponse inspect() throws UnirestException {
        return this.server.doCommand(this.agentId, "inspect", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse unload() throws UnirestException {
        this.logger.error("Observer cannot unload!");
        return null;
    }

    @Override
    public CommandResponse convert() throws UnirestException {
        return this.server.doCommand(this.agentId, "convert", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse mine(String whatToMine) throws UnirestException {
        this.logger.error("Dragoon cannot mine!");
        return null;
    }
}