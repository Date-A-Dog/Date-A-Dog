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
    database: '',
    user: 'dadadmin',
    password: 'zOg8sUs87TOu',
    poolSize: 25
};
var db = pgp(connectionString);

// add query functions
function getNextDogs(req, res, next) {
  var query = '';
  db.any(query, options)
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ALL puppies'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

module.exports = {
  // Rest API specific queries
  getNextDogs: getNextDogs,
  judgeDog: judgeDog,
  getRequests: getRequests,
  updateRequestStatus: updateRequestStatus,
  requestDate: requestDate,
  // Daemon specific queries
  updateDoggie: updateDoggie
};
