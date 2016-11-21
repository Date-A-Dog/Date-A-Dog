var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();
// var expect  = chai.expect;
// var request = require("request");

chai.use(chaiHttp);

describe("Date-a-Dog Server Rest API Tests", function() {
  describe("Should return user profile for test user on /api/loginTest", function() {
    chai.request(server)
    .post('/api/loginTest')
    .end(function(err, res){
      it('Response is OK', function(done) {
        res.should.have.status(200);
        done();
      });

      it('Response is a valid JSON', function(done) {
        res.should.be.json;
        res.body.should.be.a('object');
        done();
      });

      // Check id
      it('id is correct', function(done) {
        res.body.should.have.property('id');
        res.body.id.should.equal(119889308491710);
        done();
      });

      // Check email
      it('email is correct', function(done) {
        res.body.should.have.property('email');
        res.body.email.should.equal('dateadog@gmail.com');
        done();
      });

      // Check fname
      it('fname is correct', function(done) {
        res.body.should.have.property('fname');
        res.body.fname.should.equal('Sally');
        done();
      });

      // Check lname
      it('lname is correct', function(done) {
        res.body.should.have.property('lname');
        res.body.lname.should.equal('Smith');
        done();
      });


      // Check street
      it('street is correct', function(done) {
        res.body.should.have.property('street');
        res.body.street.should.equal('Paul G. Allen Center for Computer Science & Engineering (Cse)');
        done();
      });

      // Check city
      it('city is correct', function(done) {
        res.body.should.have.property('city');
        res.body.city.should.equal('Seattle');
        done();
      });


      // Check state
      it('state is correct', function(done) {
        res.body.should.have.property('state');
        res.body.state.should.equal('WA');
        done();
      });

      // Check zip
      it('zip is correct', function(done) {
        res.body.should.have.property('zip');
        res.body.zip.should.equal('98195');
        done();
      });

      // Check phone
      it('phone is correct', function(done) {
        res.body.should.have.property('phone');
        res.body.phone.should.equal('(206) 543-1695');
        done();
      });

      // Check shelterid
      it('fname is correct', function(done) {
        res.body.should.have.property('shelterid');
        res.body.shelterid.should.equal('WA214');
        done();
      });
    });
  });
});
