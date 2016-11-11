var express = require('express');
var router = express.Router();
var auth = require('../controllers/auth');
var db = require('../controllers/queries');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

/* GET dog profile listing. */
router.post("/getNextDogs", auth.isAuthenticated, db.getNextDogs);
router.post("/judgeDog", auth.isAuthenticated, db.judgeDog);
router.post("/getDateRequestDetail", auth.isAuthenticated, db.getDateRequestDetail);
router.post("/createUser", auth.isAuthenticated, db.createUser);
router.post("/getHugosRequest", db.getHugosRequest);

module.exports = router;
