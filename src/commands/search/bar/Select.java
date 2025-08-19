package commands.search.bar;

import app.context.ActiveUsers;
import app.context.LibraryAccess;
import app.context.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandInput;
import fileio.input.FileInput;
import fileio.input.LibraryInput;
import fileio.input.UserInput;
import java.util.List;

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

                // Get the library access
                LibraryInput library = LibraryAccess.getLibrary();

                // Get the list of users from the library
                List<UserInput> users = library.getUsers();

                // Find the current user based on the username
                UserInput currentUser = users.stream()
                        .filter(user -> user.getUsername().equals(getUsername()))
                        .findFirst()
                        .orElse(null);

                // Get the list of active users
                List<User> activeUsers = ActiveUsers.getActiveUsers();

                if (currentUser != null && !activeUsers.contains(currentUser)) {
                    // Set the current user his selected item
                    User user = new User(currentUser.getUsername(), selectedItem.getName(), selectedItem.getDuration());

                    // Add the current user to the active users list
                    activeUsers.add(user);
                } else {
//                    // Update the selected item for the current user
//                    User activeUser = activeUsers.stream()
//                            .filter(user -> user.getUsername().equals(getUsername()))
//                            .findFirst()
//                            .orElse(null);
//
//                    if (activeUser != null) {
//                        activeUser.setSelectedItem(selectedItem.getName(), selectedItem.getDuration());
//                    } else {
//                        commandOutput.put("message", "User not found in active users.");
//                        output.add(commandOutput);
//                        return;
//                    }
                }
            }
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
