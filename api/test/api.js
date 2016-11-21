var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();
// var expect  = chai.expect;
// var request = require("request");

describe("Date-a-Dog Server", function() {
  describe("Rest API tests", function() {
    it('Should return user profile for test user on /api/loginTest', function(done) {
      chai.request(server)
      .post('/api/loginTest')
      .end(function(err, res){
        res.should.have.status(200);
        res.should.be.json;
        res.body.should.be.a('object');
        res.body.should.have.property('id');
        res.body.id.should.equal('119889308491710');
        done();
      });
    });
  });
});
