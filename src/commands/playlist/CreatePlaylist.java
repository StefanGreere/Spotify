package commands.playlist;

import app.context.ActiveUsers;
import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandInput;

public final class CreatePlaylist extends Command {
    private String playlistName;

    /**
     * Constructor for CreatePlaylist command.
     * @param command the command input containing the playlist name
     */
    public CreatePlaylist(final CommandInput command) {
        super(command);
        this.playlistName = command.getPlaylistName();
    }

    @Override
    public void execute(final ArrayNode output) {
        // Create output JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", getCommand());
        commandOutput.put("user", getUsername());
        commandOutput.put("timestamp", getTimestamp());

        // Get the user by username
        User currentUser = app.context.ActiveUsers.getUserByName(getUsername());

        if (currentUser == null) {
            // Set the current user his selected item
            User user = new User(getUsername(), null, null);

            // Add the current user to the active users list
            ActiveUsers.addUser(user);
            // Create a new playlist and add it to the user's playlists
            user.createPlaylist(playlistName);
            commandOutput.put("message", "Playlist created successfully.");
        } else {
            if (currentUser.getPlaylistByName(playlistName) == null) {
                // Create a new playlist and add it to the user's playlists
                currentUser.createPlaylist(playlistName);
                commandOutput.put("message", "Playlist created successfully.");
            } else {
                // If the playlist already exists, return an error message
                commandOutput.put("message", "A playlist with the same name already exists.");
            }
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
