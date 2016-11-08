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

module.exports = router;
