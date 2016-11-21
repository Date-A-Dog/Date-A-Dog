var chai = require('chai');
var chaiHttp = require('chai-http');
var chaiThings = require('chai-things');
var server = require('../app');
var should = chai.should();
var expect  = chai.expect;
// var request = require("request");

chai.use(chaiHttp);
chai.use(chaiThings);

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

    it('id is correct', function() {
      res.body.should.have.property('id');
      res.body.id.should.equal(119889308491710);
    });

    it('fname is correct', function() {
      res.body.should.have.property('fname');
      res.body.fname.should.equal('Sally');
    });

    it('lname is correct', function() {
      res.body.should.have.property('lname');
      res.body.lname.should.equal('Smith');
    });

    it('email is correct', function() {
      res.body.should.have.property('email');
      should.not.exist(res.body.email);
    });

    it('street is correct', function() {
      res.body.should.have.property('street');
      should.not.exist(res.body.street);
    });

    it('city is correct', function() {
      res.body.should.have.property('city');
      should.not.exist(res.body.city);
    });

    it('state is correct', function() {
      res.body.should.have.property('state');
      should.not.exist(res.body.state);
    });

    it('zip is correct', function() {
      res.body.should.have.property('zip');
      should.not.exist(res.body.zip);
    });

    it('phone is correct', function() {
      res.body.should.have.property('phone');
      should.not.exist(res.body.phone);
    });

    it('fname is correct', function() {
      res.body.should.have.property('shelterid');
      res.body.shelterid.should.equal('WA214');
    });
  });

  describe("Test endpoint /api/updateUser", function() {
    var res;
    before(function(done) {;
      chai.request(server)
      .post('/api/updateUserTest')
      .send({
        'email': 'dateadog@gmail.com',
        'fname': 'Sally',
        'lname': 'Smith',
        'street': 'Paul G. Allen Center for Computer Science & Engineering (Cse)',
        'city': 'Seattle',
        'state': 'WA',
        'zip': '98195',
        'phone': '(206) 543-1695',
      })
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

    it('id is correct', function() {
      res.body.should.have.property('id');
      res.body.id.should.equal(119889308491710);
    });

    it('fname is correct', function() {
      res.body.should.have.property('fname');
      res.body.fname.should.equal('Sally');
    });

    it('lname is correct', function() {
      res.body.should.have.property('lname');
      res.body.lname.should.equal('Smith');
    });

    it('email is correct', function() {
      res.body.should.have.property('email');
      res.body.email.should.equal('dateadog@gmail.com');
    });

    it('street is correct', function() {
      res.body.should.have.property('street');
      res.body.street.should.equal('Paul G. Allen Center for Computer Science & Engineering (Cse)');
    });

    it('city is correct', function() {
      res.body.should.have.property('city');
      res.body.city.should.equal('Seattle');
    });

    it('state is correct', function() {
      res.body.should.have.property('state');
      res.body.state.should.equal('WA');
    });

    it('zip is correct', function() {
      res.body.should.have.property('zip');
      res.body.zip.should.equal('98195');
    });

    it('phone is correct', function() {
      res.body.should.have.property('phone');
      res.body.phone.should.equal('(206) 543-1695');
    });

    it('fname is correct', function() {
      res.body.should.have.property('shelterid');
      res.body.shelterid.should.equal('WA214');
    });
  });

  var dogid;
  describe("Test endpoint /api/getNextDogs", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/getNextDogsTest')
      .send({
        'count': '20',
      })
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });

    it('Response is a valid JSON Array', function() {
      res.should.be.json;
      res.body.should.be.Array;
    });

    it('Response is a valid length (number of dogs)', function() {
      res.body.should.have.length(20);
    });

    it('dog exists', function() {
      res.body[0].should.have.property('dog');
    });

    it('dog.id exists', function() {
      res.body[0].dog.should.have.property('id');
      dogid = res.body[0].id;
    });

    it('dog.age exists', function() {
      res.body[0].dog.should.have.property('age');
    });

    it('dog.mix exists', function() {
      res.body[0].dog.should.have.property('mix');
    });

    it('dog.sex exists', function() {
      res.body[0].dog.should.have.property('sex');
    });

    it('dog.size exists', function() {
      res.body[0].dog.should.have.property('size');
    });

    it('dog.media exists', function() {
      res.body[0].dog.should.have.property('media');
    });

    it('dog.media.photos exists', function() {
      res.body[0].dog.media.should.have.property('photos');
    });

    it('dog.breeds exists', function() {
      res.body[0].dog.should.have.property('breeds');
    });

    it('dog.status exists', function() {
      res.body[0].dog.should.have.property('status');
    });

    it('dog.contact exists', function() {
      res.body[0].dog.should.have.property('contact');
    });

    it('dog.shelterId exists', function() {
      res.body[0].dog.should.have.property('shelterId');
    });

    it('dog.description exists', function() {
      res.body[0].dog.should.have.property('description');
    });
  })

  describe("Test like a dog using endpoint /api/judgeDog", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/judgeDog')
      .send({
        'id': dogid,
        'liked': 'TRUE',
        'epoch': (new Date).getTime(),
      })
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });
  })

  describe("Test endpoint /api/getLikedDogs", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/getLikedDogsTest')
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

    it('Response has at least 1 dog', function() {
      expect(res.body).to.be.at.least(1);
    });

    it('Response has the liked dog', function() {
      res.body[0].should.have.property('dog');
      res.body[0].dog.should.have.property('id');
      res.body[0].dog.id.should.equal(dogid);
    });
  })

  describe("Test dislike a dog using endpoint /api/judgeDog", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/judgeDog')
      .send({
        'id': dogid,
        'liked': 'FALSE',
        'epoch': (new Date).getTime(),
      })
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });
  })

  describe("Test endpoint /api/getDislikedDogs", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/getDislikedDogsTest')
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

    it('Response has at least 1 dog', function() {
      expect(res.body).to.be.at.least(1);
    });

    it('Response has the liked dog', function() {
      res.body[0].should.have.property('dog');
      res.body[0].dog.should.have.property('id');
      res.body[0].dog.id.should.equal(dogid);
    });
  })

  describe("Cleap up test endpoint /api/updateUser", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/updateUserTest')
      .send({
        'fname': 'Sally',
        'lname': 'Smith',
      })
      .end(function(err, response){
        res = response;
        done();
      });
    });

    it('Response is OK', function() {
      res.should.have.status(200);
    });
  });


});
