package app.context;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private String username;
    private AudioFile selectedItem;
    private boolean loadState;

    public User(final String username, final String selectedItem, final Integer duration) {
        this.username = username;
        this.loadState = false;
        this.selectedItem = new AudioFile();
        this.selectedItem.setName(selectedItem);
        this.selectedItem.setRemainedTime(duration);
        this.selectedItem.setRepeat("No Repeat");
        this.selectedItem.setShuffle(false);
        this.selectedItem.setPaused(false);
    }
}
