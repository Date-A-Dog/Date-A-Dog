var expect  = require("chai").expect;
var assert = require("assert");
var DaterProfile = require("../www/js/daterequest");

describe("Date Request", function() {
  /** Sample date request properties **/
  var REQUEST_ID = 238359;
  var STATUS = "P";
  var DATE = "11/12/2016";
  var REASON = "user reason";
  var FEEDBACK = "shelter feedback";
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



  /**
  * Test constructor
  */
  describe("DateRequest constructor", function() {
    it("Builds DateRequest object", function() {
      var dr = DateRequest(REQUEST_ID, DOG_PROFILE, DATER_PROFILE, DATE, STATUS, REASON, FEEDBACK);
      var expectedDateRequest = {
        id: REQUEST_ID,
        dogProfile: DOG_PROFILE,
        daterProfile: DATER_PROFILE,
        dateTime: DATE,
        status: STATUS,
        reason: REASON,
        feedback: FEEDBACK
      }
      expect(dr).to.deep.equal(expectedDateRequest);
    });
  });

  describe("DateRequest Property Consistency", function() {
    var dr = DateRequest(REQUEST_ID, DOG_PROFILE, DATER_PROFILE, DATE, STATUS, REASON, FEEDBACK);
    it("0. requestId", function() {
      assert(dr.id, REQUEST_ID);
    });
    it("1. dogProfile", function() {
      expect(dr.dogProfile).to.deep.equal(DOG_PROFILE);
    });
    it("2. daterProfile", function() {
      expect(dr.daterProfile).to.deep.equal(DATER_PROFILE);
    });
    it("3. dateTime", function() {
      assert(dr.dateTime, DATE);
    });
    it("4. status", function() {
      assert(dr.status, STATUS);
    });
    
    it("5. reason", function() {
      assert(dr.status, REASON);
    });
    
    it("6. feedback", function() {
      assert(dr.status, FEEDBACK);
    });
  });

}); // end of DateRequest test
