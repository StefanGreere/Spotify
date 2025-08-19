package commands.search.bar;

import app.context.LibraryAccess;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.CommandInput;
import fileio.input.FileInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;

public final class Search extends Command {
    private String type;
    private Filters filters;

    // Static variables to hold results
    private static List<FileInput> resultsList;

    public static List<FileInput> getResultsList() {
        return resultsList;
    }

    public Search(final CommandInput command) {
        super(command);
        this.type = command.getType();
        this.filters = command.getFilters();
        this.resultsList = new ArrayList<>();
    }

    @Override
    public void execute(final ArrayNode output) {
        switch (type) {
            case "song" -> {
                this.searchSong(output);
            }
            case "playlist" -> {

            }
            case "podcast" -> {
                this.searchPodcast(output);
            }
            default -> throw new IllegalArgumentException("Unknown search type: " + type);
        }
    }

    public void searchSong(final ArrayNode output) {
        // Access the library
        LibraryInput library = LibraryAccess.getLibrary();

        // Get the list of songs from the library
        ArrayList<SongInput> songs = library.getSongs();

        // Clear previous results
        resultsList.clear();

        for (SongInput song : songs) {
            boolean addToResults = true;

            // Check if the song name starts with the filter name
            if (filters.getName() != null && !song.getName().startsWith(filters.getName())) {
                addToResults = false;
            }

            // Check if the song duration is within the specified range
            if (filters.getAlbum() != null && !song.getAlbum().startsWith(filters.getAlbum())) {
                addToResults = false;
            }

            if (filters.getTags() != null && !song.hasTags(filters.getTags())) {
                addToResults = false;
            }

            if (filters.getLyrics() != null && !song.getLyrics().contains(filters.getLyrics())) {
                addToResults = false;
            }

            if (filters.getGenre() != null &&
                                        !song.getGenre().equalsIgnoreCase(filters.getGenre())) {
                addToResults = false;
            }

            if (filters.getArtist() != null && !song.getArtist().equals(filters.getArtist())) {
                addToResults = false;
            }

            if (filters.getReleaseYear() != null) {
                if (filters.getReleaseYear().charAt(0) == '<') {
                    String year = filters.getReleaseYear().substring(1);
                    if (song.getReleaseYear() >= Integer.parseInt(year)) {
                        addToResults = false;
                    }
                } else {
                    String year = filters.getReleaseYear().substring(1);
                    if (song.getReleaseYear() <= Integer.parseInt(year)) {
                        addToResults = false;
                    }
                }
            }

            if (addToResults) {
                resultsList.add(song);
            }
        }

        // Create output JSON
        createOutput(output, resultsList);
    }

    public void searchPodcast(final ArrayNode output) {
        // Access the library
        LibraryInput library = LibraryAccess.getLibrary();

        // Get the list of podcasts from the library
        ArrayList<PodcastInput> podcasts = library.getPodcasts();

        // Clear previous results
        resultsList.clear();

        for (PodcastInput podcast : podcasts) {
            boolean addToResults = true;

            // Check if the podcast name starts with the filter name
            if (filters.getName() != null && !podcast.getName().startsWith(filters.getName())) {
                addToResults = false;
            }

            if (filters.getOwner() != null && !podcast.getOwner().equals(filters.getOwner())) {
                addToResults = false;
            }

            if (addToResults) {
                resultsList.add(podcast);
            }
        }

        // Create output JSON
        createOutput(output, resultsList);
    }

    private void createOutput(ArrayNode output, List<FileInput> resultsList) {
        final int SIZE = 5;

        // Create output JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode commandOutput = mapper.createObjectNode();
        commandOutput.put("command", getCommand());
        commandOutput.put("user", getUsername());
        commandOutput.put("timestamp", getTimestamp());
        commandOutput.put("message", "Search returned " +
                (resultsList.size() > SIZE ? SIZE : resultsList.size()) + " results");

        ArrayNode resultsArray = mapper.createArrayNode();

        for (int i = 0; i < resultsList.size() && i < SIZE; i++) {
            resultsArray.add(resultsList.get(i).getName());
        }
        commandOutput.set("results", resultsArray);
        output.add(commandOutput);
    }
}
