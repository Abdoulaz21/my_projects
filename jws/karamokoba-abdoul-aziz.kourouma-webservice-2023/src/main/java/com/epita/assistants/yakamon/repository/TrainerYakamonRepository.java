package com.epita.assistants.yakamon.repository;

import com.epita.assistants.ddl.tables.records.TrainerYakamonRecord;
import com.epita.assistants.yakamon.arch.Repository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.TRAINER_YAKAMON;

@Repository
public class TrainerYakamonRepository {
    DSLContext dslContext;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TrainerYakamonRepository(Connection conn){ this.dslContext = DSL.using(conn, SQLDialect.MYSQL); }

    public List<UUID> yakamonsOfThisTrainerget(UUID uuid){
        var record = this.dslContext
                .selectFrom(TRAINER_YAKAMON)
                .where(TRAINER_YAKAMON.TRAINER_ID.eq(uuid))
                .fetch();

        if (record.isEmpty()){
            this.logger.error("Trainer with uuid = " + uuid + " doesn't exist!");
            return null;
        }

        return record.stream()
                .map(TrainerYakamonRecord::getYakamonId)
                .collect(Collectors.toList());
    }

    public boolean addYakamonToTrainerModel(UUID uuid, UUID yakamonId){
        try{
            this.dslContext
                    .insertInto(TRAINER_YAKAMON, TRAINER_YAKAMON.TRAINER_ID, TRAINER_YAKAMON.YAKAMON_ID)
                    .values(uuid, yakamonId)
                    .execute();
            return true;
        } catch (Exception e){
            this.logger.error("Cannot add yakamon " + yakamonId + " to trainer " + uuid);
            return false;
        }
    }
}
