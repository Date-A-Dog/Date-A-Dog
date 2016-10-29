var express = require('express');
var router = express.Router();
var auth = require('../controllers/auth');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

/* GET dog profile listing. */
router.post("/getNextDogs", auth.isAuthenticated, function(req, res) {
  res.status(200).send(JSON.stringify(req.user));
});

module.exports = router;
