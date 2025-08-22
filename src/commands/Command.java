package commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Command {
    private String command;
    private String username;
    private int timestamp;

    /**
     * Constructor for the Command class.
     */
    public Command(final CommandInput command) {
        this.command = command.getCommand();
        this.username = command.getUsername();
        this.timestamp = command.getTimestamp();
    }

    /**
     * Executes the command and returns the result as an ObjectNode.
     *
     * @return an ObjectNode containing the result of the command execution
     */
    public abstract void execute(ArrayNode output);
}
