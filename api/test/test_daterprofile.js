var expect = require("chai").expect;
var assert = require("assert");
var DaterProfile = require("../www/js/daterprofile");

describe("Dater Profile", function() {

  /** Sample dater profile properties **/
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
  /**
  * Test object construction`
  */
  describe("DaterProfile constructor", function() {
    it("Builds DaterProfile object", function() {
      var dp = DaterProfile(FNAME, LNAME, EMAIL,PHONE, ADDRESS);
      var expectedProfile = {
        fName: FNAME,
        lName: LNAME,
        email: EMAIL,
        phone: PHONE,
        address: ADDRESS
      }
      expect(dp).to.deep.equal(expectedProfile);
    });
  });

  /**
  *  Test DaterProfile properties
  */
  describe("Test DaterProfile property consitency", function() {
    var dp = DaterProfile(FNAME, LNAME, EMAIL, PHONE, ADDRESS);
    it ("0. fname", function() {
      assert(dp.fName, FNAME);
    });
    it("1. lname" ,function() {
      assert(dp.lName, LNAME);
    });
    it("2. email", function() {
      assert(dp.email, EMAIL);
    });
    it("3. phone", function() {
      assert(dp.phone, PHONE);
    });
    it("4. address", function() {
      expect(dp.address).to.deep.equal(ADDRESS);
    });
  });
}); // end of DaterProfile test
