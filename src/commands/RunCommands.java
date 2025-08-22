package commands;

import commands.player.Like;
import commands.player.Load;
import commands.player.PlayPause;
import commands.player.Status;
import commands.playlist.AddRemoveInPlaylist;
import commands.playlist.CreatePlaylist;
import commands.search.bar.Search;
import commands.search.bar.Select;
import fileio.input.CommandInput;


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
            case "load":
                return new Load(command);
            case "status":
                return new Status(command);
            case "playPause":
                return new PlayPause(command);
            case "createPlaylist":
                return new CreatePlaylist(command);
            case "addRemoveInPlaylist":
                return new AddRemoveInPlaylist(command);
            case "like":
                return new Like(command);
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
