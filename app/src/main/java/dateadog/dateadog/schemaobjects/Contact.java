package dateadog.dateadog.schemaobjects;

import dateadog.dateadog.wrappers.StringWrapper;

public class Contact {

    private StringWrapper name;
    private StringWrapper address1;
    private StringWrapper address2;
    private StringWrapper city;
    private StringWrapper state;
    private StringWrapper phone;
    private StringWrapper fax;
    private StringWrapper email;

    private StringWrapper zip;

    public String getName() {
	return name.toString();
    }

    public String getAddress1() {
	return address1.toString();
    }

    public String getAddress2() {
	return address2.toString();
    }

    public String getCity() {
	return city.toString();
    }

    public String getState() {
	return state.toString();
    }

    public String getPhone() {
	return phone.toString();
    }

    public String getFax() {
	return fax.toString();
    }

    public String getEmail() {
	return email.toString();
    }

    public String getZip() {
	return zip.toString();
    }

    public void setName(String name) {
	this.name = new StringWrapper(name);
    }

    public void setAddress1(String address1) {
	this.address1 = new StringWrapper(address1);
    }

    public void setAddress2(String address2) {
	this.address2 = new StringWrapper(address2);
    }

    public void setCity(String city) {
	this.city = new StringWrapper(city);
    }

    public void setState(String state) {
	this.state = new StringWrapper(state);
    }

    public void setPhone(String phone) {
	this.phone = new StringWrapper(phone);
    }

    public void setFax(String fax) {
	this.fax = new StringWrapper(fax);
    }

    public void setEmail(String email) {
	this.email = new StringWrapper(email);
    }

    public void setZip(String zip) {
	this.zip = new StringWrapper(zip);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();

	if (name != null)
	    sb.append(name + ", ");

	if (address1 != null)
	    sb.append(address1 + ", ");

	if (address2 != null)
	    sb.append(address2 + ", ");

	if (city != null)
	    sb.append(city + ", ");

	if (state != null)
	    sb.append(state + ", ");

	if (zip != null)
	    sb.append(zip + ", ");

	if (phone != null)
	    sb.append(phone + ", ");

	if (fax != null)
	    sb.append(fax + ", ");

	if (email != null)
	    sb.append(email + ", ");

	String contact = sb.toString();
	return contact.substring(0, contact.length() - 2);
    }
}
