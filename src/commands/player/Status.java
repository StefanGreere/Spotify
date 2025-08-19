package commands.player;

import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandInput;

import java.util.List;

public final class Status extends Command {
    public Status(final CommandInput command) {
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

        if (currentUser == null) {
            throw new IllegalArgumentException("User not found: " + getUsername());
        }

        // Update the remained time of the selected item
        currentUser.getSelectedItem().updateRemainedTime(getTimestamp());

        // Build the status message
        ObjectNode statusMessage = mapper.createObjectNode();
        statusMessage.put("name", currentUser.getSelectedItem().getName());
        statusMessage.put("remainedTime", currentUser.getSelectedItem().getRemainedTime());
        statusMessage.put("repeat", currentUser.getSelectedItem().getRepeat());
        statusMessage.put("shuffle", currentUser.getSelectedItem().isShuffle());
        statusMessage.put("paused", currentUser.getSelectedItem().isPaused());
        commandOutput.put("stats", statusMessage);

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
