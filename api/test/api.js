var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();
// var expect  = chai.expect;
// var request = require("request");

chai.use(chaiHttp);

describe("Date-a-Dog Server", function() {
  describe("Rest API tests", function() {
    it('Should return user profile for test user on /api/loginTest', function(done) {
      chai.request(server)
      .post('/api/loginTest')
      .end(function(err, res){
        res.should.have.status(200);
        res.should.be.json;
        res.body.should.be.a('object');

        // Check id
        res.body.should.have.property('id');
        res.body.id.should.equal(119889308491710);

        // Check email
        res.body.should.have.property('email');
        res.body.id.should.equal('dateadog@gmail.com');

        // Check fname
        res.body.should.have.property('fname');
        res.body.id.should.equal('Sally');

        // Check lname
        res.body.should.have.property('lname');
        res.body.id.should.equal('Smith');

        // Check street
        res.body.should.have.property('street');
        res.body.id.should.equal('Paul G. Allen Center for Computer Science & Engineering (Cse)');

        // Check city
        res.body.should.have.property('city');
        res.body.id.should.equal('Seattle');

        // Check state
        res.body.should.have.property('state');
        res.body.id.should.equal('WA');

        // Check zip
        res.body.should.have.property('zip');
        res.body.id.should.equal('98195');

        // Check phone
        res.body.should.have.property('phone');
        res.body.id.should.equal('(206) 543-1695');

        // Check shelterid
        res.body.should.have.property('shelterid');
        res.body.id.should.equal('WA214');
        done();
      });
    });

    
  });
});
