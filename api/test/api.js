var chai = require('chai');
var chaiHttp = require('chai-http');
var chaiThings = require('chai-things');
var server = require('../app');
var should = chai.should();
// var expect  = chai.expect;
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
      dogid = res.body[0].dog.id;
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
      .post('/api/judgeDogTest')
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

    it('Response is a valid JSON Array', function() {
      res.should.be.json;
      res.body.should.be.Array;
    });

    it('Response has at least 1 dog', function() {
      res.body.should.have.length.at.least(1);
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
      .post('/api/judgeDogTest')
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

    it('Response is a valid JSON Array', function() {
      res.should.be.json;
      res.body.should.be.Array;
    });

    it('Response has at least 1 dog', function() {
      res.body.should.have.length.at.least(1);
    });

    it('Response has the disliked dog', function() {
      res.body[0].should.have.property('dog');
      res.body[0].dog.should.have.property('id');
      res.body[0].dog.id.should.equal(dogid);
    });
  })

  describe("Test endpoint /api/getDogHistory", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/getDogHistoryTest')
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

    it('Response has at least 1 dog', function() {
      res.body.should.have.length.at.least(1);
    });

    it('Response has the judged dog', function() {
      res.body[0].should.have.property('dog');
      res.body[0].dog.should.have.property('id');
      res.body[0].dog.id.should.equal(dogid);
    });
  })

  describe("Test endpoint /api/getNextDogs does not return judged dogs", function() {
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

    it('Response does not contain judged dog', function() {
      for (i = 0; i < 20; i++) {
        res.body[i].dog.id.should.not.equal(dogid);
      }
    });
  })

  var requestid;
  var dogid2 = '22406048';
  var epoch = (new Date).getTime();
  describe("Test endpoint /api/requestDate", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/requestDateTest')
      .send({
        'id': dogid2,
        'epoch': epoch,
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

  describe("Test endpoint /api/getShelter", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/getShelter')
      .send({
        'shelterId': 'WA214',
      })
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

    it('shelter exists', function() {
      res.body.should.have.property('shelter');
    });

    it('shelter.id is correct', function() {
      res.body.shelter.should.have.property('id');
      res.body.shelter.id.should.equal('WA214');
    });

    it('shelter.fax is correct', function() {
      res.body.shelter.should.have.property('fax');
      res.body.shelter.fax.should.equal('www.washingtongsd.org');
    });

    it('shelter.zip is correct', function() {
      res.body.shelter.should.have.property('zip');
      res.body.shelter.zip.should.equal('98104');
    });

    it('shelter.city is correct', function() {
      res.body.shelter.should.have.property('city');
      res.body.shelter.city.should.equal('Seattle');
    });


    it('shelter.name is correct', function() {
      res.body.shelter.should.have.property('name');
      res.body.shelter.name.should.equal('Washington German Shepherd Rescue');
    });

    it('shelter.email is correct', function() {
      res.body.shelter.should.have.property('email');
      res.body.shelter.email.should.equal('washingtonshepherds@yahoo.com');
    });

    it('shelter.phone is correct', function() {
      res.body.shelter.should.have.property('phone');
      res.body.shelter.id.should.equal('(206) 445-5151');
    });

    it('shelter.state is correct', function() {
      res.body.shelter.should.have.property('state');
      res.body.shelter.state.should.equal('WA');
    });

    it('shelter.country is correct', function() {
      res.body.shelter.should.have.property('country');
      res.body.shelter.country.should.equal('US');
    });

    it('shelter.latitude is correct', function() {
      res.body.shelter.should.have.property('latitude');
      res.body.shelter.latitude.should.equal('47.604');
    });

    it('shelter.longitude is correct', function() {
      res.body.shelter.should.have.property('longitude');
      res.body.shelter.longitude.should.equal('-122.326');
    });
  })

  describe("Test endpoint /api/getShelterRequests", function() {
    var res, length;
    before(function(done) {
      chai.request(server)
      .post('/api/getShelterRequestsTest')
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

    it('Response has at least 1 request', function() {
      res.body.should.have.length.at.least(1);
    });

    it('request exists', function() {
      res.body[res.body.length - 1].should.have.property('request');
    });

    it('request.id exists', function() {
      res.body[res.body.length - 1].request.should.have.property('id');
      requestid = res.body[res.body.length - 1].request.id;
    });

    it('request.epoch exists', function() {
      res.body[res.body.length - 1].request.should.have.property('epoch');
    });

    it('request.status exists', function() {
      res.body[res.body.length - 1].request.should.have.property('status');
    });

    it('request.epoch is correct', function() {
      res.body[res.body.length - 1].request.epoch.should.equal(epoch);
    });

    it('request.status is correct', function() {
      res.body[res.body.length - 1].request.status.should.equal('P');
    });

    it('dog exists', function() {
      res.body[res.body.length - 1].should.have.property('dog');
    });

    it('dog.id is correct', function() {
      res.body[res.body.length - 1].dog.should.have.property('id');
      res.body[res.body.length - 1].dog.id.should.equal(dogid2);
    });

    it('shelter exists', function() {
      res.body[res.body.length - 1].should.have.property('shelter');
    });

    it('shelter.id is correct', function() {
      res.body[res.body.length - 1].shelter.should.have.property('id');
      res.body[res.body.length - 1].shelter.id.should
      .equal(res.body[res.body.length - 1].dog.shelterId);
    });

    it('user exists', function() {
      res.body[res.body.length - 1].should.have.property('user');
    });

    it('user.id is correct', function() {
      res.body[res.body.length - 1].user.should.have.property('id');
      res.body[res.body.length - 1].user.id.should
      .equal(119889308491710);
    });
  })

  describe("Test endpoint /api/updateRequestStatus", function() {
    var res;
    before(function(done) {
      chai.request(server)
      .post('/api/updateRequestStatusTest')
      .send({
        'id': requestid,
        'status': 'A',
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

  describe("Verify /api/updateRequestStatus using endpoint /api/getShelterRequests", function() {
    var res, length;
    before(function(done) {
      chai.request(server)
      .post('/api/getShelterRequestsTest')
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

    it('Response has at least 1 request', function() {
      res.body.should.have.length.at.least(1);
    });

    it('request exists', function() {
      res.body[res.body.length - 1].should.have.property('request');
    });

    it('request.id is correct', function() {
      res.body[res.body.length - 1].request.should.have.property('id');
      res.body[res.body.length - 1].request.id.should.equal(requestid);
    });

    it('request.epoch is correct', function() {
      res.body[res.body.length - 1].request.should.have.property('epoch');
      res.body[res.body.length - 1].request.epoch.should.equal(epoch);
    });

    it('request.status is correct', function() {
      res.body[res.body.length - 1].request.should.have.property('status');
      res.body[res.body.length - 1].request.status.should.equal('A');
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
