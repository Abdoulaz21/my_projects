package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.YakadexZonesModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.YAKADEX_ZONES;

@Repository
public class YakadexZonesRepository {
    DSLContext dslcontext;
    public YakadexZonesRepository(Connection conn){ this.dslcontext = DSL.using(conn, SQLDialect.MYSQL); }

    public List<YakadexZonesModel> YZModelsget(String yak_id){
        var record = this.dslcontext
                .selectFrom(YAKADEX_ZONES)
                .where(YAKADEX_ZONES.YAKADEX_ID.eq(yak_id))
                .fetch();

        if (record.isEmpty()){ return null; }

        return record.stream()
                .map(yz -> new YakadexZonesModel(yz.getId(),
                        yz.getYakadexId(),
                        yz.getZoneId()))
                .collect(Collectors.toList());
    }

    public List<YakadexZonesModel> possibleYakamonModelsget(final String name){
        var record = this.dslcontext
                .selectFrom(YAKADEX_ZONES)
                .where(YAKADEX_ZONES.ZONE_ID.eq(name))
                .fetch();

        if (record.isEmpty()) { return null; }

        return record.stream()
                .map(yak -> new YakadexZonesModel(yak.getId(), yak.getYakadexId(), yak.getZoneId()))
                .collect(Collectors.toList());
    }
}
