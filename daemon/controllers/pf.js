// Import module
var petfinder = require('petfinder')('d025e514d458e4366c42ea3006fd31b3','15196eb6874f8ae2fbbd9c1d613c2ae0');

// Get list of breeds
petfinder.getBreedList('dog', function(err, breeds) {
  // console.log(breeds)
})

// Find pets in the zip code 98105 - University of Washington
var getDogs = function(zipcode, offset, callback) {
  petfinder.findPet('98105', {'animal':'dog', 'count':1000, 'offset': offset}, callback);
}

var getShelter = function(shelterId, callback) {
  petfinder.getShelter(shelterId, {'format':'json'}, callback);
}

module.exports = {
  getDogs: getDogs,
  getShelter: getShelter
};
