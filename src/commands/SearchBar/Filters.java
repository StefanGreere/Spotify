package commands.SearchBar;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Filters {
    // for songs
    private String name;
    private String album;
    private String[] tags;
    private String lyrics;
    private String genre;
    private String releaseYear;
    private String artist;
    // for podcasts and playlists
    private String owner;
}
