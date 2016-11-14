var express = require('express');
var router = express.Router();
var auth = require('../controllers/auth');
var db = require('../controllers/queries');

/* Basic welcome page. */
router.get('/', function(req, res, next) {
  res.send('Welcome to the Date-A-Dog API.');
});

// Production endpoints
router.post("/login", auth.isAuthenticated, db.login);
router.post("/getNextDogs", auth.isAuthenticated, db.getNextDogs);
router.post("/getDogHistory", auth.isAuthenticated, db.getDogHistory);
router.post("/getLikedDogs", auth.isAuthenticated, db.getLikedDogs);
router.post("/getDislikedDogs", auth.isAuthenticated, db.getDislikedDogs);
router.post("/getShelter", auth.isAuthenticated, db.getShelter);
router.post("/getShelterRequests", auth.isAuthenticated, db.getShelterRequests);
router.post("/judgeDog", auth.isAuthenticated, db.judgeDog);
router.post("/requestDate", auth.isAuthenticated, db.requestDate);
router.post("/updateRequestStatus", auth.isAuthenticated, db.updateRequestStatus);
router.post("/updateUserApplication", auth.isAuthenticated, db.updateUserApplication);

// Demo endpoints
router.post("/loginDemo", db.loginDemo);
router.post("/getNextDogsDemo", db.getNextDogsDemo);
router.post("/getDogHistoryDemo", db.getDogHistoryDemo);
router.post("/getLikedDogsDemo", db.getLikedDogsDemo);
router.post("/getDislikedDogsDemo", db.getDislikedDogsDemo);
router.post("/getShelterDemo", db.getShelterDemo);
router.post("/getShelterRequestsDemo", db.getShelterRequestsDemo);
router.post("/judgeDogDemo", db.judgeDogDemo);
router.post("/requestDateDemo", db.requestDateDemo);
router.post("/updateRequestStatusDemo", db.updateRequestStatusDemo);
router.post("/updateUserApplicationDemo", db.updateUserApplicationDemo);

module.exports = router;
