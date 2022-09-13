package com.epita.creeps;

import com.epita.creeps.given.exception.NoReportException;
import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.parameter.*;
import com.epita.creeps.given.vo.report.SpawnReport;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nexus implements Structure {
    private final String agentId;
    private final ServerCommunication server;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Nexus(String agentId, ServerCommunication server){
        this.agentId = agentId;
        this.server = server;
        logger.info("Nexus created!");
    }

    public String getAgentId() {
        return agentId;
    }

    @Override
    public CommandResponse spawn(UnitType unitType) throws UnirestException {
        return server.doCommand(this.agentId, "spawn:" + unitType.getUnitType(), Json.serialize(NoParameter.INSTANCE));
    }

    public SpawnReport getReport(String reportId, double cast, double ticks) throws UnirestException {
        var report = server.getreport(reportId, cast, ticks);
        try{
            return Json.parseReport(report.getBody().toString());
        } catch (NoReportException e){
            this.logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public CommandResponse message(MessageParameter text) throws UnirestException {
        return this.server.doCommand(this.agentId, "message", Json.serialize(text));
    }

    @Override
    public CommandResponse release() throws UnirestException {
        logger.info("Nexus released!");
        return this.server.doCommand(this.agentId, "release", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse inspect(String direction) throws UnirestException {
        return this.server.doCommand(this.agentId, "inspect", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse fire(FireParameter destination) {
        this.logger.error("Nexus cannot fire!");
        return null;
    }

    @Override
    public CommandResponse teleport(TeleportParameter body) throws UnirestException {
        this.logger.error("Nexus cannot teleport!");
        return null;
    }

    @Override
    public CommandResponse fetch() throws UnirestException {
        return this.server.doCommand(this.agentId, "fetch", Json.serialize(NoParameter.INSTANCE));
    }

    @Override
    public CommandResponse credit(CreditParameter creditparam) throws UnirestException {
        return this.server.doCommand(this.agentId, "credit", Json.serialize(creditparam));
    }
}
