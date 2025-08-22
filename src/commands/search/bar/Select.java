package commands.search.bar;

import app.context.ActiveUsers;
import app.context.AudioFile;
import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import fileio.input.CommandInput;
import fileio.input.FileInput;

public final class Select extends Command {
    private String itemNumber;

    public Select(final CommandInput command) {
        super(command);
        this.itemNumber = command.getItemNumber();
    }

    @Override
    public void execute(final ArrayNode output) {
        // Create output JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", getCommand());
        commandOutput.put("user", getUsername());
        commandOutput.put("timestamp", getTimestamp());

        if (Search.getResultsList().isEmpty()) {
            commandOutput.put("message", "Please conduct a search before making a selection.");
        } else {
            if (Integer.parseInt(itemNumber) > Search.getResultsList().size()) {
                commandOutput.put("message", "The selected ID is too high.");
            } else {
                FileInput selectedItem = Search.getResultsList().
                                            get(Integer.parseInt(itemNumber) - 1);
                commandOutput.put("message", "Successfully selected " +
                                    selectedItem.getName() + ".");

                if (ActiveUsers.getUserByName(getUsername()) == null) {
                    // Set the current user his selected item
                    User user = new User(getUsername(), selectedItem.getName(), selectedItem.getDuration(), Search.getType());

                    // Add the current user to the active users list
                    ActiveUsers.addUser(user);
                } else {
                    // Find the current user by username
                    User currentUser = ActiveUsers.getUserByName(getUsername());

                    // Set the selected item for the current user
                    currentUser.setSelectedItem(new AudioFile(selectedItem.getName(),
                            selectedItem.getDuration(), false, Search.getType()));
                }
            }
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
