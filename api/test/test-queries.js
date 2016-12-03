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
    poolSize: 2
};
var db = pgp(cn);

function assignTestUserToShelter(user, next) {
  var query = 'UPDATE users SET shelterid = $1 \
               WHERE id = $2';
  db.none(query, [user.shelterid, user.id])
      .then(function () {
        return next();
      })
      .catch(function (err) {
          return next(err);
      });
}

function cleanTestRequests(id, next) {
  var query = 'DELETE FROM requests \
               WHERE userid = $1';
  db.none(query, id)
      .then(function () {
        return next();
      })
      .catch(function (err) {
          return next(err);
      });
}

function cleanTestJudged(id, next) {
  var query = 'DELETE FROM judged \
               WHERE userid = $1';
  db.none(query, id)
      .then(function () {
        return next();
      })
      .catch(function (err) {
          return next(err);
      });
}

function removeTestUser(id, next) {
  var query = 'DELETE FROM judged \
               WHERE userid = $1';
  db.none(query, id)
      .then(function () {
        return next();
      })
      .catch(function (err) {
          return next(err);
      });
}

module.exports = {
  // Test specific queries
  assignTestUserToShelter: assignTestUserToShelter,
  cleanTestRequests: cleanTestRequests,
  cleanTestJudged: cleanTestJudged,
  removeTestUser: removeTestUser,
};
