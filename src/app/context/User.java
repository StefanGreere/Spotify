package app.context;

import commands.playlist.Playlist;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class User {
    private String username;
    private AudioFile selectedItem;
    private boolean loadState;
    private List<Playlist> playlists;

    public User(final String username, final String selectedItem, final Integer duration) {
        this.username = username;
        this.loadState = false;
        this.playlists = new ArrayList<>();
        this.selectedItem = new AudioFile(selectedItem, duration, false);
    }

    public Playlist getPlaylistByName(final String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getPlaylistName().equals(playlistName)) {
                return playlist;
            }
        }
        return null; // Return null if no playlist found with the given name
    }

    public void createPlaylist(final String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);
        playlists.add(newPlaylist);
    }
}
