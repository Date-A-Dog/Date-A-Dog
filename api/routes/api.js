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
router.post("/updateUser", auth.isAuthenticated, db.updateUser);

// Demo endpoints
router.post("/loginTest", db.loginTest);
router.post("/getNextDogsTest", db.getNextDogsTest);
router.post("/getDogHistoryTest", db.getDogHistoryTest);
router.post("/getLikedDogsTest", db.getLikedDogsTest);
router.post("/getDislikedDogsTest", db.getDislikedDogsTest);
router.post("/getShelterTest", db.getShelterTest);
router.post("/getShelterRequestsTest", db.getShelterRequestsTest);
router.post("/judgeDogTest", db.judgeDogTest);
router.post("/requestDateTest", db.requestDateTest);
router.post("/updateRequestStatusTest", db.updateRequestStatusTest);
router.post("/updateUserTest", db.updateUserTest);

module.exports = router;
