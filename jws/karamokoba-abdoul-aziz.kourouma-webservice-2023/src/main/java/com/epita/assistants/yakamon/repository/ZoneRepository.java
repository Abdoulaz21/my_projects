package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.*;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.*;

@Repository
public class ZoneRepository {
    DSLContext dslContext;

    public ZoneRepository(Connection conn){
        this.dslContext = DSL.using(conn, SQLDialect.MYSQL);
    }

    public List<ZoneModel> allZoneModelsget(){
        return this.dslContext
                .selectFrom(ZONE)
                .fetch()
                .stream()
                .map(record -> new ZoneModel(record.getName()))
                .collect(Collectors.toList());
    }

    public ZoneModel particularZoneModelget(String name){
        var record = this.dslContext
                   .selectFrom(ZONE)
                   .where(ZONE.NAME.eq(name))
                   .fetch();

        if (record.isEmpty()){ return null; }

        return record.stream()
                     .map(tmp -> new ZoneModel(tmp.getName()))
                     .collect(Collectors.toList())
                     .get(0);
    }
}
