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

    public boolean existsFile(final String fileName) {
        for (AudioFile file : files) {
            if (file.getName().equals(fileName)) {
                return true; // File exists in the playlist
            }
        }

        return false; // File does not exist in the playlist
    }

    public void addFile(final AudioFile file) {
        files.add(file); // Add the file to the playlist
    }

    public void removeFile(final AudioFile file) {
        files.remove(file); // Remove the file from the playlist
    }
}
