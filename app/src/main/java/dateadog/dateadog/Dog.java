package dateadog.dateadog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a dog available for a date at a participating shelter.
 */
public class Dog {

    /**The ID for this dog. */
    private long dogId;
    /** The breed for this dog. */
    private List<String> breeds;
    /** The age of this dog. */
    private String age;
    /** The shelter from where to request a date with this dog. */
    private int shelterID;
    /** Where the dog is located */
    private String city;

    public long getDogId() {
        return dogId;
    }

    public String getImage() {
        return image;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getCity() { return city; }

    public String getSex() {
        if (sex.equals("F")) {
            return "Female";
        } else if (sex.equals("M")) {
            return "Male";
        } else {
            System.out.println(sex);
            return sex;
        }
    }

    /** A set of pictures of this dog. */
    private String size;
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
            String initName = dogObject.getString("name");
            name = analyzeName(initName);
            age = dogObject.getString("age");
            sex = dogObject.getString("sex");
            size = dogObject.getString("size");
            image = dogObject.getJSONObject("media").getJSONObject("photos").getJSONObject("1").getString("x");
            city = dogObject.getJSONObject("contact").getString("city");
            JSONArray jArrayBreeds = dogObject.getJSONArray("breeds");
            breeds = new ArrayList<>();
            for (int i = 0; i <jArrayBreeds.length(); i++) {
                String breed = jArrayBreeds.getString(i);
                breeds.add(breed);
            }
            System.out.println(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Dog(int dogID, List<String> breeds, String age, int shelterID, Set<String> images,
                      String description, String name, String sex, boolean availability) {
        this.dogId = dogID;
        this.breeds = breeds;
        this.age = age;
        this.shelterID = shelterID;
        // this.images = images;
        this.description = description;
        this.name = name;
        this.sex = sex;
        this.availability = availability;
    }

    //Returns a list of breeds of dog in a string form of: Breed 1, Breed 2, Breed 3
    public String getStringBreeds() {
        String res = "";
        if (breeds == null) {
            return "Breed of Dog is Unknown.";
        } else { //breeds cannot be null in here
            if (breeds.size() == 0) {
                return "Unknown Breeds: Mixed Dog";
            } else if (breeds.size() == 1) {
                return breeds.get(0);
            } else {
                res = breeds.get(0);
                for (int i = 1; i < breeds.size(); i++) {
                    res += ", " + breeds.get(i);
                }
            }
        }
        return res;
    }

    //returns the size of the dog in a string format of
    //Small Dog, Medium Dog, Large Dog
    public String getSizeStringDog() {
        if (size == null) {
            return "Size of Dog is Unknown";
        }
        if (size.equals("S")) {
            return "Small Dog";
        } else if (size.equals("M")) {
            return "Medium Dog";
        } else if (size.equals("L")) {
            return "Large Dog";
        } else {
            return "Size of Dog is Unknown";
        }
    }

    //changes name to unknown if the name of dog is really weird like
    //foster care or something
    //takes orig string as a param
    public String analyzeName(String initName) {
        if (initName.toLowerCase().contains("foster")) {
            return "Dog(s) Intended for Foster Care (No Name)";
        } else if (initName.toLowerCase().contains("adoption")) {
            return "Dog(s)intented for adoption (No Name)";
        } else if (initName.toLowerCase().contains("donations")) {
            return "Dog(s) Needing Donations (No Name)";
        } else {
            return initName;
        }
    }


}
