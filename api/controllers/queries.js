/**
 * @module controllers/queries
 * @description A module that is used to query the database
 * @author Amarpal Singh <amarpal@cs.washington.edu>
 */

/**
 * @description Use bluebird promise module
 */
var promise = require('bluebird');

/**
 * @namespace
 * @property {module} promiseLib - The bluebird promise module.
 */
var options = {
  promiseLib: promise
};

/**
 * @description Use the promises interface for PostgreSQL.
 */
var pgp = require('pg-promise')(options);

/**
 * @description The database connection "string" settings.
 * @namespace
 * @property {string}  host     - the database host
 * @property {number}  port     - the port number to use when connecting to the host
 * @property {string}  database - the database name
 * @property {string}  username - the database user to login as
 * @property {string}  password - the database user password
 * @property {number}  password - the size of the connection pool
 */
var cn = {
    host: 'dad-postgres.clcyrikoceop.us-west-2.rds.amazonaws.com',
    port: 5432,
    database: 'dateadog',
    user: 'dadadmin',
    password: 'zOg8sUs87TOu',
    poolSize: 25
};
/**
 * @description Create a connection the database.
 */
var db = pgp(cn);

/** Production functions */
function login(req, res, next) {
  // Find or create user in the database
  var query = 'INSERT INTO users (id, fname, lname) \
               VALUES ($1, $2, $3) \
               ON CONFLICT DO NOTHING';
  db.none(query, [req.user.id, req.user.first_name, req.user.last_name])
    .then(function() {
      query = 'SELECT json_build_object(\'id\', u.id, \
                                        \'email\', u.email, \
                                        \'fname\', u.fname, \
                                        \'lname\', u.lname, \
                                        \'street\', u.street, \
                                        \'city\', u.city, \
                                        \'state\', u.state, \
                                        \'zip\', u.zip, \
                                        \'phone\', u.phone, \
                                        \'shelterid\', u.shelterid \
                                      ) as user \
               FROM users u \
               WHERE u.id = $1';
       db.one(query, [req.user.id])
         .then(function (data) {
           res.status(200).json(data.user)
         })
         .catch(function (err) {
           return next(err);
         });
    })
    .catch(function(err) {
      return next(err);
    });
}

