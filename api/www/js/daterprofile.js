/*
 * daterprofile.js is a module encapsulating a user profile.
 *
 */
var DaterProfile =function(fName,lName, email, phone, address) {
  // dater profile properties
  var profile = {};
  profile.fName = fName;
  profile.lName = lName;
  profile.email = email;
  profile.phone = phone;
  profile.address = address;

  // log entry
  console.log("Dater profile object created: " + profile);

  return profile;
}

module.exports = DaterProfile;
