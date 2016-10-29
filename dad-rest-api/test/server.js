var expect  = require("chai").expect;
var request = require("request");

describe("Date-a-Dog Server", function() {
  describe("Respond to requests", function() {
    var url = "http://localhost:3000/getNextDogs"
    it("return status 200", function() {
      request(url, function(error, response, body) {
        expect(response.statusCode).to.equal(200);
        done();
      });
    });
  });
});
