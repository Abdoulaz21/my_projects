package data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsSongs {
    public static List<String> getOlderArtists(List<Song> songs) {
        return songs.stream().filter(i -> i.getArtist().getAge() >= 30)
                    .map(Song::getArtist)
                    .distinct()
                    .map(i -> i.getSurname())
                    .limit(10)
                    .collect(Collectors.toList());
    }

    public static Integer getSumAges(List<Song> songs) {
        return songs.stream().filter(i -> i.getArtist().getAge() >= 20)
                    .map(i -> i.getArtist().getAge())
                    .reduce(0, Integer::sum);
    }

    public static List<String> getMusics(List<Song> songs) {
        return songs.stream().map(i -> i.getArtist().getName())
                    .filter(i -> i.toLowerCase().contains("an"))
                    .limit(10)
                    .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Artist Celine = new Artist(false, 53, "Dion", "Celine");
        Artist ArafatDJ = new Artist(true, 31, "Arafat", "Yorobo");
        Artist Molare = new Artist(true, 42, "Inter", "Molare");
        Song Kpangor = new Song("Kpangor", 185, ArafatDJ);
        Song featgarou = new Song("Sous le vent", 197, Celine);
        Song CoupéDécalé = new Song("Coupé Décalé", 205, Molare);
        List<Song> songs = new ArrayList<Song>();
        songs.add(Kpangor);
        songs.add(featgarou);
        songs.add(CoupéDécalé);
        System.out.println(getOlderArtists(songs));
    }
}
