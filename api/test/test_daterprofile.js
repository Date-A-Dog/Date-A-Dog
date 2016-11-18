var expect  = require("chai").expect;
var DaterProfile = require("../www/js/daterprofile");

describe("Dater Profile", function() {

  /** Sample dater properties **/
  var FNAME = "joe";
  var LNAME  = "rippper";
  var EMAIL = "email@gmail.com";
  var PHONE = "123-456-7890";
  var ADDRESS = {
        street: "652 N 16th St",
        city: "Seattle",
        state: "WA",
        zipcode: "92104"
  };

  describe("DaterProfile constructor", function() {
    it("Builds DaterProfile object", function() {
      var resultProfile = DaterProfile(FNAME, LNAME, EMAIL,PHONE, ADDRESS);
      var expectedProfile = {
        fName: FNAME,
        lName: LNAME,
        email: EMAIL,
        phone: PHONE,
        address: ADDRESS
      }

      expect(resultProfile).to.deep.equal(expectedProfile);
    }); 
  });

});