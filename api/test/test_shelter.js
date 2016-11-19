var assert = require("assert");
var Shelter = require("../www/js/shelter");


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
      assert.throws(Shelter, Error);
    });

  });

  /** 
  * Tests for getPendingRequests() method 
  */
  describe("Test getPendingRequests()", function() {
    var shelter = Shelter(SHELTER_ID, true);

    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(shelter.getPendingRequests(), Error);
    });

    // load data and get history results before proceding with tests
    shelter.updateDateRequests(); // no call back, this is sync
    var results = shelter.getPendingRequests(); 

    // test 1
    it("1. Correct number of elemnents", function() {
      // ensure data is loaded before asserting
      assert.equal(1, results.length);
      
    });
    // test 2
    it("2. Correct status for each element", function() {
      for (var i = 0; i < results.length; i++) {
        assert.equal("P", results[i].status);
      }      
    });
  });

  /**
  * Tests for getHistoryRequests() method 
  */
  describe("Test getHistoryRequests()", function() {
    var shelter = Shelter(SHELTER_ID, true);
    
    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(shelter.getHistoryRequests(), Error);
    });

    // load data and get history results before proceding with tests
    shelter.updateDateRequests(); 
    var results = shelter.getHistoryRequests(); 

    // test 1
    it("1. Correct number of elemnents", function() {
      // ensure data is loaded before asserting    
      assert.equal(2, results.length);
    });
    // test 2
    it("2. Correct status for each element", function() {
      var allValidHistoryStatus = true;
      for (var i = 0; i < results.length; i++) {
        var status = results[i].status;
        if (status != "A" && status != "D") {
          // contains other status then history
          allValidHistoryStatus = false;
        }
        assert.notEqual("P", status);
        assert.equal(true, allValidHistoryStatus);
      }      
    });
      
  });

  /** 
  * Tests for getDateRequest() method 
  */
  describe("Test getDateRequests()", function() {
    var shelter = Shelter(SHELTER_ID, true);
    // test 0
    it("0. Exception when calling before loading data.", function() {
      assert.throws(shelter.getDateRequests(), Error);
    });

    // load data and get history results before proceding with tests
    shelter.updateDateRequests(); 
    var results = shelter.getDateRequests(); 
    
    // test 1
    it("1. Correct number of elemnents", function() {
      // ensure data is loaded before asserting    
      assert.equal(3, results.length);
    });
  });

  /**
  * Tests for getDateRequestById() method
  */
  // TODO: implement testing
   describe("Test getDateRequestById()", function() {
     // test 0
     it("0. Exception when calling before loading data", function() {
      var shelter = Shelter(SHELTER_ID, true);
      // We pass a function since to enable callimg with parameters
       assert.throws(function() {shelter.getDateRequestById(89686);}, Error);
     });
     var shelter = Shelter(SHELTER_ID, true);
     // load data before proceding with tests
     shelter.updateDateRequests();

     // test 1
     it("1. Existing requestId", function() {
      var result = shelter.getDateRequestById(187327);
      assert.notEqual(result, undefined);
      assert.equal(result.daterProfile.fName, "John");
      assert.equal(result.daterProfile.phone, "281-330-8004");
      assert.equal(result.daterProfile.fax, undefined);
      assert.equal(result.daterProfile.address.street, "124 N Elmo St");

      assert.equal(result.dogProfile.id, 14553000);
     });
     // test 2
     it ("2. Non-existing requestId", function() {

     });
     // test 3
     it ("3. Searching on empty dateRequests", function() {

     });
   });


}); // end of shlelter test
