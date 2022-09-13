package dataAccess;

import data.Artist;
import data.Song;

import java.util.List;

public class StreamsSongs {

    public static List<String> getOlderArtists(List<Song> songs) {
        return songs.stream()
                .map(Song::getArtist)
                .filter(x -> x.getAge() >= 30)
                .map(Artist::getSurname)
                .distinct()
                .limit(10)
                .toList();
    }

    public static Integer getSumAges(List<Song> songs) {
        return songs.stream()
                .map(x -> x.getArtist().getAge())
                .filter(x -> x >= 20)
                .reduce(0, Integer::sum);
    }

    public static List<String> getMusics(List<Song> songs) {
        return songs.stream()
                .filter(s -> s.getArtist().getName().matches(".*[Aa][nN].*"))
                .map(Song::getName)
                .limit(10)
                .toList();
    }
}
