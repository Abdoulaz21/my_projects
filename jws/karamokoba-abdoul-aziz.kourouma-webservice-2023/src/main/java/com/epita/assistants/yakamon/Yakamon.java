package com.epita.assistants.yakamon;

import com.epita.assistants.yakamon.controller.*;
import com.epita.assistants.yakamon.repository.*;
import com.epita.assistants.yakamon.service.*;
import org.h2.jdbcx.JdbcDataSource;
import spark.Spark;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Entry point for Yakamon.
 *
 */
public final class Yakamon {

    /**
     * Setup database connection
     *
     * @return Optional with the Connection if succeed
     */
    private static Optional<Connection> setupConnection() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:./webservice");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        try {
            return Optional.of(dataSource.getConnection());
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    public static void start(final int port) {
        // 1 - Instantiate your Controllers
        // 2 - Set the port of spark
        // 3 - Register your endpoints with the good address and the good
        // controller method
        // Be careful: remember that you must set the content type
        var connection = setupConnection();
        if (connection.isEmpty()){
            System.exit(1);
        }
        TypeRepository typerepo = new TypeRepository(connection.get());
        TypeService typeservice = new TypeService(typerepo);
        TypeController typecontroller = new TypeController(typeservice);

        MoveRepository moverepo = new MoveRepository(connection.get());
        MoveService moveservice = new MoveService(moverepo);
        MoveController movecontroller = new MoveController(moveservice);

        ZoneRepository zonerepo = new ZoneRepository(connection.get());
        YakadexZonesRepository yakadexzonerepo = new YakadexZonesRepository(connection.get());
        YakamonZoneRepository yakamonzonerepo = new YakamonZoneRepository(connection.get());
        ZoneService zoneservice = new ZoneService(zonerepo, yakadexzonerepo, yakamonzonerepo);
        ZoneController zonecontroller = new ZoneController(zoneservice);

        YakadexRepository yakadexrepo = new YakadexRepository(connection.get());
        YakadexMovesRepository yakadexmoverepo = new YakadexMovesRepository(connection.get());
        YakadexTypesRepository yakadextyperepo = new YakadexTypesRepository(connection.get());
        YakadexService yakadexservice = new YakadexService(yakadexrepo, yakadexzonerepo, yakadexmoverepo, yakadextyperepo);
        YakadexController yakadexcontroller = new YakadexController(yakadexservice);

        TrainerRepository trainerrepo = new TrainerRepository(connection.get());
        TrainerYakamonRepository traineryakamonrepo = new TrainerYakamonRepository(connection.get());
        TrainerService trainerserv = new TrainerService(trainerrepo, traineryakamonrepo);
        TrainerController trainercontroller = new TrainerController(trainerserv);

        Spark.port(port);

        typecontroller.init();
        movecontroller.init();
        zonecontroller.init();
        yakadexcontroller.init();
        trainercontroller.init();
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String... args) {
        start(4567);
    }
}
