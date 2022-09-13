package com.epita.assistants.yakamon.repository;

import com.epita.assistants.ddl.tables.records.TrainerYakamonRecord;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.repository.model.TrainerModel;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.epita.assistants.ddl.Tables.*;

@Repository
public class TrainerRepository {
    DSLContext dslContext;

    public TrainerRepository(Connection conn){ this.dslContext = DSL.using(conn, SQLDialect.MYSQL); }

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<TrainerModel> allTrainerModelsget(){
        return this.dslContext
                .selectFrom(TRAINER)
                .fetch()
                .stream()
                .map(trainer -> new TrainerModel(trainer.getUuid(), trainer.getName()))
                .collect(Collectors.toList());
    }

    public TrainerModel particularTrainerModelget(UUID uuid){
        var record = this.dslContext
                .selectFrom(TRAINER)
                .where(TRAINER.UUID.eq(uuid))
                .fetch();

        if (record.isEmpty()){
            this.logger.error("Trainer with uuid = " + uuid + " doesn't exist!");
            return null;
        }

        return record.stream()
                .map(trainer -> new TrainerModel(trainer.getUuid(), trainer.getName()))
                .collect(Collectors.toList())
                .get(0);
    }

    public TrainerModel createTrainerModel(UUID uuid, String name){
        var record = this.dslContext
                .insertInto(TRAINER, TRAINER.UUID, TRAINER.NAME)
                .values(uuid, name)
                .returningResult(TRAINER.UUID, TRAINER.NAME)
                .fetch();

        if (record.isEmpty()){
            this.logger.error("Cannot create trainer (" + uuid + ", " + name + ")");
            return null;
        }

        return record.stream()
                .map(rr -> new TrainerModel(rr.value1(), rr.value2()))
                .collect(Collectors.toList())
                .get(0);
    }

    public boolean renameTrainerModel(UUID uuid, String name){
        try {
            this.dslContext
                    .update(TRAINER)
                    .set(TRAINER.NAME, name)
                    .where(TRAINER.UUID.eq(uuid))
                    .execute();
            return true;
        } catch (Exception e){
            this.logger.error("Cannot rename trainer with uuid = " + uuid);
            return false;
        }
    }

    public boolean deleteTrainerModel(UUID uuid){
        try{
            var record = this.dslContext
                    .select(TRAINER_YAKAMON.YAKAMON_ID)
                    .from(TRAINER_YAKAMON)
                    .where(TRAINER_YAKAMON.TRAINER_ID.eq(uuid))
                    .fetch();
            if (record.isEmpty()){
                this.dslContext
                    .delete(TRAINER)
                    .where(TRAINER.UUID.eq(uuid))
                    .execute();
                return true;
            }
        } catch (Exception e){
            this.logger.error("Cannot delete trainer with uuid = " + uuid);
        }
        return false;
    }

    public boolean freeYakamonOfTrainerInZoneModel(UUID uuid, UUID yakamonId, String zone){
        try{
            this.dslContext
                    .delete(YAKAMON_ZONE)
                    .where(YAKAMON_ZONE.YAKAMON_ID.eq(yakamonId))
                    .and(YAKAMON_ZONE.ZONE_ID.eq(zone))
                    .execute();
            return true;
        } catch (Exception e){
            this.logger.error("Cannot free yakamon " + yakamonId + " of trainer " + uuid + " in " + zone);
            return false;
        }
    }
}
