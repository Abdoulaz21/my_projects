package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.MoveModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.MOVE;

@Repository
public class MoveRepository {
    DSLContext dslContext;

    public MoveRepository(Connection conn){
        this.dslContext = DSL.using(conn, SQLDialect.MYSQL);
    }

    public MoveModel particularMoveModelget(final String name){
        var record = this.dslContext
                .selectFrom(MOVE)
                .where(MOVE.NAME.eq(name))
                .fetch();

        if (record.isEmpty()){ return null; }

        return record.stream()
                     .map(tmp -> new MoveModel(tmp.getName()))
                     .collect(Collectors.toList())
                     .get(0);
    }

    public List<MoveModel> allMovesModelsget(){
        return this.dslContext
                .selectFrom(MOVE)
                .fetch()
                .stream()
                .map(record -> new MoveModel(record.getName()))
                .collect(Collectors.toList());
    }
}
