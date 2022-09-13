import java.util.StringJoiner;

public class Student {
    public Student(String name, int age, String majeure) throws InvalidNameException, InvalidAgeException, InvalidMajeureException{
        if (!name.matches("[A-Za-z]*")){
            throw new InvalidNameException(name);
        }
        if (age <= 0 || age >= 130){
            throw new InvalidAgeException(age);
        }
    }

    @Override
    public String toString(){
        var tostring = new StringBuilder("Name: " + this.name + ", ");
        tostring.append("Age: " + new StringBuilder(this.age) + ", ");
        tostring.append("Major: " + this.major);
        return new String(tostring);
    }

    String name;
    int age;
    String major;
}
