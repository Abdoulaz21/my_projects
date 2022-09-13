import streamStudent.Pair;
import streamStudent.Streamer;

import java.util.Arrays;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        Streamer streamer = new Streamer();
        Pair<Integer, String> pair1 = new Pair<>(-1, "abc_z");
        Pair<Integer, String> pair2 = new Pair<>(10, "loginx");
        Pair<Integer, String> pair3 = new Pair<>(10, "login_x");
        Collection<Pair<Integer, String>> collection = Arrays.asList(pair1, pair2, pair3);
        streamer.validator(collection.stream()).forEach(System.out::println);
    }
}
