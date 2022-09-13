package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.YakamonZoneModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.YAKAMON_ZONE;

@Repository
public class YakamonZoneRepository {
    DSLContext dslContext;

    public YakamonZoneRepository(Connection conn){ this.dslContext = DSL.using(conn, SQLDialect.MYSQL); }

    public List<YakamonZoneModel> currentYakamonModelsget(final String name){
        var record = this.dslContext
                .selectFrom(YAKAMON_ZONE)
                .where(YAKAMON_ZONE.ZONE_ID.eq(name))
                .fetch();

        if (record.isEmpty()) { return null; }

        return record.stream()
                .map(yak -> new YakamonZoneModel(yak.getYakamonId()))
                .collect(Collectors.toList());
    }
}