function getNextDogs(req, res, next) {
  var query = 'SELECT d.dog \
               FROM doggies d \
               WHERE NOT EXISTS (SELECT j.dogId \
                                 FROM judged j \
                                 WHERE d.id = j.dogId \
                                 AND j.userId = $1) \
               ORDER BY d.id ASC \
               LIMIT $2';
  db.any(query, [req.user.id, req.body.count])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function getDateRequestsStatus(req, res, next) {
  var query = 'SELECT json_build_object(\'id\', r.id, \
                                        \'epoch\', r.epoch, \
                                        \'status\', r.status, \
                                        \'feedback\', r.feedback) AS request, \
                      d.dog AS dog, \
                      s.shelter AS shelter, \
                      json_build_object(\'id\', u.id, \
                                        \'email\', u.email, \
                                        \'fname\', u.fname, \
                                        \'lname\', u.lname, \
                                        \'street\', u.street, \
                                        \'city\', u.city, \
                                        \'state\', u.state, \
                                        \'zip\', u.zip, \
                                        \'phone\', u.phone) AS user \
               FROM requests r \
               JOIN doggies d ON d.id = r.dogId \
               JOIN shelters s ON s.id = r.shelterId \
               JOIN users u ON  u.id = r.userId \
               WHERE r.userId = $1 \
               ORDER BY r.epoch ASC';
  db.any(query, [req.user.id])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function getDogHistory(req, res, next) {
  var query = 'SELECT d.dog, j.liked \
               FROM doggies d JOIN judged j ON d.id = j.dogId \
               WHERE j.userId = $1 \
               ORDER BY j.epoch DESC';
  db.any(query, [req.user.id])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function getLikedDogs(req, res, next) {
  var query = 'SELECT d.dog \
               FROM doggies d JOIN judged j ON d.id = j.dogId \
               WHERE j.userId = $1 \
               AND j.liked = $2 \
               ORDER BY j.epoch DESC';
  db.any(query, [req.user.id, 'TRUE'])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function getDislikedDogs(req, res, next) {
  var query = 'SELECT d.dog \
               FROM doggies d JOIN judged j ON d.id = j.dogId \
               WHERE j.userId = $1 \
               AND j.liked = $2 \
               ORDER BY j.epoch DESC';
  db.any(query, [req.user.id, 'FALSE'])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function getShelterRequests(req, res, next) {
  if (req.user.id == 119889308491710) {
    var query = 'SELECT json_build_object(\'id\', r.id, \
                                      \'epoch\', r.epoch, \
                                      \'status\', r.status, \
                                      \'reason\', r.reason, \
                                      \'feedback\', r.feedback) AS request, \
                    d.dog AS dog, \
                    s.shelter AS shelter, \
                    json_build_object(\'id\', u.id, \
                                      \'email\', u.email, \
                                      \'fname\', u.fname, \
                                      \'lname\', u.lname, \
                                      \'street\', u.street, \
                                      \'city\', u.city, \
                                      \'state\', u.state, \
                                      \'zip\', u.zip, \
                                      \'phone\', u.phone) AS user \
             FROM requests r \
             JOIN doggies d ON d.id = r.dogId \
             JOIN shelters s ON s.id = r.shelterId \
             JOIN users u ON  u.id = r.userId \
             ORDER BY r.epoch ASC';
    db.any(query, [req.user.id])
      .then(function(data) {
        res.status(200).json(data)
      })
      .catch(function(err) {
        return next(err);
      });
  } else {
    var query = 'SELECT json_build_object(\'id\', r.id, \
                                          \'epoch\', r.epoch, \
                                          \'status\', r.status, \
                                          \'reason\', r.reason, \
                                          \'feedback\', r.feedback) AS request, \
                        d.dog AS dog, \
                        s.shelter AS shelter, \
                        json_build_object(\'id\', u.id, \
                                          \'email\', u.email, \
                                          \'fname\', u.fname, \
                                          \'lname\', u.lname, \
                                          \'street\', u.street, \
                                          \'city\', u.city, \
                                          \'state\', u.state, \
                                          \'zip\', u.zip, \
                                          \'phone\', u.phone) AS user \
                 FROM requests r \
                 JOIN doggies d ON d.id = r.dogId \
                 JOIN shelters s ON s.id = r.shelterId \
                 JOIN users u ON  u.id = r.userId \
                 JOIN users v ON v.shelterId = r.shelterId \
                 WHERE v.id = $1 \
                 ORDER BY r.epoch ASC';
    db.any(query, [req.user.id])
      .then(function(data) {
        res.status(200).json(data)
      })
      .catch(function(err) {
        return next(err);
      });
  }
}

function getShelter(req, res, next) {
  var query = 'SELECT s.shelter \
               FROM shelters s \
               WHERE s.id = $1';
  db.one(query, [req.body.shelterId])
    .then(function(shelter) {
      res.status(200).json(shelter);
    })
    .catch(function(err) {
      return next(err);
    });
}

function judgeDog(req, res, next) {
  var query = 'INSERT INTO judged (userId, dogId, epoch, liked) \
               VALUES ($1, $2, $3, $4) \
               ON CONFLICT (userId, dogId) \
               DO UPDATE SET epoch = $3, liked = $4';
  db.none(query, [req.user.id, req.body.id, req.body.epoch, req.body.liked])
    .then(function () {
      res.sendStatus(200);
    })
    .catch(function (err) {
      return next(err);
    });
}

function requestDate(req, res, next) {
    var query = 'INSERT INTO requests (dogId, userId, shelterId, epoch, reason) \
                 SELECT $1, $2, d.dog->>\'shelterId\' AS shelterId, $3, $4 \
                 FROM doggies d \
                 WHERE d.id = $1 \
                 ON CONFLICT DO NOTHING';
    db.none(query, [req.body.id, req.user.id, req.body.epoch, req.body.reason])
      .then(function() {
          res.sendStatus(200);
      })
      .catch(function(err) {
          return next(err);
      });
}

function updateRequestStatus(req, res, next) {
  if (typeof(req.body.reason) != "undefined") {
    var query = 'UPDATE requests \
                 SET status = $1, reason = $2 \
                 WHERE id = $3';
    db.none(query, [req.body.status, req.body.reason, req.body.id])
      .then(function () {
        res.sendStatus(200);
      })
      .catch(function (err) {
        return next(err);
      });
  } else if (typeof(req.body.feedback) != 'undefined') {
    var query = 'UPDATE requests \
                 SET status = $1, feedback = $2 \
                 WHERE id = $3';
    db.none(query, [req.body.status, req.body.feedback, req.body.id])
      .then(function () {
        res.sendStatus(200);
      })
      .catch(function (err) {
        return next(err);
      });
  } else {
    var query = 'UPDATE requests \
                 SET status = $1 \
                 WHERE id = $2';
    db.none(query, [req.body.status, req.body.id])
      .then(function () {
        res.sendStatus(200);
      })
      .catch(function (err) {
        return next(err);
      });
  }
}

function updateUser(req, res, next) {
    var query = 'UPDATE users \
                 SET email = $1, \
                     fname = $2, \
                     lname = $3, \
                     street = $4, \
                     city = $5, \
                     state = $6, \
                     phone = $7, \
                     zip = $8 \
                 WHERE id = $9';
    db.none(query, [req.body.email, req.body.fname, req.body.lname,
                    req.body.street, req.body.city, req.body.state,
                    req.body.phone, req.body.zip, req.user.id])
        .then(function () {
            res.sendStatus(200);
        })
        .catch(function (err) {
            return next(err);
        });
}

// Test user
var testUser = {};
testUser.id = 10153811720940946;
testUser.email = 'amarpal@dateadog.com';
testUser.fname ='Amarpal';
testUser.lname = 'Singh';
testUser.street = 'Paul G. Allen Center for Computer Science & Engineering (Cse)';
testUser.city = 'Seattle';
testUser.state = 'WA';
testUser.zip = '98105';
testUser.phone = '(206) 850-6944';
testUser.shelterid = 'WA214';

/** Test functions */
function loginTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  req.user.first_name = testUser.fname;
  req.user.last_name = testUser.lname;
  return login(req, res, next);
}

function getNextDogsTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getNextDogs(req, res, next);
}

function getDateRequestsStatusTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getDateRequestsStatus(req, res, next);
}

function getDogHistoryTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getDogHistory(req, res, next);
}

function getLikedDogsTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getLikedDogs(req, res, next);
}

function getDislikedDogsTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getDislikedDogs(req, res, next);
}

function getShelterTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getShelter(req, res, next);
}

function getShelterRequestsTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return getShelterRequests(req, res, next);
}

function judgeDogTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return judgeDog(req, res, next);
}

function requestDateTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return requestDate(req, res, next);
}

function updateRequestStatusTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return updateRequestStatus(req, res, next);
}

function updateUserTest(req, res, next) {
  req.user = {};
  req.user.id = testUser.id;
  return updateUser(req, res, next);
}

module.exports = {
  /** Create or find the user and return the current user profile */
  login: login,

  /** Get the next unjudged dogs to present for the test user */
  getNextDogs: getNextDogs,

  /** */
  getDateRequestsStatus : getDateRequestsStatus,
  getDogHistory: getDogHistory,
  getLikedDogs: getLikedDogs,
  getDislikedDogs: getDislikedDogs,
  getShelter: getShelter,
  getShelterRequests: getShelterRequests,
  judgeDog: judgeDog,
  requestDate: requestDate,
  updateRequestStatus: updateRequestStatus,
  updateUser: updateUser,


  /** Create or find the test user and return the current test user profile */
  loginTest: loginTest,

  /** Get the next unjudged dogs to present for the test user */
  getNextDogsTest: getNextDogsTest,

  /** Get the date request status for the test user */
  getDateRequestsStatusTest : getDateRequestsStatusTest,

  /** Get the combined liked / disliked dog history for the test user */
  getDogHistoryTest: getDogHistoryTest,

  /** Get the history of the dogs that were liked for the test user */
  getLikedDogsTest: getLikedDogsTest,

  /** Get the history of dogs that were disliked for the test user */
  getDislikedDogsTest: getDislikedDogsTest,

  /** Get the shelter profile for the provided shelter id */
  getShelterTest: getShelterTest,

  /** Get the requests for the specific shelter that test user is responsible for */
  getShelterRequestsTest: getShelterRequestsTest,

  /** Judge a dog as the test user */
  judgeDogTest: judgeDogTest,

  /** Request a date as the test user */
  requestDateTest: requestDateTest,

  /** Update the request status as the test user */
  updateRequestStatusTest: updateRequestStatusTest,

  /** Update the test user porfile */
  updateUserTest: updateUserTest
};
