package commands.player;

import app.context.ActiveUsers;
import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import fileio.input.CommandInput;

public class Like extends Command {
    /**
     * Constructor for the Like command.
     * @param command the command input
     */
    public Like(final CommandInput command) {
        super(command);
    }

    @Override
    public void execute(final ArrayNode output) {
        // Create output JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", getCommand());
        commandOutput.put("user", getUsername());
        commandOutput.put("timestamp", getTimestamp());

        User user = ActiveUsers.getUserByName(getUsername());

        if (user.isLoadState() == false) {
            commandOutput.put("message", "Please load a source before liking or unliking.");
        } else if (!user.getSelectedItem().getType().equals("song")) {
            commandOutput.put("message","Loaded source is not a song.");
        } else {
            if (user.getSelectedItem().isLike()) {
                // If the song is already liked, unlike it
                user.getSelectedItem().setLike(false);
                commandOutput.put("message", "Unlike registered successfully.");
            } else {
                // If the song is not liked, like it
                user.getSelectedItem().setLike(true);
                commandOutput.put("message", "Like registered successfully.");
            }
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
