public class InvalidNameException extends Exception{
    public InvalidNameException(final String n) { System.err.println("InvalidNameException: " + n); }
}
