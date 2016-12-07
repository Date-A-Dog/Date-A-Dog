var promise = require('bluebird');

var options = {
  // Initialization Options
  promiseLib: promise
};

var pgp = require('pg-promise')(options);

// Database connection details;
var cn = {
    host: 'dad-postgres.clcyrikoceop.us-west-2.rds.amazonaws.com', // 'localhost' is the default;
    port: 5432, // 5432 is the default;
    database: 'dateadog',
    user: 'dadadmin',
    password: 'zOg8sUs87TOu',
    poolSize: 10
};
var db = pgp(cn);

// add query functions
function updateDoggie(dogs, callback) {
  db.tx(function (t) {
        var queries = dogs.map(function (dog) {
            console.log('inserting: ' + dog.id);
            return t.none("INSERT INTO doggies (id, dog) \
                           VALUES ($1, $2) \
                           ON CONFLICT DO NOTHING", [dog.id, JSON.stringify(dog)]);
        });
        return t.batch(queries);
    })
    .then(function (data) {
        // SUCCESS
        console.log(data);
        callback();
    })
    .catch(function (error) {
        // ERROR;
        console.log(error);
        callback(error);
    });
}

function getShelterIds(callback) {
  var query = 'SELECT DISTINCT \
                 dog->>\'shelterId\' AS shelterId \
               FROM doggies';
  db.any(query)
    .then(function (data) {
      callback(null, data);
    })
    .catch(function (error) {
      console.log(error);
    });
}

function updateShelter(shelter) {
  var query = 'INSERT INTO shelters (id, shelter) \
               VALUES ($1, $2) \
               ON CONFLICT DO NOTHING';
  db.none(query, [shelter.id, shelter])
    .then(function () {
      console.log('inserted ' + shelter.id);
    })
    .catch(function (error) {
      console.log(error);
    });
}

module.exports = {
  // Daemon specific queries
  updateDoggie: updateDoggie,
  getShelterIds: getShelterIds,
  updateShelter: updateShelter
};
