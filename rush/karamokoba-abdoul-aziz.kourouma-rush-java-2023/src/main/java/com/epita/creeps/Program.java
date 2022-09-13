package com.epita.creeps;

import com.epita.creeps.given.json.Json;
import com.epita.creeps.given.vo.parameter.FireParameter;
import com.epita.creeps.given.vo.parameter.MessageParameter;
import com.epita.creeps.given.vo.parameter.TeleportParameter;
import com.epita.creeps.given.vo.report.*;
import com.epita.creeps.given.vo.response.CommandResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Program {

    public static void main(String[] args) throws UnirestException {
        String myuri = "http://" + args[0] + ":" + args[1] + "/";
        String myname = args[2];
        var serverConnection = new ServerCommunication(myuri, myname);

        var init = serverConnection.connectToServer();

        var status = serverConnection.getstatus();

        //Nexus
        Nexus base = new Nexus(init.baseId, serverConnection);

        //Observer
        /*var spawnObserver = base.spawn(UnitType.Dragoon);
        var spawnObserverreport = serverConnection.getreport(spawnObserver.reportId,
                init.costs.spawnDragoon.cast,
                init.setup.ticksPerSeconds);

        var spawnObserverreportResponse = Json.parse(spawnObserverreport.getBody().toString(), SpawnReport.class);

        Dragoon obs1 = new Dragoon(spawnObserverreportResponse.spawnedAgentId, serverConnection);

        spawnObserver = base.spawn(UnitType.Dragoon);
        spawnObserverreport = serverConnection.getreport(spawnObserver.reportId,
                init.costs.spawnDragoon.cast,
                init.setup.ticksPerSeconds);

        spawnObserverreportResponse = Json.parse(spawnObserverreport.getBody().toString(), SpawnReport.class);

        Dragoon obs2 = new Dragoon(spawnObserverreportResponse.spawnedAgentId, serverConnection);*/

        /*
        var spawndragoon = base.spawn(UnitType.Dragoon);
        var spawndragoonreport = serverConnection.getreport(spawndragoon.reportId,
                                                                        init.costs.spawnDragoon.cast,
                                                                        init.setup.ticksPerSeconds);

        var spawndragoonreportResponse = Json.parse(spawndragoonreport.getBody().toString(), SpawnReport.class);

        //Dragoon
        Dragoon dracarys = new Dragoon(spawndragoonreportResponse.spawnedAgentId, serverConnection);

        int x = 0;
        int y = 1;
        int z = 0;
        FireParameter destination = new FireParameter(spawndragoonreportResponse.spawnedAgentLocation.plus(x, y, z));

        var movedracarys = dracarys.move("up");
        var movedracarysreport = serverConnection.getreport(movedracarys.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //var movedracarysReportResponse = Json.parse(movedracarysreport.getBody().toString(), SpawnReport.class);

        var firedracarys = dracarys.fire(destination);
        var firedracarysreport = serverConnection.getreport(firedracarys.reportId,
                init.costs.fireDragoon.cast,
                init.setup.ticksPerSeconds);
        //var firedracarysReportResponse = Json.parse(firedracarysreport.getBody().toString(), SpawnReport.class);

        movedracarys = dracarys.move("up");
        movedracarysreport = serverConnection.getreport(movedracarys.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //movedracarysReportResponse = Json.parse(movedracarysreport.getBody().toString(), SpawnReport.class);

        movedracarys = dracarys.move("east");
        movedracarysreport = serverConnection.getreport(movedracarys.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //movedracarysReportResponse = Json.parse(movedracarysreport.getBody().toString(), SpawnReport.class);

        movedracarys = dracarys.move("east");
        movedracarysreport = serverConnection.getreport(movedracarys.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //movedracarysReportResponse = Json.parse(movedracarysreport.getBody().toString(), SpawnReport.class);

        movedracarys = dracarys.move("up");
        movedracarysreport = serverConnection.getreport(movedracarys.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //movedracarysReportResponse = Json.parse(movedracarysreport.getBody().toString(), SpawnReport.class);

        firedracarys = dracarys.fire(destination);
        firedracarysreport = serverConnection.getreport(firedracarys.reportId,
                init.costs.fireDragoon.cast,
                init.setup.ticksPerSeconds);
        //firedracarysReportResponse = Json.parse(firedracarysreport.getBody().toString(), SpawnReport.class);

        //dracarys.release();
        */

        //Probe Samy
        Probe samy = new Probe(init.probeId, serverConnection);

        var moveSamy = samy.move("north");
        var moveSamyResponse = serverConnection.getreport(moveSamy.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //var moveSamyReportResponse = Json.parse(moveSamyResponse.getBody().toString(), MoveReport.class);

        var spawnpylon = samy.spawn(UnitType.Pylon);
        var spawnpylonResponse = serverConnection.getreport(spawnpylon.reportId,
                init.costs.spawnPylon.cast,
                init.setup.ticksPerSeconds);
        var spawnpylonResponseReport = Json.parse(spawnpylonResponse.getBody().toString(), SpawnReport.class);

        Pylon pylon1 = new Pylon(spawnpylonResponseReport.spawnedAgentId, serverConnection);
/*
        MessageParameter text = new MessageParameter("Trying to contact other players");
        var messageSamy = samy.message(text);
        var messageSamyResponse = serverConnection.getreport(messageSamy.reportId,
                init.costs.message.cast,
                init.setup.ticksPerSeconds);
        //var messageSamyResponseReport = Json.parse(messageSamyResponse.getBody().toString(), MessageReport.class);

        var fetchBase = base.fetch();
        var fetchBaseResponse = serverConnection.getreport(fetchBase.reportId,
                init.costs.fetch.cast,
                init.setup.ticksPerSeconds);
        var fetchBaseResponseReport = Json.parse(fetchBaseResponse.getBody().toString(), FetchReport.class);
        LoggerFactory.getLogger(fetchBase.getClass()).info("Messages: " + fetchBaseResponseReport.messages.toString());
*/
        var inspectSamy = samy.inspect();
        var inspectSamyResponse = serverConnection.getreport(inspectSamy.reportId,
                init.costs.inspect.cast,
                init.setup.ticksPerSeconds);
        var inspectSamyReportResponse = Json.parse(inspectSamyResponse.getBody().toString(), InspectReport.class);

        String toMine = null;
        if (inspectSamyReportResponse.biomass > inspectSamyReportResponse.minerals){
            if (inspectSamyReportResponse.biomass > 0)
                toMine = "biomass";
        } else {
            if (inspectSamyReportResponse.minerals > 0)
                toMine = "minerals";
        }

        int i = 0;
        while (i != 5 && toMine == null) {
            inspectSamy = samy.inspect();
            inspectSamyResponse = serverConnection.getreport(inspectSamy.reportId,
                    init.costs.inspect.cast,
                    init.setup.ticksPerSeconds);
            inspectSamyReportResponse = Json.parse(inspectSamyResponse.getBody().toString(), InspectReport.class);

            if (inspectSamyReportResponse.biomass > inspectSamyReportResponse.minerals){
                if (inspectSamyReportResponse.biomass > 0)
                    toMine = "biomass";
            } else {
                if (inspectSamyReportResponse.minerals > 0)
                    toMine = "minerals";
            }

            moveSamy = samy.move("east");
            if (moveSamy.causeOfDeath == "null"){
                LoggerFactory.getLogger(samy.getClass()).info("Samy is dead!");
                break;
            }
            moveSamyResponse = serverConnection.getreport(moveSamy.reportId,
                    init.costs.move.cast,
                    init.setup.ticksPerSeconds);
            //moveSamyReportResponse = Json.parse(moveSamyResponse.getBody().toString(), MoveReport.class);
            i = i + 1;

        }

        if (toMine != null){
            var mineSamy = samy.mine(toMine);
            if (mineSamy.causeOfDeath == "null"){
                LoggerFactory.getLogger(samy.getClass()).info("Samy is dead!");
            }
            else {
                var mineSamyResponse = serverConnection.getreport(mineSamy.reportId,
                        init.costs.mine.cast,
                        init.setup.ticksPerSeconds);
                //var mineSamyResponseReport = Json.parse(mineSamyResponse.getBody().toString(), MineReport.class);

                while (i != 0){
                    moveSamy = samy.move("west");
                    moveSamyResponse = serverConnection.getreport(moveSamy.reportId,
                            init.costs.move.cast,
                            init.setup.ticksPerSeconds);
                    //moveSamyReportResponse = Json.parse(moveSamyResponse.getBody().toString(), MoveReport.class);
                    i = i - 1;

                    var convertSamy = samy.convert();
                    moveSamyResponse = serverConnection.getreport(convertSamy.reportId,
                            init.costs.convert.cast,
                            init.setup.ticksPerSeconds);
                }
                //unload
                var unloadSamy = samy.unload();
                var unloadSamyResponse = serverConnection.getreport(unloadSamy.reportId,
                        init.costs.unload.cast,
                        init.setup.ticksPerSeconds);
                //var unloadSamyResponseReport = Json.parse(unloadSamyResponse.getBody().toString(), UnloadReport.class);
            }
        }

        spawnpylon = samy.spawn(UnitType.Pylon);
        spawnpylonResponse = serverConnection.getreport(spawnpylon.reportId,
                init.costs.spawnPylon.cast,
                init.setup.ticksPerSeconds);
        spawnpylonResponseReport = Json.parse(spawnpylonResponse.getBody().toString(), SpawnReport.class);

        Pylon pylon2 = new Pylon(spawnpylonResponseReport.spawnedAgentId, serverConnection);

        TeleportParameter telparam = new TeleportParameter(samy.getAgentId(), pylon1.getAgentId());
        LoggerFactory.getLogger(ServerCommunication.class).info( "Body du teleport"  + telparam.toString());
        var teleportSamy = pylon2.teleport(telparam);
        var teleportSamyResponse = serverConnection.getreport(teleportSamy.reportId,
                    init.costs.teleport.cast,
                    init.setup.ticksPerSeconds);
/*
        var spawnPhoton = samy.spawn(UnitType.PhotonCannon);
        var spawnPhotonResponse = serverConnection.getreport(spawnPhoton.reportId,
                init.costs.spawnPhotonCannon.cast,
                init.setup.ticksPerSeconds);
        var spawnPhotonResponseReport = Json.parse(spawnPhotonResponse.getBody().toString(), SpawnReport.class);

        PhotonCannon photon = new PhotonCannon(spawnPhotonResponseReport.spawnedAgentId, serverConnection);

        int x = 0;
        int y = 2;
        int z = 0;
        FireParameter destination2 = new FireParameter(spawnPhotonResponseReport.spawnedAgentLocation.plus(x, y, z));
        var firephoton = photon.fire(destination2);

        var firephotonreport = serverConnection.getreport(firephoton.reportId,
                init.costs.firePhotonCannon.cast,
                init.setup.ticksPerSeconds);
        //firedracarysReportResponse = Json.parse(firedracarysreport.getBody().toString(), SpawnReport.class);
*/
        var moveSouthSamy = samy.move("south");
        var moveSoouthSamyResponse = serverConnection.getreport(moveSouthSamy.reportId,
                init.costs.move.cast,
                init.setup.ticksPerSeconds);
        //var moveSamyReportResponse = Json.parse(moveSamyResponse.getBody().toString(), MoveReport.class);

        var unloadSamy = samy.unload();
        var unloadSamyResponse = serverConnection.getreport(unloadSamy.reportId,
                init.costs.unload.cast,
                init.setup.ticksPerSeconds);
        //var unloadSamyResponseReport = Json.parse(unloadSamyResponse.getBody().toString(), UnloadReport.class);

        //Probe Quentin
/*
        var spawnprobe2 = base.spawn(UnitType.Probe);
        var spawnprobe2Report = serverConnection.getreport(spawnprobe2.reportId,
                init.costs.spawnProbe.cast,
                init.setup.ticksPerSeconds);
        var spawnprobe2ReportResponse = Json.parse(spawnprobe2Report.getBody().toString(), SpawnReport.class);

        Probe quentin = new Probe(spawnprobe2ReportResponse.spawnedAgentId, serverConnection);

        ArrayList<String> directions = new ArrayList<>();
        directions.add("north");
        directions.add("north");
        directions.add("east");
        directions.add("east");
        directions.add("east");
        directions.add("south");
        directions.add("south");
        directions.add("west");
        directions.add("west");
        directions.add("west");

        CommandResponse moveQuentin;
        HttpResponse<JsonNode> moveQuentinResponse;

        CommandResponse inspectQuentin;
        HttpResponse<JsonNode> inspectQuentinResponse;
        InspectReport inspectQuentinReportResponse;

        CommandResponse mineQuentin;
        HttpResponse<JsonNode> mineQuentinResponse;
        MineReport mineQuentinResponseReport;

        for (var x : directions) {
            moveQuentin = quentin.move(x);
            moveQuentinResponse = serverConnection.getreport(moveQuentin.reportId,
                    init.costs.move.cast,
                    init.setup.ticksPerSeconds);

            inspectQuentin = quentin.inspect();
            inspectQuentinResponse = serverConnection.getreport(inspectQuentin.reportId,
                    init.costs.inspect.cast,
                    init.setup.ticksPerSeconds);
            inspectQuentinReportResponse = Json.parse(inspectQuentinResponse.getBody().toString(), InspectReport.class);

            if(inspectQuentinReportResponse.minerals > 0){
                mineQuentin = quentin.mine("minerals");
                mineQuentinResponse = serverConnection.getreport(mineQuentin.reportId,
                        init.costs.mine.cast,
                        init.setup.ticksPerSeconds);
                mineQuentinResponseReport = Json.parse(mineQuentinResponse.getBody().toString(), MineReport.class);
            }
        }

        var unloadQuentin = quentin.unload();
        var unloadQuentinResponse = serverConnection.getreport(unloadQuentin.reportId,
                init.costs.unload.cast,
                init.setup.ticksPerSeconds);

        //Probe Zizou
        var spawnprobe3 = base.spawn(UnitType.Probe);
        var spawnprobe3Report = serverConnection.getreport(spawnprobe3.reportId,
                init.costs.spawnProbe.cast,
                init.setup.ticksPerSeconds);
        var spawnprobe3ReportResponse = Json.parse(spawnprobe3Report.getBody().toString(), SpawnReport.class);

        Probe zizou = new Probe(spawnprobe2ReportResponse.spawnedAgentId, serverConnection);

        ArrayList<String> directions2 = new ArrayList<>();
        directions2.add("east");
        directions2.add("north");
        directions2.add("east");
        directions2.add("north");
        directions2.add("east");
        directions2.add("south");
        directions2.add("west");
        directions2.add("west");
        directions2.add("south");
        directions2.add("west");

        CommandResponse moveZizou;
        HttpResponse<JsonNode> moveZizouResponse;

        CommandResponse inspectZizou;
        HttpResponse<JsonNode> inspectZizouResponse;
        InspectReport inspectZizouReportResponse;

        CommandResponse mineZizou;
        HttpResponse<JsonNode> mineZizouResponse;
        MineReport mineZizouResponseReport;

        for (var x : directions2) {
            moveZizou = zizou.move(x);
            moveZizouResponse = serverConnection.getreport(moveZizou.reportId,
                    init.costs.move.cast,
                    init.setup.ticksPerSeconds);

            inspectZizou = zizou.inspect();
            inspectZizouResponse = serverConnection.getreport(inspectZizou.reportId,
                    init.costs.inspect.cast,
                    init.setup.ticksPerSeconds);
            inspectZizouReportResponse = Json.parse(inspectZizouResponse.getBody().toString(), InspectReport.class);

            if(inspectZizouReportResponse.minerals > 0){
                mineZizou = zizou.mine("minerals");
                mineZizouResponse = serverConnection.getreport(mineZizou.reportId,
                        init.costs.mine.cast,
                        init.setup.ticksPerSeconds);
                mineZizouResponseReport = Json.parse(mineZizouResponse.getBody().toString(), MineReport.class);
                LoggerFactory.getLogger(zizou.getClass()).info("Payload of Zizou: " + mineZizouResponseReport.payload.toString());
            }
        }

        var unloadZizou = zizou.unload();
        var unloadZizouResponse = serverConnection.getreport(unloadZizou.reportId,
                init.costs.unload.cast,
                init.setup.ticksPerSeconds);

        var spawnprobe4 = base.spawn(UnitType.Probe);
        var spawnprobe4Report = serverConnection.getreport(spawnprobe4.reportId,
                init.costs.spawnProbe.cast,
                init.setup.ticksPerSeconds);
        var spawnprobe4ReportResponse = Json.parse(spawnprobe4Report.getBody().toString(), SpawnReport.class);

        Probe inconnu = new Probe(spawnprobe4ReportResponse.spawnedAgentId, serverConnection);

        var spawnprobe5 = base.spawn(UnitType.Probe);
        var spawnprobe5Report = serverConnection.getreport(spawnprobe5.reportId,
                init.costs.spawnProbe.cast,
                init.setup.ticksPerSeconds);
        var spawnprobe5ReportResponse = Json.parse(spawnprobe5Report.getBody().toString(), SpawnReport.class);

        Probe inconnu2 = new Probe(spawnprobe5ReportResponse.spawnedAgentId, serverConnection);

        var releaseinconnu2 = inconnu2.release();
        var releaseinconu2Response = serverConnection.getreport(releaseinconnu2.reportId,
                init.costs.release.cast,
                init.setup.ticksPerSeconds);

        var releaseinconnu = inconnu.release();
        var releaseinconuResponse = serverConnection.getreport(releaseinconnu.reportId,
                init.costs.release.cast,
                init.setup.ticksPerSeconds);

        var release = zizou.release();
        var releaseResponse = serverConnection.getreport(release.reportId,
                init.costs.release.cast,
                init.setup.ticksPerSeconds);

        release = quentin.release();
        releaseResponse = serverConnection.getreport(release.reportId,
                init.costs.release.cast,
                init.setup.ticksPerSeconds);*/

        var release2 = samy.release();
        var releaseResponse2 = serverConnection.getreport(release2.reportId,
                init.costs.release.cast,
                init.setup.ticksPerSeconds);

        /*MessageParameter bye = new MessageParameter("Bye everyone!");
        for (int i = 0;i < 1500;i++) {
            var byemessage = base.message(bye);
            var byemessageResponse = serverConnection.getreport(byemessage.reportId,
                    init.costs.message.cast,
                    init.setup.ticksPerSeconds);
        }
        fetchBase = base.fetch();
        fetchBaseResponse = serverConnection.getreport(fetchBase.reportId,
                init.costs.fetch.cast,
                init.setup.ticksPerSeconds);
        //fetchBaseResponseReport = Json.parse(fetchBaseResponse.getBody().toString(), FetchReport.class);
        */
        /*var releasenexus = Nexus2.release();
        var releasenexusResponse = serverConnection.getreport(releasenexus.reportId,
                init.costs.release.cast,
                init.setup.ticksPerSeconds);*/

        base.release();
    }
}
