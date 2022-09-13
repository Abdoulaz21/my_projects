package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.YakadexMovesModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.YAKADEX_MOVES;

@Repository
public class YakadexMovesRepository {
    DSLContext dslcontext;
    public YakadexMovesRepository(Connection conn){ this.dslcontext = DSL.using(conn, SQLDialect.MYSQL); }

    public List<YakadexMovesModel> YMModelsget(String yak_id){
        var record = this.dslcontext
                .selectFrom(YAKADEX_MOVES)
                .where(YAKADEX_MOVES.YAKADEX_ID.eq(yak_id))
                .fetch();

        if (record.isEmpty()){ return null; }

        return record.stream()
                .map(yz -> new YakadexMovesModel(yz.getId(),
                        yz.getYakadexId(),
                        yz.getMoveId()))
                .collect(Collectors.toList());
    }
}
