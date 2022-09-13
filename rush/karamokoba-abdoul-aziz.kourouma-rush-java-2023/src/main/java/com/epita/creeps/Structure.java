package com.epita.creeps;

import com.epita.creeps.given.vo.parameter.CreditParameter;
import com.epita.creeps.given.vo.parameter.FireParameter;
import com.epita.creeps.given.vo.parameter.MessageParameter;
import com.epita.creeps.given.vo.parameter.TeleportParameter;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface Structure {
    public CommandResponse message(MessageParameter text) throws UnirestException;
    public CommandResponse release() throws UnirestException;
    public CommandResponse inspect(String direction) throws UnirestException;
    public CommandResponse fire(FireParameter destination) throws UnirestException;
    public CommandResponse spawn(UnitType unitType) throws UnirestException;
    public CommandResponse teleport(TeleportParameter body) throws UnirestException;
    public CommandResponse fetch() throws UnirestException;
    public CommandResponse credit(CreditParameter creditparam) throws UnirestException;
}
