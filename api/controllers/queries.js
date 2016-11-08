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
                 WHERE vd.user_id = $1 \
                 AND d.dog_id = vd.dog_id) \
               AND di.image_id = 1 \
               AND di.image_size = $2 \
               ORDER BY d.dog_id ASC \
               LIMIT $3';
  db.any(query, [2, 'pn', 3])
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'retrieved next doggies'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

// add query functions
function judgeDog(req, res, next) {
  var query = 'INSERT INTO ViewedDogs (user_id, dog_id, liked) \
                 VALUES ($1, $2, $3)';
  db.none(query, req.user.id, req.body.dog_id, req.body.liked)
    .then(function () {
      res.status(200)
        .json({
          status: 'success',
          message: 'judged dog ' + req.body.dog_id
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

function createUser(req, res, next) {
  var query = 'INSERT INTO Users (email, full_name) \
               VALUES ($1, $2)';
  db.none(query, [req.user.email, req.user.name])
    .then(function () {
      res.status(200)
        .json({
          status: 'success',
          message: 'created user ' + req.user.name
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

function getDateRequestDetail(req, res, next) {
  var query = 'SELECT r.dog_id, r.user_id, r.requested_date, r.status, \
                      d.dog_name, u.email, u.full_name, u.street, u.city, \
                      u.state, u.zipcode, u.phone \
               FROM Request r \
                 JOIN Users u ON r.user_id = u.user_id \
                 JOIN Dogs d ON r.dog_id = d.dog_id \
               WHERE r.req_id = $1';
  db.one(query, [req.body.request_id])
    .then(function (data) {
    res.status(200)
      .json({
        status: 'success',
        data: data,
        message: 'retrieved request details for request  ' + req.body.request_id
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
  getDateRequestDetail: getDateRequestDetail,
  createUser: createUser
};
