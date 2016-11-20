var express = require('express');
var router = express.Router();
var auth = require('../controllers/auth');
var db = require('../controllers/queries');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('Welcome to the Date-A-Dog API.');
});

/*
login: login,
getNextDogs: getNextDogs,
getDogHistory: getDogHistory,
getLikedDogs: getLikedDogs,
getDislikedDogs: getDislikedDogs,
judgeDog: judgeDog,
getShelterRequests: getShelterRequests,
getShelterRequestsDemo: getShelterRequestsDemo,
getShelter: getShelter,
updateRequestStatus: updateRequestStatus,
requestDate: requestDate,
updateUserApplication: updateUserApplication
*/

/* GET dog profile listing. */
router.post("/login", auth.isAuthenticated, db.login);
router.post("/getNextDogs", auth.isAuthenticated, db.getNextDogs);
router.post("/getNextDogsDemo", db.getNextDogsDemo);
// router.post("/getDogHistory", auth.isAuthenticated, db.getDogHistory);
// router.post("/getDogHistoryDemo", db.getDogHistory);
// router.post("/getLikedDogs", auth.isAuthenticated, db.getLikedDogs);
// router.post("/getDislikedDogs", auth.isAuthenticated, db.getDislikedDogs);
router.post("/judgeDog", auth.isAuthenticated, db.judgeDog);
router.post("/judgeDogDemo", db.judgeDogDemo);
router.post("/getShelterRequests", auth.isAuthenticated, db.getShelterRequests);
router.post("/getShelterRequestsDemo", db.getShelterRequestsDemo); // For Demo
// router.post("/getShelter", auth.isAuthenticated, db.getShelter);
// router.post("/requestDate", auth.isAuthenticated, db.requestDate);
// router.post("/updateUserApplication", auth.isAuthenticated, db.updateUserApplication);

module.exports = router;
