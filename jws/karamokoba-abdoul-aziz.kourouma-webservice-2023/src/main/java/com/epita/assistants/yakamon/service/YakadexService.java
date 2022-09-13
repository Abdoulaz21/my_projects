package com.epita.assistants.yakamon.service;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.repository.YakadexMovesRepository;
import com.epita.assistants.yakamon.repository.YakadexTypesRepository;
import com.epita.assistants.yakamon.repository.YakadexZonesRepository;
import com.epita.assistants.yakamon.service.entity.*;
import com.epita.assistants.yakamon.repository.model.YakadexModel;
import com.epita.assistants.yakamon.repository.model.YakadexMovesModel;
import com.epita.assistants.yakamon.repository.model.YakadexTypesModel;
import com.epita.assistants.yakamon.repository.model.YakadexZonesModel;
import com.epita.assistants.yakamon.repository.YakadexRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YakadexService {
    private final YakadexRepository yakadexrepo;
    private final YakadexZonesRepository zonerepo;
    private final YakadexMovesRepository moverepo;
    private final YakadexTypesRepository typerepo;

    public YakadexService(YakadexRepository yakadex,
                          YakadexZonesRepository zone,
                          YakadexMovesRepository move,
                          YakadexTypesRepository type){
        this.yakadexrepo = yakadex;
        this.zonerepo = zone;
        this.moverepo = move;
        this.typerepo = type;
    }

    public YakadexZonesEntity modelToEntity_zone(YakadexZonesModel model){
        return new YakadexZonesEntity(model.getId(),
                                      model.getYakadex_id(),
                                      model.getZone_id());
    }

    public List<String> YZEntitiesget(String yak_id){
        var record = this.zonerepo.YZModelsget(yak_id);

        if (record == null){ return null; }

        return record.stream()
                .map(this::modelToEntity_zone)
                .map(zone -> zone.zone_id)
                .collect(Collectors.toList());
    }

    public YakadexMovesEntity modelToEntity_move(YakadexMovesModel model){
        return new YakadexMovesEntity(model.getId(),
                model.getYakadex_id(),
                model.getMove_id());
    }

    public List<String> YMEntitiesget(String yak_id){
        var record = this.moverepo.YMModelsget(yak_id);

        if (record == null){ return null; }

        return record.stream()
                .map(this::modelToEntity_move)
                .map(move -> move.move_id)
                .collect(Collectors.toList());
    }

    public YakadexTypesEntity modelToEntity_type(YakadexTypesModel model){
        return new YakadexTypesEntity(model.getId(),
                model.getYakadex_id(),
                model.getType_id());
    }

    public List<String> YTEntitiesget(String yak_id){
        var record = this.typerepo.YTModelsget(yak_id);

        if (record == null){ return null; }

        return record.stream()
                .map(this::modelToEntity_type)
                .map(type -> type.type_id)
                .collect(Collectors.toList());
    }

    public YakadexEntity modelToEntity_yakadex(YakadexModel model){
        return new YakadexEntity(model.getName(),
                                 model.getPrevious(),
                                 model.getNext());
    }

    public YakadexEntryEntity particularEntryget(String name){
        var recordyakadex = this.yakadexrepo.particularYakadexModelget(name);

        if (recordyakadex == null){ return null; }

        var recordzone = this.YZEntitiesget(name);
        var recordmove = this.YMEntitiesget(name);
        var recordtype = this.YTEntitiesget(name);

        return new YakadexEntryEntity(recordyakadex.getName(),
                recordyakadex.getPrevious(),
                recordyakadex.getNext(),
                recordmove,
                recordtype,
                recordzone);
    }

    public List<YakadexEntryEntity> allEntriesget(){
        return this.yakadexrepo.allYakadexModelsget()
                        .stream()
                        .map(entry -> new YakadexEntryEntity(entry.getName(),
                                entry.getPrevious(),
                                entry.getNext(),
                                this.YMEntitiesget(entry.getName()),
                                this.YTEntitiesget(entry.getName()),
                                this.YZEntitiesget(entry.getName())))
                        .collect(Collectors.toList());
    }
}
