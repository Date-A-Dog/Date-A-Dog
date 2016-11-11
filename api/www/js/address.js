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
  
  console.log("Address object constructed: " + address);
  return address;
};

module.exports = Address;
