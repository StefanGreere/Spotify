package fileio.input;

import commands.search.bar.Filters;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class CommandInput {
    private String command;
    private String username;
    private int timestamp;
    private String type;
    private Filters filters;
    private String itemNumber;
    private String playlistName;
    private String playlistId;
    private String seed;
}
