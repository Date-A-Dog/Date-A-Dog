package dateadog.dateadog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by aj on 11/11/16.
 */

//info is all based on the Shelter adoption form
public class Form {
    private long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String city;
    private String state;
    private String zip;
    private String primaryPhone;
    private String shelterID; //why you want to date a dog

    public Form(long id, String firstName, String lastName, String address, String email, String city, String state, String zip, String primaryPhone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.primaryPhone = primaryPhone;
        this.shelterID = null;
    }

    public Form (JSONObject json) {
        try {
            id = json.getLong("id");
            email = json.getString("email");
            firstName = json.getString("fname");
            lastName = json.getString("lname");
            address = json.getString("street");
            city = json.getString("city");
            state = json.getString("state");
            zip = json.getString("zip");
            primaryPhone = json.getString("phone");
            shelterID = json.getString("shelterid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getID() { return id; }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public String retuenShelterID() {
        return shelterID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

}
