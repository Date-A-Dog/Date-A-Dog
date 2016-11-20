var expect  = require("chai").expect;
var assert = require("assert");
var DogProfile = require("../www/js/dogprofile");

describe("Dog profile", function() {

  /** Sample dog properties **/
  var ID = "1234";
  var NAME = "fido";
  var SEX  = "F";
  var AGE  = "Adult";
  var PHOTO_URL = "www.somephoto.com/dog.jpg";

  describe("DogProfile constructor", function() {
    it("Builds a DogProfile object", function() {
      var dp = DogProfile(ID, NAME, SEX, AGE, PHOTO_URL);
      var expectedProfile = {
        id: ID,
        name: NAME,
        sex: SEX,
        age: AGE,
        photoURL: PHOTO_URL
      };
      expect(dp).to.deep.equal(expectedProfile);
    });
  });

  describe("Test DogProfile property consitency", function() {
    var dp = DogProfile(ID, NAME, SEX, AGE, PHOTO_URL);
    it("0. dogId", function() {
      assert.equal(dp.id, ID);
    });
    it("1. name", function() {
      assert.equal(dp.name, NAME);
    });
    it("2. sex", function() {
      assert.equal(dp.sex, SEX);
    });
    it("3. age", function() {
      assert.equal(dp.age, AGE);
    });
    it("4. photoURL", function() {
      assert.equal(dp.photoURL, PHOTO_URL);
    });
  });

});
