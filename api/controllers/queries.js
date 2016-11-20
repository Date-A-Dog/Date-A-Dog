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
  var query = 'SELECT d.dog \
               FROM doggies d JOIN shelters s ON d.dog->>\'shelterId\' = s.id \
               WHERE NOT EXISTS (SELECT j.dogId \
                                 FROM judged j \
                                 WHERE d.id = j.dogId \
                                   AND j.userId = $1 \
                                   AND d.dog->>\'zip\' = $2) \
               ORDER BY d.id ASC \
               LIMIT $3';
  db.any(query, [req.user.id, '98105', '20'])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function getNextDogsDemo(req, res, next) {
  var query = 'SELECT d.dog \
               FROM doggies d JOIN shelters s ON d.dog->>\'shelterId\' = s.id \
               WHERE NOT EXISTS (SELECT j.dogId \
                                 FROM judged j \
                                 WHERE d.id = j.dogId \
                                   AND j.userId = $1 \
                                   AND d.dog->>\'zip\' = $2) \
               ORDER BY d.id ASC \
               LIMIT $3';
  db.any(query, ['119889308491710', '98105', '20'])
    .then(function (data) {
      res.status(200).json(data);
    })
    .catch(function (err) {
      return next(err);
    });
}

function judgeDog(req, res, next) {
  var query = 'INSERT INTO judged (userId, dogId, liked, epoch) \
               VALUES ($1, $2, $3, $4) \
               ON CONFLICT (userId, dogId) \
               DO UPDATE SET liked = $3';
  db.none(query, [req.user.id, req.body.id, req.body.liked, req.body.epoch])
    .then(function () {
      res.sendStatus(200);
    })
    .catch(function (err) {
      return next(err);
    });
}

function judgeDogDemo(req, res, next) {
  var query = 'INSERT INTO judged (userId, dogId, liked, epoch) \
               VALUES ($1, $2, $3, $4) \
               ON CONFLICT (userId, dogId) \
               DO UPDATE SET liked = $3, epoch = $4';
  db.none(query, ['119889308491710', req.body.id, req.body.liked, req.body.epoch])
    .then(function () {
      res.sendStatus(200);
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
               WHERE v.id = $1';
  db.any(query, [req.user.id])
    .then(function(data) {
      res.status(200).json(data)
    })
    .catch(function(err) {
      return next(err);
    });
}

function getShelterRequestsDemo(req, res, next) {
  console.log('getShelterRequests called');
  var query = 'SELECT json_build_object(\'id\', r.id, \
                                        \'epoch\', r.epoch, \
                                        \'status\', r.status) AS request, \
                      d.dog AS dog, \
                      s.shelter AS shelter, \
                      json_build_object(\'id\', u.id, \
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
               WHERE r.shelterId = $1';
  db.any(query, ['WA214'])
    .then(function(data) {
      res.status(200).json(data)
    })
    .catch(function(err) {
      return next(err);
    });
}

function getZipcodes(zipcode, distance) {
  var PythonShell = require('python-shell');

  var options = {
    mode: 'text',
    pythonPath: '/usr/bin/python',
    scriptPath: '../zipcodes/zipcode_tools',
    args: [zipcode, distance]
  };

  PythonShell.run('main.py', options, function(err, res){
    if(err) throw err;
    return res;
  });
}


module.exports = {
  // Rest API specific queries
  login: login,
  getNextDogs: getNextDogs,
  getNextDogsDemo: getNextDogsDemo,
  // getDogHistory: getDogHistory,
  // getDogHistoryDemo: getDogHistoryDemo,
  // getLikedDogs: getLikedDogs,
  // getDislikedDogs: getDislikedDogs,
  judgeDog: judgeDog,
  judgeDogDemo: judgeDogDemo,
  getShelterRequests: getShelterRequests,
  getShelterRequestsDemo: getShelterRequestsDemo,
  // getShelter: getShelter,
  // updateRequestStatus: updateRequestStatus,
  // requestDate: requestDate,
  // updateUserApplication: updateUserApplication
};
