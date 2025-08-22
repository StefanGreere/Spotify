package commands.player;

import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import fileio.input.CommandInput;

public final class PlayPause extends Command {
    public PlayPause(final CommandInput command) {
        super(command);
    }

    @Override
    public void execute(final ArrayNode output) {
        User currentUser = app.context.ActiveUsers.getActiveUsers().stream()
                .filter(user -> user.getUsername().equals(getUsername()))
                .findFirst()
                .orElse(null);

        // Update the remained time of the selected item
        currentUser.getSelectedItem().updateRemainedTime(getTimestamp());

        // JSON output creation
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", getCommand());
        commandOutput.put("user", getUsername());
        commandOutput.put("timestamp", getTimestamp());

        if (currentUser != null && currentUser.isLoadState()) {
            if (currentUser.getSelectedItem().isPaused()) {
                commandOutput.put("message", "Playback resumed successfully.");
                currentUser.getSelectedItem().setPaused(false);
                currentUser.getSelectedItem().setStartTime(getTimestamp());
            } else {
                commandOutput.put("message", "Playback paused successfully.");
                currentUser.getSelectedItem().setPaused(true);
            }
        } else {
            commandOutput.put("message",
                    "Please load a source before attempting to pause or resume playback.");
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
