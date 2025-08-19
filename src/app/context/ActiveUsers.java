package app.context;

import java.util.ArrayList;
import java.util.List;

public final class ActiveUsers {
    private static List<User> activeUsers = new ArrayList<>();

    private ActiveUsers() {
        // Private constructor to prevent instantiation
    }

    /**
     * Static getter for the list of active users.
     */
    public static List<User> getActiveUsers() {
        return activeUsers;
    }

    /**
     * Clears the list of active users.
     */
    public static void clearActiveUsers() {
        activeUsers.clear();
    }

    public static User getUserByName(final String username) {
        for (User user : activeUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null; // Return null if no user found with the given username
    }

    public static void addUser(final User user) {
        if (user != null && !activeUsers.contains(user)) {
            activeUsers.add(user);
        }
    }
}
