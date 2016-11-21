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

// Production queries
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
  console.log('getShelterRequests called');
  var query = 'SELECT json_build_object(\'id\', r.id, \
                                        \'epoch\', r.epoch, \
                                        \'status\', r.status) AS request, \
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

function getShelter(req, res, next) {
  var query = 'SELECT * \
               FROM shelters s \
               WHERE s = $1';
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
    var query = 'INSERT INTO requests (dogId, userId, shelterId, epoch) \
                 SELECT $1, $2, d.dog->>\'shelterId\' AS shelterId, $3\
                 FROM doggies d \
                 WHERE d.id = $1 \
                 ON CONFLICT DO NOTHING';
    db.none(query, [req.body.id, req.user.id, req.body.epoch])
      .then(function() {
          res.sendStatus(200);
      })
      .catch(function(err) {
          return next(err);
      });
}

function updateRequestStatus(req, res, next) {
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

// Demo queries
function loginTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  req.user.first_name = 'Sally';
  req.user.last_name = 'Smith';
  return login(req, res, next);
}

function getNextDogsTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return getNextDogs(req, res, next);
}

function getDogHistoryTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return getDogHistory(req, res, next);
}

function getLikedDogsTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return getLikedDogs(req, res, next);
}

function getDislikedDogsTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return getDislikedDogs(req, res, next);
}

function getShelterRequestsTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return getShelterRequests(req, res, next);
}

function judgeDogTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return judgeDog(req, res, next);
}

function requestDateTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return requestDate(req, res, next);
}

function updateRequestStatusTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return updateRequestStatus(req, res, next);
}

function updateUserTest(req, res, next) {
  req.user = {};
  req.user.id = '119889308491710';
  return updateUser(req, res, next);
}

module.exports = {
  // Rest API specific queries
  login: login,
  getNextDogs: getNextDogs,
  getDogHistory: getDogHistory,
  getLikedDogs: getLikedDogs,
  getDislikedDogs: getDislikedDogs,
  getShelter: getShelter,
  getShelterRequests: getShelterRequests,
  judgeDog: judgeDog,
  requestDate: requestDate,
  updateRequestStatus: updateRequestStatus,
  updateUser: updateUser,

  // Rest API Test specific queries
  loginTest: loginTest,
  getNextDogsTest: getNextDogsTest,
  getDogHistoryTest: getDogHistoryTest,
  getLikedDogsTest: getLikedDogsTest,
  getDislikedDogsTest: getDislikedDogsTest,
  getShelterTest: getShelter,
  getShelterRequestsTest: getShelterRequestsTest,
  judgeDogTest: judgeDogTest,
  requestDateTest: requestDateTest,
  updateRequestStatusTest: updateRequestStatusTest,
  updateUserTest: updateUserTest
};
