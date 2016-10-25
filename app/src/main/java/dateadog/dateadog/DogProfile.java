package dateadog.dateadog;

/**
 * A DogProfile is a dog available for a date at a participating shelter.
 */
public class DogProfile {

    /**The ID for this dog. */
    private int dogId;
    /** The breed for this dog. */
    private String breed;
    /** The age of this dog. */
    private String age;
    /** The shelter from where to request a date with this dog. */
    private ShelterProfile shelter;
    /** A set of pictures of this dog. */
    private Set<String> images;
    /** A short description of this dog. */
    private String description;
    /** The name of this dog. */
    private String name;
    /** The sex of this dog. */
    private String sex;
    /** Whether or not this dog is available to date. */
    private boolean availability;


    public DogProfile(int dogID, String breed, String age, ShelterProfile shelter, Set<String> images,
                      String description, String name, String sex, boolean availability) {
        this.dogId = dogID;
        this.breed = breed;
        this.age = age;
        this.shelter = shelter;
        this.images = images;
        this.description = description;
        this.name = name;
        this.sex = sex;
        this.availability = availability;
    }


}
