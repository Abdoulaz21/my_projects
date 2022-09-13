package com.epita.creeps;

import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.parameter.NoParameter;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.epita.creeps.given.vo.response.InitResponse;
import com.epita.creeps.given.vo.response.StatisticsResponse;
import com.epita.creeps.given.vo.response.StatusResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ServerCommunication {
    private final String uri;
    private final String login;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServerCommunication(String uri, String login){
        this.uri = uri;
        this.login = login;
    }

    public StatusResponse getstatus() throws UnirestException {
        String completeUri = this.uri + "status/";
        StatusResponse statusResponse = null;
        boolean hasGameBegun = false;
        while (!hasGameBegun) {
            var future = CompletableFuture.supplyAsync(() -> {
                try {
                    return Unirest.get(completeUri)
                            .asJson();
                } catch (UnirestException e) {
                    logger.error(e.getMessage());
                    return null;
                }
            }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
            HttpResponse<JsonNode> futureResponse = null;
            try {
                futureResponse = future.get();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            } catch (ExecutionException e) {
                logger.error(e.getMessage());
            }
            statusResponse = Json.parse(futureResponse.getBody().toString(), StatusResponse.class);
            hasGameBegun = statusResponse.running;
        }
        logger.info(statusResponse.toString());
        return statusResponse;
    }

    public StatisticsResponse getstatistics() throws UnirestException {
        try {
            String completeUri = this.uri + "statistics/";
            var statistics = Unirest.get(completeUri)
                    .asJson();
            var statisticsResponse = Json.parse(statistics.getBody().toString(), StatisticsResponse.class);
            return statisticsResponse;
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public InitResponse connectToServer() throws UnirestException {
        try {
            var init = Unirest.post(uri + "init/" + this.login)
                          .body(Json.serialize(NoParameter.INSTANCE))
                          .asJson();
            var initResponse = Json.parse(init.getBody().toString(), InitResponse.class);
            logger.info(initResponse.toString());
            return initResponse;
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public CommandResponse doCommand(String agentId , String opcode, String body) throws UnirestException {
        String completeUri = this.uri + "command/" +
                this.login + "/" +
                agentId + "/" + opcode;
        try {
            var command = Unirest.post(completeUri)
                                                        .body(body)
                                                        .asJson();
            var commandResponse = Json.parse(command.getBody().toString(), CommandResponse.class);
            logger.info(commandResponse.toString());
            return commandResponse;
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public HttpResponse<JsonNode> getreport(String reportId, double cast, double ticks) throws UnirestException {
        String completeUri = this.uri + "report/" + reportId;
        var report = CompletableFuture.supplyAsync(() ->
        {
            try {
                return Unirest.get(completeUri).asJson();
            } catch (UnirestException e) {
                logger.error(e.getMessage());
            }
            return null;
        }, CompletableFuture.delayedExecutor((long) (cast/ ticks), TimeUnit.SECONDS));
        try {
            var res = report.get();
            logger.info(res.getBody().toString());
            return res;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } catch (ExecutionException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}