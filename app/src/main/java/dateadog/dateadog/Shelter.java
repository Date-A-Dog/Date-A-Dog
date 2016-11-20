package dateadog.dateadog;

/**
 * A {@code Shelter} is a profile of a shelter.
 */
public class Shelter {

    private Address address;
    /** The shelter's phone number */
    private String phone;
    /** The shelter's email */
    private String email;
    /** The name of the shelter */
    private String name;
    /** The ID for this shelter. */
    private String shelterId;
    /** The availability of the shelter */
    public boolean availability;

    /**
     * Constructs and initializes a new {@code Shelter} with the given data.
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
    public Shelter(String street, String shelterID, String city, String state, int zip, String phone, String email, String name){
        this.address = this.new Address(street, city ,state, zip);
        this.shelterId = shelterID;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    /**
     * Returns a String that uniquely identifies this {@code Shelter}
     *
     * @return a String that uniquely identifies this {@code Shelter}
     */
    public String getShelterId() {
        return shelterId;
    }

    /**
     * Returns the street of the shelter's address
     *
     * @return the street of the shelter's address
     */
    public String getStreet() {
        return address.getStreet();
    }

    /**
     * Returns the city of the shelter's address
     *
     * @return the city of the shelter's address
     */
    public String getCity() {
        return address.getCity();
    }

    /**
     * Returns the state of the shelter's address
     *
     * @return the state of the shelter's address
     */
    public String getState() {
        return address.getState();
    }

    /**
     * Returns the zip code of the shelter's address
     *
     * @return the zip code of the shelter's address
     */
    public int getZip() {
        return address.getZip();
    }

    /**
     * Returns the phone number of the shelter
     *
     * @return the phone number of the shelter
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the email of the shelter
     *
     * @return the email of the shelter
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the name of the shelter
     *
     * @return the name of the shelter
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the availability of the shelter
     *
     * @return the availability of the shelter
     */
    public boolean getAvailability() {
        return availability;
    }

    /**
     * {@code Address} is an inner class to contain the address fields for the shelter.
     */
    private class Address {
        /** The street of the shelter's address */
        private String street;
        /** The city of the shelter's address */
        private String city;
        /** The state of the shelter's address */
        private String state;
        /** The zip code of the shelter's address */
        private int zip;

        /**
         * Constructs and initializes a new {@code Address} with the given data.
         *
         * @param street the street of the address
         * @param city the city of the address
         * @param state the state of the address
         * @param zip the zip code of the address
         */
        private Address(String street, String city, String state, int zip){
            this.street = street;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }

        private String getStreet() {
            return street;
        }

        /**
         * Returns the city of the address
         *
         * @return the city of the address
         */
        private String getCity() {
            return city;
        }

        /**
         * Returns the state of the address
         *
         * @return the state of the address
         */
        private String getState() {
            return state;
        }

        /**
         * Returns the zip code of the address
         *
         * @return the zip code of the address
         */
        private int getZip() {
            return zip;
        }
    }
}