package serialize;

public class Main {

    public static void main(String[] args){
        Student student = new Student("Bob", "Dupont", 21);
        String fileOut = "student.tmp";
        try {
            BinarySerialization.serializeStudent(student, fileOut);
            BinarySerialization.deserializeStudent(fileOut);
            var a = JsonSerialization.serializeStudent(student);
            JsonSerialization.deserializeStudent(a);
        } catch (SerializationException e) {
            System.err.println("Error");
            //var a = e.getMessage();
            //String.contains(a);
        }
    }
}
