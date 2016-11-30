package dateadog.dateadog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * This class stores the user's profile information.
 */
public class UserProfile implements Serializable {

    private String firstName;
    private String lastName;
    private String street;
    private String email;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String shelterId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShelterId() {
        return shelterId;
    }

    public void setShelterId(String shelterId) {
        this.shelterId = shelterId;
    }

    public boolean isComplete() {
        return !(firstName.isEmpty() || lastName.isEmpty() ||  email.isEmpty()
                 ||  street.isEmpty() ||  city.isEmpty() ||  state.isEmpty()
                 || zip.isEmpty() ||  phone.isEmpty());
    }

    public JSONObject asJSONObject() {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fname", getFirstName());
            parameters.put("lname", getLastName());
            parameters.put("email", getEmail());
            parameters.put("street", getStreet());
            parameters.put("city", getCity());
            parameters.put("state", getState());
            parameters.put("zip", getZip());
            parameters.put("phone", getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    public UserProfile(String firstName, String lastName, String email, String street, String city, String state, String zip, String phone, String shelterId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.shelterId = shelterId;
    }

    /**
     * Constructs and initializes an empty user profile (empty strings for each field).
     */
    public UserProfile() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.street = "";
        this.city = "";
        this.state = "";
        this.zip = "";
        this.phone = "";
        this.shelterId = "";
    }

    /**
     * Constructs and initializes a user profile using the fields of the given JSON object.
     *
     * @param json the JSON object containing the profile data
     */
    public UserProfile(JSONObject json) {
        try {
            firstName = json.getString("fname");
            lastName = json.getString("lname");
            email = json.getString("email");
            street = json.getString("street");
            city = json.getString("city");
            state = json.getString("state");
            zip = json.getString("zip");
            phone = json.getString("phone");
            shelterId = json.getString("shelterid");
            // Replace missing JSON values with empty string:
            firstName = firstName.equals("null") ? "" : firstName;
            lastName = lastName.equals("null") ? "" : lastName;
            email = email.equals("null") ? "" : email;
            street = street.equals("null") ? "" : street;
            city = city.equals("null") ? "" : city;
            state = state.equals("null") ? "" : state;
            zip = zip.equals("null") ? "" : zip;
            phone = phone.equals("null") ? "" : phone;
            shelterId = shelterId.equals("null") ? "" : shelterId;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
