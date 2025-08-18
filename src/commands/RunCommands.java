package commands;

import commands.SearchBar.Search;
import commands.SearchBar.Select;

public class RunCommands implements CommandFactory {
    /**
     * Creates a command instance based on the command input.
     *
     * @param command the command input containing the command type and parameters
     * @return a Command instance corresponding to the command type, or null if the
     *         command is unknown
     */
    public Command createCommand(final CommandInput command) {
        switch (command.getCommand()) {
            case "search":
                return new Search(command);
            case "select":
                return new Select(command);
            default:
                return null;    // DELETE THIS IN FINAL VERSION !!!
        //    throw new IllegalArgumentException("Unknown command type: " + command.getCommand());
        }
    }

    /**
     * Constructs a RunCommands object.
     * This class is responsible for creating command instances based on the command type.
     */
    public RunCommands() {
    }
}
