package singleton;

public enum Logger {

    INSTANCE;

    public enum Level {
        INFO,
        WARN,
        ERROR
    }

    private int infos = 0;
    private int warns = 0;
    private int errors = 0;

    /**
     * Outputs the message to the standard error using the following format:
     * "[LEVEL] Message".
     * @param level The gravity level of the logged message.
     * @param message The logged message.
     */
    public void log(Logger.Level level, String message) {
        if (level == Level.INFO) {
            infos += 1;
        } else if (level == Level.WARN) {
            warns += 1;
        } else {
            errors += 1;
        }
        System.err.println("[" + level + "] " + message);
    }

    /**
     * Returns the number of info messages logged.
     */
    public int getInfoCounter() {
        return infos;
    }

    /**
     * Returns the number of warning messages logged.
     */
    public int getWarnCounter() {
        return warns;
    }

    /**
     * Returns the number of error messages logged.
     */
    public int getErrorCounter() {
        return errors;
    }

    /**
     * Resets the counters.
     */
    public void reset() {
        infos = 0;
        warns = 0;
        errors = 0;
    }
}
