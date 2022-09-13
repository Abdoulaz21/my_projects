package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.TypeModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.tables.Type.TYPE;

@Repository
public class TypeRepository {
    DSLContext dslContext;

    public TypeRepository(Connection conn){
        this.dslContext = DSL.using(conn, SQLDialect.MYSQL);
    }

    public TypeModel particularTypeModelget(final String name){
        var record = this.dslContext
                   .selectFrom(TYPE)
                   .where(TYPE.NAME.eq(name))
                   .fetch();

        if (record.isEmpty()){ return null; }

       return record.stream()
                    .map(tmp -> new TypeModel(tmp.getName()))
                    .collect(Collectors.toList())
                    .get(0);
    }

    public List<TypeModel> allTypesModelsget(){
        return this.dslContext
                .selectFrom(TYPE)
                .fetch()
                .stream()
                .map(record -> new TypeModel(record.getName()))
                .collect(Collectors.toList());
    }
}
