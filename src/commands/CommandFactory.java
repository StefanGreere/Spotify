package commands;

public interface CommandFactory {
    /**
     * Creates a Command object based on the provided CommandInput.
     *
     * @param command the CommandInput containing details for the command to be created
     * @return a Command object that corresponds to the input
     */
    Command createCommand(CommandInput command);
}
