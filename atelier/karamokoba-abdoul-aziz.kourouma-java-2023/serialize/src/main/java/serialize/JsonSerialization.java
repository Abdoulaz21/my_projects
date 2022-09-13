package serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerialization {

    public static String serializeStudent(Student student) throws SerializationException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(student);
        } catch (final JsonProcessingException e) {
            System.err.println(e);
            throw new SerializationException();
        }
    }

    public static Student deserializeStudent(String serializedStudent) throws SerializationException {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(serializedStudent, Student.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException();
        }
    }
}
