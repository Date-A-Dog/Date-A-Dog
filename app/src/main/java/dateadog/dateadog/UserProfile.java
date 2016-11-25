package dateadog.dateadog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class stores the user's profile information.
 */
public class UserProfile {

    private String firstName;
    private String lastName;
    private String address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public JSONObject asJSONObject() {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("email", getEmail());
            parameters.put("fname", getFirstName());
            parameters.put("lname", getLastName());
            parameters.put("street", getAddress());
            parameters.put("city", getCity());
            parameters.put("state", getState());
            parameters.put("zip", getZip());
            parameters.put("phone", getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    public UserProfile(String firstName, String lastName, String address, String email, String city, String state, String zip, String phone, String shelterId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.shelterId = shelterId;
    }

    /**
     * Constructs a profile and initializes its fields using the given JSON object.
     *
     * @param json the JSON object containing the profile data
     */
    public UserProfile(JSONObject json) {
        try {
            email = json.getString("email");
            firstName = json.getString("fname");
            lastName = json.getString("lname");
            address = json.getString("street");
            city = json.getString("city");
            state = json.getString("state");
            zip = json.getString("zip");
            phone = json.getString("phone");
            shelterId = json.getString("shelterid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
