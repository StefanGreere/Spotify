package fileio.input;

public abstract class FileInput {
    /**
     * This class can be used as a base class for different file types
     * It can contain common properties or methods if needed in the future
     * Currently, it serves as a marker interface for file inputs
     */
    public FileInput() {
        // Default constructor
    }

    /**
     * Abstract methods to be implemented by subclasses
     */
    public abstract String getName();

    /**
     * Returns the duration of the file in seconds.
     * This method should be implemented by subclasses to return the specific duration.
     *
     * @return the duration of the file in seconds, or null if not applicable
     */
    public abstract Integer getDuration();
}
