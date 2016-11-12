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
    poolSize: 25
};
var db = pgp(cn);

function login(req, res, next) {
  // Find or create user in the database
  console.log(req.user);
  var query = 'INSERT INTO users (id, fname, lname) \
               VALUES ($1, $2, $3) \
               ON CONFLICT DO NOTHING';
  db.none(query, [req.user.id, req.user.first_name, req.user.last_name])
    .then(function() {
      res.status(200).json(req.user);
    })
    .catch(function(err) {
      return next(err);
    });
}

function getNextDogs(req, res, next) {
  console.log(req.user.id);
  console.log(req.body.zip);
  var query = 'SELECT d.dog \
               FROM doggies d JOIN shelters s ON d.dog->>\'shelterId\' = s.id \
               WHERE NOT EXISTS (SELECT j.dogId \
                                 FROM judged j \
                                 WHERE d.id = j.dogId \
                                   AND j.userId = $1 \
                                   AND d.dog->>\'zip\' = $2) \
               ORDER BY d.id ASC \
               LIMIT $3';
  db.any(query, [req.user.id, req.body.zip, req.body.count])
    .then(function (data) {
      console.log(data);
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

module.exports = {
  // Rest API specific queries
  login: login,
  getNextDogs: getNextDogs,
  // getDogHistory: getDogHistory,
  // getLikedDogs: getLikedDogs,
  // getDislikedDogs: getDislikedDogs,
  // judgeDog: judgeDog,
  // getShelterRequests: getShelterRequests,
  // getShelterRequestsDemo: getShelterRequestsDemo,
  // getShelter: getShelter,
  // updateRequestStatus: updateRequestStatus,
  // requestDate: requestDate,
  // updateUserApplication: updateUserApplication
};
