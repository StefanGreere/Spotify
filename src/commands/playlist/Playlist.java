package commands.playlist;

import app.context.AudioFile;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public final class Playlist {
    private String playlistName;
    List<AudioFile> files;

    /**
     * Constructor for Playlist.
     * @param playlistName the name of the playlist
     */
    public Playlist(final String playlistName) {
        this.playlistName = playlistName;
        this.files = new ArrayList<>();
    }
}
