/*
 * Dog profile is a module which contains shelter dog properties.
 * This module is immutable, and can only be accessed
 * by using its accessor methods.
 *
 */
var DogProfile = function(dogId, name, sex, age, photoURL) {
  // object profile properties
  var profile = {};

  // declare/init dog properties
  profile.id       = dogId;
  profile.name     = name;
  profile.sex      = sex;
  profile.age      = age;
  profile.photoURL = photoURL;

  return profile;
};
if (typeof module !== "undefined" && typeof module.exports !== "undefined") {
 module.exports = DogProfile;
} else {
  window.DogProfile = DogProfile;
}
