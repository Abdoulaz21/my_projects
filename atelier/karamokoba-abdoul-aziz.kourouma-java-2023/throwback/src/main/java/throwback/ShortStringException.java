package throwback;

public class ShortStringException extends StringException {

    public ShortStringException(String message) {
        super("StringException: ShortStringException: " + message + " (length: " + message.length() + ")");
    }
}
