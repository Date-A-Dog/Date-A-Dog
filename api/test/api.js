var chai = require('chai');
var chaiHttp = require('chai-http');
var server = require('../app');
var should = chai.should();
// var expect  = chai.expect;
// var request = require("request");

chai.use(chaiHttp);

describe("Date-a-Dog Server Rest API Tests", function() {
  describe("Test endpoint /api/login", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/loginTest')
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });

    it('Response is a valid JSON', function() {
      res.should.be.json;
      res.body.should.be.a('object');
    });

    // Check id
    it('id is correct', function() {
      res.body.should.have.property('id');
      res.body.id.should.equal(119889308491710);
    });

    // Check fname
    it('fname is correct', function() {
      res.body.should.have.property('fname');
      res.body.fname.should.equal('Sally');
    });

    // Check lname
    it('lname is correct', function() {
      res.body.should.have.property('lname');
      res.body.lname.should.equal('Smith');
    });

    // Check email
    it('email is correct', function() {
      res.body.should.have.property('email');
      should.be.null(res.body.email);
    });

    // Check street
    it('street is correct', function() {
      res.body.should.have.property('street');
      should.be.null(res.body.street);
    });

    // Check city
    it('city is correct', function() {
      res.body.should.have.property('city');
      should.be.null(res.body.city);
    });

    // Check state
    it('state is correct', function() {
      res.body.should.have.property('state');
      should.be.null(res.body.state);
    });

    // Check zip
    it('zip is correct', function() {
      res.body.should.have.property('zip');
      should.be.null(res.body.zip);
    });

    // Check phone
    it('phone is correct', function() {
      res.body.should.have.property('phone');
      should.be.null(res.body.phone);
    });

    // Check shelterid
    it('fname is correct', function() {
      res.body.should.have.property('shelterid');
      res.body.shelterid.should.equal('WA214');
    });
  });

  describe("Test endpoint /api/updateUser", function() {
    var res;
    before(function(done) {
      var data = '{ \
        "email": "dateadog@gmail.com", \
        "fname": "Sally", \
        "lname": "Smith", \
        "street": "Paul G. Allen Center for Computer Science & Engineering (Cse)", \
        "city": "Seattle", \
        "state": "WA", \
        "zip": "98195", \
        "phone": "(206) 543-1695", \
      }';
      chai.request(server)
      .post('/api/updateUser')
      .send(data)
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });
  });

  describe("Verify /api/updateUser using /api/login", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/loginTest')
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });

    it('Response is a valid JSON', function() {
      res.should.be.json;
      res.body.should.be.a('object');
    });

    // Check id
    it('id is correct', function() {
      res.body.should.have.property('id');
      res.body.id.should.equal(119889308491710);
    });

    // Check fname
    it('fname is correct', function() {
      res.body.should.have.property('fname');
      res.body.fname.should.equal('Sally');
    });

    // Check lname
    it('lname is correct', function() {
      res.body.should.have.property('lname');
      res.body.lname.should.equal('Smith');
    });

    // Check email
    it('email is correct', function() {
      res.body.should.have.property('email');
      res.body.email.should.equal('dateadog@gmail.com');
    });


    // Check street
    it('street is correct', function() {
      res.body.should.have.property('street');
      res.body.street.should.equal('Paul G. Allen Center for Computer Science & Engineering (Cse)');
    });

    // Check city
    it('city is correct', function() {
      res.body.should.have.property('city');
      res.body.city.should.equal('Seattle');
    });


    // Check state
    it('state is correct', function() {
      res.body.should.have.property('state');
      res.body.state.should.equal('WA');
    });

    // Check zip
    it('zip is correct', function() {
      res.body.should.have.property('zip');
      res.body.zip.should.equal('98195');
    });

    // Check phone
    it('phone is correct', function() {
      res.body.should.have.property('phone');
      res.body.phone.should.equal('(206) 543-1695');
    });

    // Check shelterid
    it('fname is correct', function() {
      res.body.should.have.property('shelterid');
      res.body.shelterid.should.equal('WA214');
    });
  });
});
