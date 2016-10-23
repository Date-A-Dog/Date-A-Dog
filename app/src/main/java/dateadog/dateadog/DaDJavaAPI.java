package dateadog.dateadog;

import java.util.Set;

/**
 * {@code DaDJavaAPI} interfaces with the Date-a-Dog server to retrieve and update
 * information about the user and dogs available to them.
 */
public class DaDJavaAPI {

    private int lastViewedDogId;

    public DaDJavaAPI(int userId) {

    }

    public Set<DogProfile> getNextDogs(int zipCode) {
        return null;
    }

    private void judgeDog(int dogId, boolean like) {

    }

    public void likeDog(int dogId) {

    }

    public void dislikeDog(int dogId) {

    }

    public Set<DateRequest> getRequests() {
        return null;
    }

    public void requestDate(int dogId) {

    }

}
