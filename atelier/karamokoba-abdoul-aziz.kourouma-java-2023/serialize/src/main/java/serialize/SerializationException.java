package serialize;

public class SerializationException extends Exception {
    public SerializationException() {
        super("An error happened while serializing:");
        System.err.println("An error happened while serializing:");
    }
}
