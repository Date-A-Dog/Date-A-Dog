var pf = require('./controllers/pf');
var db = require('./controllers/queries');
console.log("Calling pf.getDogs");

pf.getDogs('98105', 0, function(err, dogs) {
  for (var key in dogs[0]) {
    if (dogs[0].hasOwnProperty(key)) {
        console.log(key);
    }
  }
  console.log('Inserting doggies into database...');
  db.updateDoggie(dogs);
  console.log('Complete.')
});
