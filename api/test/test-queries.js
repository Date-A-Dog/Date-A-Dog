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

function cleanTestRequests(id, next) {
  var query = 'DELETE FROM requests \
               WHERE userid=$1';
  db.none(query, [id])
      .then(function () {
      })
      .catch(function (err) {
          return next(err);
      });
}

function cleanTestJudged(id, next) {
  var query = 'DELETE FROM judged \
               WHERE userid=$1';
  db.none(query, [id])
      .then(function () {
      })
      .catch(function (err) {
          return next(err);
      });
}

module.exports = {
  // Test specific queries
  cleanTestRequests: cleanTestRequests,
  cleanTestJudged: cleanTestJudged,
};
