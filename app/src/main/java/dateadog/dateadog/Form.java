package dateadog.dateadog;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//info is all based on the Shelter adoption form
public class Form {

    /** Holds the sole instance of this class. */
    private static Form instance;

    /** Used to identify this class in logging messages. */
    private static String TAG_FORM = Form.class.getName();

    private Context context;

    //form fields
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String city;
    private String state;
    private String zip;
    private String primaryPhone;
    private String shelterID;

    public Form(String firstName, String lastName, String address, String email, String city, String state, String zip, String primaryPhone) {
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

    public JSONObject asJSONObject() {
        JSONObject parameters = new JSONObject();
        try {
            /*
            req.body.email, req.body.fname, req.body.lname,
                    req.body.street, req.body.city, req.body.state,
                    req.body.phone, req.body.zip,
             */
            parameters.put("email", getEmail());
            parameters.put("fname", getFirstName());
            parameters.put("lname", getLastName());
            parameters.put("street", getAddress());
            parameters.put("city", getCity());
            parameters.put("state", getState());
            parameters.put("zip", getZip());
            parameters.put("phone", getPrimaryPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parameters;
    }

    /**
     * Constructs an instance of the Form class with the given context.
     *
     * @param context the application context in which this class will be used
     */
    public Form(Context context) {
        this.context = context;
    }

    /**
     * Returns an instance of the {@code Form} class.
     *
     * @param context the application context in which this class will be used
     * @return an instance of the {@code Form} class
     */
    public static Form getInstance(Context context) {
        if (instance == null) {
            instance = new Form(context);
        }
        return instance;
    }


    /**
     * Constructs a form object given a json
     * @param json the json of form to be parsed
     */
    public Form (JSONObject json) {
        try {
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
