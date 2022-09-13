package data;

public class Artist {
    private boolean male;
    private int age;
    private String name;
    private String surname;

    public Artist(boolean male, int age, String name, String surname) {
        this.male = male;
        this.age = age;
        this.name = name;
        this.surname = surname;
    }

    public boolean isMale() {
        return male;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
