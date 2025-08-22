package commands.playlist;

import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import fileio.input.CommandInput;

public final class AddRemoveInPlaylist extends Command {
    private String playlistId;

    public AddRemoveInPlaylist(final CommandInput command) {
        super(command);
        this.playlistId = command.getPlaylistId();
    }

    @Override
    public void execute(ArrayNode output) {
        // Create output JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", getCommand());
        commandOutput.put("user", getUsername());
        commandOutput.put("timestamp", getTimestamp());

        // Get the user
        User user = app.context.ActiveUsers.getUserByName(getUsername());

        // Get the playlist by ID
        Playlist playlist = user.getPlaylistById(playlistId);
        if (playlist == null) {
            commandOutput.put("message", "The specified playlist does not exist.");
            output.add(commandOutput);
            return;
        }

        if (user.isLoadState() == false) {
            commandOutput.put("message", "Please load a source before" +
                    " adding to or removing from the playlist.");
        } else {
            if (!user.getSelectedItem().getType().equals("song")) {
                commandOutput.put("message", "The loaded source is not a song." + user.getSelectedItem().getType());
            } else {
                // Verify if the song is already in the playlist
                if (playlist.existsFile(user.getSelectedItem().getName())) {
                    // Remove the song from the playlist
                    playlist.removeFile(user.getSelectedItem());
                    commandOutput.put("message", "Successfully removed from playlist.");
                } else {
                    // Add the song to the playlist
                    playlist.addFile(user.getSelectedItem());
                    commandOutput.put("message", "Successfully added to playlist.");
                }
            }
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
