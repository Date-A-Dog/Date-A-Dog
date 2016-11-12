package dateadog.dateadog;

/**
 * A {@code User} is a profile of a user.
 */
public class User {

    // Facebook authentication
    private String authToken;

    // User's name
    private String name;



    /**
     * Constructs and initializes a new {@code User} with the given data.
     *
     * @param authToken the user's facebook authentication
     * @param name the user's name
     */
    public User(String authToken, String name)  {
        this.authToken = authToken;
        this.name = name;
    }

    /**
     * Returns a String that uniquely identifies this {@code User}
     *
     * @return a String that uniquely identifies this {@code User}
     */
    public String getUserToken() {
        return this.authToken;
    }

    /**
     * Returns a string that represents the user's name
     *
     * @return a String that represents the user's name
     */
    public String getUserName() {
        return this.name;
    }
}
