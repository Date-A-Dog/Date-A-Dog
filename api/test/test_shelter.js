var assert = require("assert");
var Shelter = require("../www/js/shelter");
var extractDateRequestProperties = require("../www/js/shelter");
var fs = require("fs");


describe("Shelter", function() {
  var SHELTER_ID = "WA142";

  /**
  * Test construction of shelter objects
  */
  describe("Test Constructor", function() {
    //test 0
  	it("0. Correct parameters", function() {
      assert.notEqual( Shelter(SHELTER_ID, true), undefined);
  	});
    // test 1
    it("1. No test boolean parameter given", function() {
      assert.notEqual(Shelter(SHELTER_ID), undefined);
    });
    // test 2
    it("2. Exception when no parameters given", function() {
      assert.throws(function() {Shelter();}, Error);
    });

  });

  /**
  * Tests for getPendingRequests() method
  */
  describe("Test getPendingRequests() when dateRequests > 0", function() {
    var shelter = Shelter(SHELTER_ID, true);
    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(function() {shelter.getPendingRequests();}, Error);
    });
    // test 1
    it("1. Correct number of elemnents", function() {
      // load data and get history results before proceding with tests
      shelter.updateDateRequests(); // no call back, this is sync
      var results = shelter.getPendingRequests();
      // ensure data is loaded before asserting
      assert.equal(results.length, 2);

    });
    // test 2
    it("2. Correct status for each element", function() {
      // load data and get history results before proceding with tests
      //shelter.updateDateRequests(); // no call back, this is sync
      var results = shelter.getPendingRequests();
      for (var i = 0; i < results.length; i++) {
        assert.equal(results[i].status, "P");
      }
    });

  });

  /**
  * Tests for getHistoryRequests() method
  */
  describe("Test getHistoryRequests() when dateRequests > 0 ", function() {
    var shelter = Shelter(SHELTER_ID, true);

    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(function(){shelter.getHistoryRequests();}, Error);
    });
    // test 1
    it("1. Correct number of elemnents", function() {
      // load data and get history results before proceding with tests
      shelter.updateDateRequests();
      var results = shelter.getHistoryRequests();
      assert.equal(results.length, 2);
    });
    // test 2
    it("2. Correct status for each element", function() {
      // load data and get history results before proceding with tests
      shelter.updateDateRequests();
      var results = shelter.getHistoryRequests();
      var allValidHistoryStatus = true;

      for (var i = 0; i < results.length; i++) {
        var status = results[i].status;
        if (status != "A" && status != "D") {
          // contains other status then history
          allValidHistoryStatus = false;
        }
        assert.notEqual(status, "P");
        assert.equal(allValidHistoryStatus,true);
      }
    });
  });

  describe("Test getPendingRequests() when dateRequests == 0 ", function() {
    var shelter = Shelter(SHELTER_ID, true);
    // set testFile to mock date with no requests
    shelter.testFile = "./test/mockData/testData_empty.json";
    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(function() {shelter.getPendingRequests();}, Error);
    });
    // test 1
    it("1. results array is not null", function() {
      // load data and get pending results
      shelter.updateDateRequests();
      var results = shelter.getPendingRequests();
      assert.notEqual(results, null);
    });
    // test 2
    it("2. results array is not undefined", function() {
      // load data and get pending results
      shelter.updateDateRequests();
      var results = shelter.getPendingRequests();
      assert.notEqual(results, undefined);
    });
    // test 3
    it("3. number of results == 0", function() {
      // load data and get pending results
      shelter.updateDateRequests();
      var results = shelter.getPendingRequests();
      assert.equal(results.length, 0);
    });
  });

  describe("Test getHistoryRequests() when dateRequests == 0", function() {
    var shelter = Shelter(SHELTER_ID, true);
    // set testFile to mock date with no requests
    shelter.testFile = "./test/mockData/testData_empty.json";
    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(function() {shelter.getHistoryRequests();}, Error);
    });
    // test 1
    it("1. results array is not null", function() {
      // load data and get pending results
      shelter.updateDateRequests();
      var results = shelter.getHistoryRequests();
      assert.notEqual(results, null);
    });
    // test 2
    it("2. results array is not undefined", function() {
      // load data and get pending results
      shelter.updateDateRequests();
      var results = shelter.getHistoryRequests();
      assert.notEqual(results, undefined);
    });
    // test 3
    it("3. number of results == 0", function() {
      // load data and get pending results
      shelter.updateDateRequests();
      var results = shelter.getHistoryRequests();
      assert.equal(results.length, 0);
    });
  });

}); // end of shelter test
