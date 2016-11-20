var expect = require("chai").expect;
var assert = require("assert");
var Address = require("../www/js/address");

describe("Address", function() {

  /** Sample address properties **/
  var STREET = "652 N 16th St";
  var CITY  = "Seattle";
  var STATE = "WA";
  var ZIPCODE = "92104";

  /**
  * Test object construction`
  */
  describe("Address constructor", function() {
    it("Builds Address object", function() {
      var addr = Address(STREET, CITY, STATE, ZIPCODE);
      var expectedAddress = {
        street: STREET,
        city: CITY,
        state: STATE,
        zipcode: ZIPCODE
      };
      expect(addr).to.deep.equal(expectedAddress);
    });
  });

  /**
  *  Test Address properties
  */
  describe("Test Address property consitency", function() {
    var addr = Address(STREET, CITY, STATE, ZIPCODE);
    it("0. street", function() {
      assert(addr.street, STREET);
    });
    it("1. city", function() {
      assert(addr.city, CITY);
    });
    it("2. state", function() {
      assert(addr.state, STATE);
    });
    it("2. zipcode", function() {
      assert(addr.zipcode, ZIPCODE);
    });
  });
}); // end of DaterProfile test
