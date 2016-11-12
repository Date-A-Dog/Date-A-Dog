package dateadog.dateadog.schemaobjects;

import dateadog.dateadog.wrappers.StringWrapper;

public class Shelter {
    public StringWrapper id;

    public StringWrapper name;

    public StringWrapper address1;

    public StringWrapper address2;

    public StringWrapper city;

    public StringWrapper state;

    public StringWrapper zip;

    public StringWrapper country;

    public StringWrapper latitude;

    public StringWrapper longitude;

    public StringWrapper phone;

    public StringWrapper fax;

    public StringWrapper email;

    public String getShelter() {
	return email.toString();
    }

    public String getName() {
	return name.toString();
    }

    public String getState() {
	return state.toString();
    }

    public String getCity() {
	return this.city.toString();
    }
}
