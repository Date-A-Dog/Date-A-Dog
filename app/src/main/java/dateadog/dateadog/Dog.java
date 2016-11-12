package dateadog.dateadog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Represents a dog available for a date at a participating shelter.
 */
public class Dog {

    /**The ID for this dog. */
    private long dogId;
    /** The breed for this dog. */
    private String breed;
    /** The age of this dog. */
    private String age;
    /** The shelter from where to request a date with this dog. */
    private int shelterID;

    public String getImage() {
        return image;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    /** A set of pictures of this dog. */
    private String image;
    /** A short description of this dog. */
    private String description;
    /** The name of this dog. */
    private String name;
    /** The sex of this dog. */
    private String sex;
    /** Whether or not this dog is available to date. */
    private boolean availability;



    public Dog(JSONObject json) {
        try {
            JSONObject dogObject = json.getJSONObject("dog");
            dogId = dogObject.getLong("id");
            name = dogObject.getString("name");
            age = dogObject.getString("age");
            sex = dogObject.getString("sex");
            image = dogObject.getJSONObject("media").getJSONObject("photos").getJSONObject("1").getString("x");
            System.out.println(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Dog(int dogID, String breed, String age, int shelterID, Set<String> images,
                      String description, String name, String sex, boolean availability) {
        this.dogId = dogID;
        this.breed = breed;
        this.age = age;
        this.shelterID = shelterID;
        // this.images = images;
        this.description = description;
        this.name = name;
        this.sex = sex;
        this.availability = availability;
    }


}
