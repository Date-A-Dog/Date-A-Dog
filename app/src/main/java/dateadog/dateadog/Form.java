package dateadog.dateadog;

/**
 * Created by aj on 11/11/16.
 */

//info is all based on the Shelter adoption form
public class Form {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String city;
    private String state;
    private String zip;
    private String primaryPhone;
    private String whyYouWantToDateADog; //why you want to date a dog

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

    public String getWhyYouWantToDateADog() {
        return whyYouWantToDateADog;
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

    public void setWhyYouWantToDateADog(String whyYouWantToDateADog) {
        this.whyYouWantToDateADog = whyYouWantToDateADog;
    }
}
