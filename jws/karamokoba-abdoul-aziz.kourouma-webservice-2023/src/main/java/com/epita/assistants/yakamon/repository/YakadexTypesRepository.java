package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.repository.model.YakadexTypesModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.YAKADEX_TYPES;

public class YakadexTypesRepository {
    DSLContext dslcontext;
    public YakadexTypesRepository(Connection conn){ this.dslcontext = DSL.using(conn, SQLDialect.MYSQL); }

    public List<YakadexTypesModel> YTModelsget(String yak_id){
        var record = this.dslcontext
                .selectFrom(YAKADEX_TYPES)
                .where(YAKADEX_TYPES.YAKADEX_ID.eq(yak_id))
                .fetch();

        if (record.isEmpty()){ return null; }

        return record.stream()
                .map(yz -> new YakadexTypesModel(yz.getId(),
                        yz.getYakadexId(),
                        yz.getTypeId()))
                .collect(Collectors.toList());
    }
}
