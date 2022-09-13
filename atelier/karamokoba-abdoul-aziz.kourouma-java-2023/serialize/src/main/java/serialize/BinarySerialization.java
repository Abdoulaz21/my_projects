package serialize;

import java.io.*;

public class BinarySerialization {

    public static void serializeStudent(Student student, String fileOut) throws SerializationException {
        try (final var myOos = new ObjectOutputStream(new FileOutputStream(fileOut))) {
            myOos.writeObject(student);
        } catch (final IOException e) {
            System.err.println(e);
            throw new SerializationException();
        }
    }

    public static Student deserializeStudent(String fileIn) throws SerializationException {
        try ( final var myOis = new ObjectInputStream(new FileInputStream(fileIn))) {
            return (Student) myOis.readObject();
        } catch (final IOException | ClassNotFoundException | NullPointerException e) {
            throw new SerializationException();
        }
    }
}
