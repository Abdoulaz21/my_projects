public class InvalidAgeException extends Exception {
    public InvalidAgeException(final int age) { System.err.println("Invalid age: " + age); }
}
