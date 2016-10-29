var express = require("express");
var app = express();

app.get("/getNextDogs", function(req, res) {
  res.send(200);
});

app.listen(3000);
