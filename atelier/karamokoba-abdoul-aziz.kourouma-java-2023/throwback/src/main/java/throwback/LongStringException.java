package throwback;

public class LongStringException extends StringException {

    public LongStringException(String message) {
        super("StringException: LongStringException: " + message + " (length: " + message.length() + ")");
    }
}
