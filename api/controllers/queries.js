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
    poolSize: 25
};
var db = pgp(cn);

// add query functions
function getNextDogs(req, res, next) {
  var query = 'SELECT d.dog_id, d.dog_name, d.age, d.sex, d.size, di.image_url, s.shelter_name \
               FROM Dogs d \
               JOIN DogImages di ON d.dog_id = di.dog_id \
               JOIN Shelters s ON d.shelter_id = s.shelter_id \
               WHERE NOT EXISTS (SELECT vd.dog_id \
                 FROM ViewedDogs vd \
                 WHERE vd.user_id = 2 \
                 AND d.dog_id = vd.dog_id) \
               AND di.image_id = 1 \
               AND di.image_size = $1 \
               ORDER BY d.dog_id ASC \
               LIMIT 1';
  db.any(query, ['pn'])
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ALL doggies'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

module.exports = {
  // Rest API specific queries
  getNextDogs: getNextDogs,
  // judgeDog: judgeDog,
  // getRequests: getRequests,
  // updateRequestStatus: updateRequestStatus,
  // requestDate: requestDate,
  // Daemon specific queries
  // updateDoggie: updateDoggie
};
