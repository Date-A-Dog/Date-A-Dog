var pf = require('./controllers/pf');
var db = require('./controllers/queries');
console.log("Calling pf.getDogs");

pf.getDogs('98105', 1000, function(err, dogs) {
  for (var key in dogs[0]) {
    if (dogs[0].hasOwnProperty(key)) {
        console.log(key);
    }
  }
  console.log('Inserting doggies into database...');
  db.updateDoggie(dogs, function(err) {
    db.getShelterIds(function(err, shelterIds) {
      shelters = [];
      var shelterIdsLength = shelterIds.length;
      for (var i = 0; i < shelterIdsLength; i++) {
        pf.getShelter(shelterIds[i].shelterid, function(err, shelter) {
          db.updateShelter(shelter);
        })
      }
    })
  });
  console.log('Complete.')

});

// First get dogs to populate dog table
// Then get shetlerID from dog table for all dogs
// Then get shelterInfo from petfinder for each shetlerID
// Insert shetlerInfo into shetlers table
