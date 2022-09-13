package com.epita.assistants.yakamon.service;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.repository.YakadexZonesRepository;
import com.epita.assistants.yakamon.repository.YakamonZoneRepository;
import com.epita.assistants.yakamon.service.entity.YakadexZonesEntity;
import com.epita.assistants.yakamon.service.entity.YakamonZoneEntity;
import com.epita.assistants.yakamon.service.entity.ZoneEntity;
import com.epita.assistants.yakamon.repository.model.YakadexZonesModel;
import com.epita.assistants.yakamon.repository.model.YakamonZoneModel;
import com.epita.assistants.yakamon.repository.model.ZoneModel;
import com.epita.assistants.yakamon.repository.ZoneRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneService {
    private final ZoneRepository zonerepo;
    private final YakadexZonesRepository yakadexrepo;
    private final YakamonZoneRepository yakamonrepo;

    public ZoneService(final ZoneRepository repo,
                       final YakadexZonesRepository yakadex,
                       final YakamonZoneRepository yakamon){
        this.zonerepo = repo;
        this.yakadexrepo = yakadex;
        this.yakamonrepo = yakamon;
    }

    public static ZoneEntity modelToEntity_zone(ZoneModel model){ return new ZoneEntity(model.getName()); }

    public ZoneEntity particularZoneget(String name){
        var model = this.zonerepo.particularZoneModelget(name);
        if(model == null){ return null; }
        return modelToEntity_zone(model);
    }

    public List<ZoneEntity> allZonesget(){
        return this.zonerepo.allZoneModelsget()
                        .stream()
                        .map(ZoneService::modelToEntity_zone)
                        .collect(Collectors.toList());
    }

    public static YakamonZoneEntity modelToEntity_yakamon(YakamonZoneModel model){
        return new YakamonZoneEntity(model.getYakamon_id());
    }

    public List<YakamonZoneEntity> currentYakamonsget(final String name){
        var curr =  this.yakamonrepo.currentYakamonModelsget(name);

        if (curr == null){ return null; }

        return curr.stream()
                   .map(ZoneService::modelToEntity_yakamon)
                   .collect(Collectors.toList());
    }

    public static YakadexZonesEntity modelToEntity_yakadex(YakadexZonesModel model){
        return new YakadexZonesEntity(model.getId(), model.getYakadex_id(), model.getZone_id());
    }

    public List<YakadexZonesEntity> possibleYakamonsget(final String name){
        var poss = this.yakadexrepo.possibleYakamonModelsget(name);

        if (poss == null){ return null; }

        return poss.stream()
                   .map(ZoneService::modelToEntity_yakadex)
                   .collect(Collectors.toList());
    }
}
