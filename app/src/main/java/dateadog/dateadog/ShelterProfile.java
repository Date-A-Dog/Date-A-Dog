package dateadog.dateadog;

/**
 * A {@code ShelterProfile} is a profile of a shelter.
 */
public class ShelterProfile {
    /** The street of the shelter's address */
    private String street;
    /** The city of the shelter's address */
    private String city;
    /** The state of the shelter's address */
    private String state;
    /** The zip code of the shelter's address */
    private int zip;
    /** The shelter's phone number */
    private String phone;
    /** The shelter's email */
    private String email;
    /** The name of the shelter */
    private String name;
    /** The ID for this shelter. */
    private String shelterId;
    /** The street of the shelter's address */
    public boolean availability;

    /**
     * Constructs and initializes a new {@code ShelterProfile} with the given data.
     *
     * @param street the street of the shelter's address
     * @param shelterID the ID for this shelter
     * @param city the city of the shelter's address
     * @param state the state of the shelter's address
     * @param zip the zip code of the shelter's address
     * @param phone the phone number of the shelter
     * @param email the email of the shelter
     * @param name the name of the shelter
     */
    public ShelterProfile(String street, String shelterID, String city, String state, int zip, String phone, String email, String name){
        this.street = street;
        this.shelterId = shelterID;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }
}