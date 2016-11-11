/*
 * Dog profile is a module which contains shelter dog properties.
 * This module is immutable, and can only be accessed
 * by using its accessor methods.
 *
 */
var DogProfile = function(dogId, name, sex, photoURL) {
  // object profile properties
  var profile = {};

  // declare/init dog properties 
  profile.id       = dogId;
  profile.name     = name;
  profile.sex      = sex;
  profile.photoURL = photoURL;

  // log entry
  console.log("DogProfile created: " + profile);
  
  return profile;
};

module.exports = DogProfile;
