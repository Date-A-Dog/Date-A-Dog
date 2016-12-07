/**
 * Adress represents a physical address for a user,
 * and is composed of street, city, state, and zipcode
 */
var Address = function(street, city, state, zipcode) {

    var address = {};
    address.street = street;
    address.city = city;
    address.state = state;
    address.zipcode = zipcode;
    return address;
};

/* Export this file only when testing */
if (typeof module !== "undefined" && typeof module.exports !== "undefined") {
    module.exports = Address;
} else {
    window.Address = Address;
}
