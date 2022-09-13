package com.epita.creeps;

import com.epita.creeps.given.vo.parameter.FireParameter;
import com.epita.creeps.given.vo.parameter.MessageParameter;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface Agent {
    public CommandResponse message(MessageParameter text) throws UnirestException;
    public CommandResponse release() throws UnirestException;
    public CommandResponse inspect() throws UnirestException;
    public CommandResponse unload() throws UnirestException;
    public CommandResponse convert() throws UnirestException;
    public CommandResponse mine(String whatToMine) throws UnirestException;
    public CommandResponse move(String direction) throws UnirestException;
    //public CommandResponse scan(String power) throws UnirestException;
    public CommandResponse fire(FireParameter destination) throws UnirestException;
    public CommandResponse spawn(UnitType unitType) throws UnirestException;
}