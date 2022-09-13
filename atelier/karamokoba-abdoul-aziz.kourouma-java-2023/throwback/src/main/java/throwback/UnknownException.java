package throwback;

public class UnknownException extends Exception {

    public UnknownException(String message) {
        super("UnknownException: " + message);
    }
}
