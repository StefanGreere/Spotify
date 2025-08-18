package commands.SearchBar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandInput;

public class Select extends Command {
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
                String selectedItem = Search.getResultsList().get(Integer.parseInt(itemNumber) - 1);
                commandOutput.put("message", "Successfully selected " + selectedItem + ".");
            }
        }

        // Add the command output to the output array
        output.add(commandOutput);
    }
}
