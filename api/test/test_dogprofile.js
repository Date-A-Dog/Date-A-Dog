var expect  = require("chai").expect;
var DogProfile = require("../www/js/dogprofile");

describe("Dog profile", function() {

  /** Sample dog properties **/
  var ID = "1234";
  var NAME = "fido";
  var SEX  = "F";
  var PHOTO_URL = "www.somephoto.com/dog.jpg";

  describe("DogProfile constructor", function() {
    it("Builds a DogProfile object", function() {
      var resultProfile = DogProfile(ID, NAME, SEX, PHOTO_URL);
      var expectedProfile = {
        id: ID,
        name: NAME,
        sex: SEX,
        photoURL: PHOTO_URL
      }

      expect(resultProfile).to.deep.equal(expectedProfile);
    }); 
  });

});
