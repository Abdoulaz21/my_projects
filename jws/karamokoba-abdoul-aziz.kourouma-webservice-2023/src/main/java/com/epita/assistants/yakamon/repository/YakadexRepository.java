package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.YakadexModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.*;

@Repository
public class YakadexRepository {
    DSLContext dslContext;

    public YakadexRepository(Connection conn){ this.dslContext = DSL.using(conn, SQLDialect.MYSQL); }

    public List<YakadexModel> allYakadexModelsget(){
        return this.dslContext
                .selectFrom(YAKADEX)
                .fetch()
                .stream()
                .map(yak -> new YakadexModel(yak.getName(),
                                             yak.getPreviousEvolution(),
                                             yak.getNextEvolution()))
                .collect(Collectors.toList());
    }

    public YakadexModel particularYakadexModelget(String name){
        var record = this.dslContext
                .selectFrom(YAKADEX)
                .where(YAKADEX.NAME.eq(name))
                .fetch();

        if (record.isEmpty()){ return null; }

        return record.stream()
                .map(yak -> new YakadexModel(yak.getName(),
                                             yak.getPreviousEvolution(),
                                             yak.getNextEvolution()))
                .collect(Collectors.toList())
                .get(0);
    }
}
