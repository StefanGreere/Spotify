package commands.player;

import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandInput;
import java.util.List;

public final class Load extends Command {
    public Load(final CommandInput command) {
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

        // Find the current user based on the username
        User currentUser = app.context.ActiveUsers.getUserByName(getUsername());

        // Check if the user had already loaded a song
        if (currentUser != null && currentUser.getSelectedItem() != null) {
            commandOutput.put("message", "Playback loaded successfully.");

            // Mark the current item as loaded
            currentUser.setLoadState(true);
            currentUser.getSelectedItem().setStartTime(getTimestamp());
        } else {
            commandOutput.put("message", "Please select a source before attempting to load.");
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
