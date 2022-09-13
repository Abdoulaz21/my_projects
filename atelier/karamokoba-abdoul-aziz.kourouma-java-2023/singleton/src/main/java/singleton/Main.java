package singleton;

public class Main {
    public static void main(String[] strings) {
        final var logger = Logger.INSTANCE;

        System.err.println(logger.getInfoCounter());
        System.err.println(logger.getWarnCounter());
        System.err.println(logger.getErrorCounter());

        logger.log(Logger.Level.INFO, "Logger instantiated");
        logger.log(Logger.Level.WARN, "This is a warning message");

        System.err.println(logger.getInfoCounter());
        System.err.println(logger.getWarnCounter());
        System.err.println(logger.getErrorCounter());

        logger.log(Logger.Level.ERROR, "This is an error message");

        System.err.println(logger.getInfoCounter());
        System.err.println(logger.getWarnCounter());
        System.err.println(logger.getErrorCounter());

        logger.reset();

        System.err.println(logger.getInfoCounter());
        System.err.println(logger.getWarnCounter());
        System.err.println(logger.getErrorCounter());
    }
}
