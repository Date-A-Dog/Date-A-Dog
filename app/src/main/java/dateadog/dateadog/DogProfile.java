package dateadog.dateadog;

import java.util.Set;
import java.util.List;

/**
 * A DogProfile is a dog available for a date at a participating shelter.
 */
public class DogProfile {

    /**The ID for this dog. */
    private long id; //id
    /** The name of this dog. */
    private String name;
    /** The breed for this dog. */
    private List<String> breeds;
    /** The age of this dog. */
    private String age;
    /** The shelter from where to request a date with this dog. */
    private String shelter_id;
    /** A set of pictures of this dog. */
    private List<String> image_url;
    /** A short description of this dog. */
    private String description;

    /** The sex of this dog. */
    private char sex;
    /** Whether or not this dog is available to date. */
    private char status;


    public DogProfile(long id, String name, List<String> breeds, String age, String shelter_id, List<String> image_url, String description, char sex, char status) {
        this.id = id;
        this.name = name;
        this.breeds = breeds;
        this.age = age;
        this.shelter_id = shelter_id;
        this.image_url = image_url;
        this.description = description;
        this.sex = sex;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getBreeds() {
        return breeds;
    }

    public String getAge() {
        return age;
    }

    public String getShelter_id() {
        return shelter_id;
    }

    public List<String> getImage_url() {
        return image_url;
    }

    public String getDescription() {
        return description;
    }

    public char getSex() {
        return sex;
    }

    public char getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreeds(List<String> breeds) {
        this.breeds = breeds;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setShelter_id(String shelter_id) {
        this.shelter_id = shelter_id;
    }

    public void setImage_url(List<String> image_url) {
        this.image_url = image_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setStatus(char status) {
        this.status = status;
    }
}
