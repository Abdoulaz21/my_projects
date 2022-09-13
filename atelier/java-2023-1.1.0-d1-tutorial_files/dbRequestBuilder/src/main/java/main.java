import RequestBuilder.RequestBuilder;

import java.util.Arrays;
import java.util.List;

public class main {
    public static void main(String[] args) {
        var req = new RequestBuilder("movie-theatre");
        List<String> films = Arrays.asList("Aliens", "The Departed");
        System.out.println(req.buildInsert("title", films));

    }
}
