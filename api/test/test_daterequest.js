var expect  = require("chai").expect;
var DaterProfile = require("../www/js/daterequest");

describe("Date Request", function() {

  /** Sample request properties **/


  var REQUEST_ID = 238359;
  var STATUS = "P";
  var DATE = "11/12/2016";
  var DATER_PROFILE = {
      fName: "joe",
      lName: "rippper",
      email: "email@gmail.com",
      phone: "123-456-7890",
      address: {
        street: "652 N 16th St",
        city: "Seattle",
        state: "WA",
        zipcode: "92104"
      }
  };
  var DOG_PROFILE = {
    id: "1234",
    name: "fido",
    sex: "F",
    photoURL: "www.somephoto.com/dog.jpg"
  };




  describe("DateRequest constructor", function() {
    it("Builds DateRequest object", function() {
      var resultProfile = DateRequest(REQUEST_ID, DOG_PROFILE, DATER_PROFILE, DATE, STATUS);
      var expectedProfile = {
        id: REQUEST_ID,
        dogProfile: DOG_PROFILE,
        daterProfile: DATER_PROFILE,
        dateTime: DATE,
        status: STATUS
      }

      expect(resultProfile).to.deep.equal(expectedProfile);
    }); 
  });

});