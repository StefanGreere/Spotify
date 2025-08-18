package app.context;

import fileio.input.LibraryInput;

public final class LibraryAccess {
    // The unique global instance of LibraryInput
    private static LibraryInput library;

    // Private constructor to prevent instantiation
    private LibraryAccess() {
    }

    /**
     * This method should be called once to load the library data.
     * It can be called from the main application or any initialization code.
     */
    public static void load(final LibraryInput libraryInput) {
        library = libraryInput;
    }

    /**
     * This method retrieves the library data.
     * It should only be called after the library has been loaded.
     *
     * @return the LibraryInput instance
     * @throws IllegalStateException if the library has not been loaded yet
     */
    public static LibraryInput getLibrary() {
        if (library == null) {
            throw new IllegalStateException("Library has not been loaded yet!");
        }
        return library;
    }
}
