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

    public User(final String username, final String selectedItem, final Integer duration, final String type) {
        this.username = username;
        this.loadState = false;
        this.playlists = new ArrayList<>();
        if (type == null)
            this.selectedItem = null;
        else
            this.selectedItem = new AudioFile(selectedItem, duration, false, type);
    }

    public Playlist getPlaylistByName(final String playlistName) {
        for (Playlist playlist : playlists) {
            if (playlist.getPlaylistName().equals(playlistName)) {
                return playlist;
            }
        }
        return null; // Return null if no playlist found with the given name
    }

    public Playlist getPlaylistById(final String Id) {
        Integer index = Integer.parseInt(Id) - 1;
        if (index < 0 || index >= playlists.size()) {
            return null; // Return null if the index is out of bounds
        }

        return playlists.get(index); // Return the playlist at the specified index
    }

    public void createPlaylist(final String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);
        playlists.add(newPlaylist);
    }

    public Playlist getPlaylistById(final Integer playlistId) {
        if (playlistId < 0 || playlistId >= playlists.size()) {
            return null; // Return null if the index is out of bounds
        }

        return playlists.get(playlistId); // Return the playlist at the specified index
    }
}
