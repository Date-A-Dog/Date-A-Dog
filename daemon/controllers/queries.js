var promise = require('bluebird');

var options = {
  // Initialization Options
  promiseLib: promise
};

var pgp = require('pg-promise')(options);

// Database connection details;
var cn = {
    host: 'localhost', // 'localhost' is the default;
    port: 5432, // 5432 is the default;
    database: 'dadapidb',
    user: 'dadapisvc',
    password: 'v8FrUD5XavaswezEsu',
    poolSize: 10
};
var db = pgp(cn);

// add query functions
function updateDoggie(dogs) {
  db.tx(function (t) {
        var queries = dogs.map(function (dog) {
            console.log('inserting: ' + dog.id);
            return t.none("INSERT INTO doggies (id, dog) VALUES($1, $2)", [dog.id, JSON.stringify(dog)]);
        });
        return t.batch(queries);
    })
    .then(function (data) {
        // SUCCESS
        console.log(data);
    })
    .catch(function (error) {
        // ERROR;
        console.log(error);
    });
}

module.exports = {
  // Daemon specific queries
  updateDoggie: updateDoggie
};
