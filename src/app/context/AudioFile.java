package app.context;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class AudioFile {
    private String name;
    private Integer remainedTime;
    private String repeat;
    private boolean shuffle;
    private boolean paused;
    private Integer startTime;
    private String type;
    private boolean like;

    public AudioFile(final String name, final Integer remainedTime, final boolean shuffle,
                     final String type) {
        this.name = name;
        this.remainedTime = remainedTime;
        this.repeat = "No Repeat"; // Default value
        this.shuffle = shuffle;
        this.paused = false; // Default to not paused
        this.startTime = 0; // Initialize start time to 0
        this.type = type; // Song, Podcast, or Episode
        this.like = false; // Default to not liked
    }

    /**
     * Method to update the remained time of the audio file.
     *
     * @param timestamp The current timestamp to calculate the time difference.
     */
    public void updateRemainedTime(final Integer timestamp) {
        if (!paused && remainedTime != null) {
            remainedTime = remainedTime - (timestamp - startTime);
            if (remainedTime < 0) {
                remainedTime = 0; // Ensure remained time does not go negative
                name = ""; // Clear the name if time runs out
                paused = true; // Pause the audio if time runs out
            }

            startTime = timestamp; // Update start time to the current timestamp
        }
    }
}
