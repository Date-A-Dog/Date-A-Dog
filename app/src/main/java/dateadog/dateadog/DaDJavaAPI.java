package dateadog.dateadog;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * {@code DaDJavaAPI} interfaces with the Date-a-Dog server to retrieve and update
 * information about the user and dogs available to them.
 */
public class DaDJavaAPI {

    private int lastViewedDogId;

    /** Facebook authentication token. */
    private String token;

    public DaDJavaAPI(int userId) {

    }

    public Set<DogProfile> getNextDogs(int zipCode) {
        Set<DogProfile> result = new LinkedHashSet<>();
        //result.add(new DogProfile());
        return result;
    }

    private void judgeDog(int dogId, boolean like) {

    }

    public void likeDog(int dogId) {
        judgeDog(dogId, true);
    }

    public void dislikeDog(int dogId) {
        judgeDog(dogId, false);
    }

    public Set<DateRequest> getRequests() {
        return null;
    }

    public void requestDate(int dogId) {

    }


    public ShelterProfile getShelterInfo(String shelterID) {
        return null;
    }

}
