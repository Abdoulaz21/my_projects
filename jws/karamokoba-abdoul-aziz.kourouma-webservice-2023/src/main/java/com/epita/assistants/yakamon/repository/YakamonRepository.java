package com.epita.assistants.yakamon.repository;

import com.epita.assistants.yakamon.arch.Repository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

@Repository
public class YakamonRepository {
    DSLContext dslContext;
    public YakamonRepository(Connection conn){ this.dslContext = DSL.using(conn, SQLDialect.MYSQL); }
}
