package dateadog.dateadog;

import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dog available for a date at a participating shelter.
 */
public class Dog implements Serializable {

    /** The ID for this dog. */
    private long dogId;
    /** The name of this dog. */
    private String name;
    /** The sex of this dog. */
    private String sex;
    /** Dog size. */
    private String size;
    /** The age of this dog. */
    private String age;
    /** Breeds for this dog. */
    private List<String> breeds;
    /** Where the dog is located. */
    private String city;
    /** A URL to a profile picture, or the empty string. */
    private String image;
    /** The shelter from where to request a date with this dog. */
    private String shelterId;

    public long getDogId() {
        return dogId;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getSize() {
        return size;
    }

    public String getAge() {
        return age;
    }

    public List<String> getBreeds() {
        return breeds;
    }

    public String getBreedsString() {
        if (breeds == null || breeds.size() == 0) {
            return "Unknown Breeds: Mixed Dog";
        } else if (breeds.size() == 1) {
            return breeds.get(0);
        } else {
            String result = breeds.get(0);
            for (int i = 1; i < breeds.size(); i++) {
                result += ", " + breeds.get(i);
            }
            return result;
        }
    }

    public String getCity() {
        return city;
    }

    /**
     * Returns a URL to a profile picture, or the empty string.
     *
     * @return a URL to a profile picture, or the empty string
     */
    public String getImage() {
        return image;
    }

    public String getShelterId() {
        return shelterId;
    }

    public Dog(JSONObject json) {
        try {
            JSONObject dogObject = json.getJSONObject("dog");
            dogId = dogObject.getLong("id");
            name = fixName(dogObject.getString("name"));
            age = dogObject.getString("age");
            sex = fixSex(dogObject.getString("sex"));
            size = fixSize(dogObject.getString("size"));
            if (dogObject.getJSONObject("media").getJSONObject("photos").has("1")) {
                image = dogObject.getJSONObject("media").getJSONObject("photos").getJSONObject("1").getString("x");
            } else {
                image = "";
            }
            city = dogObject.getJSONObject("contact").getString("city");
            shelterId = dogObject.getString("shelterId");
            JSONArray jArrayBreeds = dogObject.getJSONArray("breeds");
            breeds = new ArrayList<>();
            for (int i = 0; i < jArrayBreeds.length(); i++) {
                breeds.add(jArrayBreeds.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String fixName(String name) {
        if (name.toLowerCase().contains("foster")) {
            return "Dog(s) Intended for Foster Care (No Name)";
        } else if (name.toLowerCase().contains("adoption")) {
            return "Dog(s) Intended for Adoption (No Name)";
        } else if (name.toLowerCase().contains("donations")) {
            return "Dog(s) Needing Donations (No Name)";
        } else {
            return name;
        }
    }

    public String fixSize(String size) {
        if (size.equals("S")) {
            return "Small Dog";
        } else if (size.equals("M")) {
            return "Medium Dog";
        } else if (size.equals("L")) {
            return "Large Dog";
        } else {
            return "Unknown Size";
        }
    }

    public String fixSex(String sex) {
        if (sex.equals("F")) {
            return "Female";
        } else if (sex.equals("M")) {
            return "Male";
        } else {
            return sex;
        }
    }

}
