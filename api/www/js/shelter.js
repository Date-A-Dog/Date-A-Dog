
/******* Comment out when not testing ***********************/
//var fs = require("fs");
//var filterDateRequestProperties = require("./fetchrequests");
/************************************************************/

/**
 * shelter object represents a shelter session for the front
 * end webapp. This object is composed of a set of date requests
 * and methods to query and update server data.
 *
 */
var Shelter = function(_id, _testingMockData) {

  if (arguments < 1 || _id === undefined) {
    throw Error("shelterId is required when constructing Shelter object.");
  }


  /** Te array where all date requests for this shelter are stored*/
  var dateRequests = null; 
  /** The shelter id - determinded by petfinder.com */
  var id = _id;
  /** Determines if we are testing - defaults to false if no second arg given */
  var testingMockData = _testingMockData || false;
  /**object to which all functinality is attached */
  var shelter = {
  };



  // Updates this shelter's set of date requests
  // by calling database with most up-to-date data.
  // This function is asynchronous and supports a  call back
  // funtion as parameter.
  //
  // params: 
  // callback - Is a callback function to be invoked upon
  //            completion of async task.
  //                            
  shelter.updateDateRequests = function(callback) {
    // fetch new requests from database 
    if (testingMockData) {
      // read from mock data
      var content = fs.readFileSync("./test/mockData/testData.txt");
      var jsonContent = JSON.parse(content);
      dateRequests = filterDateRequestProperties(jsonContent);
    } else {
             // call async method to load dateRequext data
      fetchRequests('./mockData/dateRequestData.json', function(data) {
        //return data here
		
        dateRequests = data;
        callback();
      });
    }
  };

  // Getter function wich provides access to pending date
  // requests objects for this shelter.
  // 
  // return:
  //   An unordered array of dateRequest objects with status
  //   equal to 'P'. Logs error, and returns null 
  //   if called before shelter data is fetched.
  // 
  shelter.getPendingRequests = function() {
    if (dateRequests == null){ 
      throw Error("Calling getPendingRequests() before fetching shelter data.");
    }

    return filterDateRequests('pending');
  };

  // Getter function wich provides access to history date
  // requests objects for this shelter. 
  // 
  // return:
  //   An unorderedd array of dateRequest objects with staus
  //   equal to 'A' or 'D'. Logs error, and returns 
  //   null if called before shelter data is fetched.
  // 
  shelter.getHistoryRequests = function() {
    if (dateRequests == null){ 
      throw Error("Calling getHistoryRequests() before fetching shelter data.");
    }
   
    return filterDateRequests('history');
  };
 
  // Makes appropiate call to database/API to update
  // status for the specified requestId - careful to update
  // the current screen to reflect changes 
  shelter.updateRequestStatus = function(requestId, newStatus) {

  };


  // Gets the request object identified by 'requestId'
  // given as parameter. In the event that this method is
  // called before this shelter has been loaded with data,
  // this method will call this.refreshRequest before attempting
  // search.
  // 
  // params:
  // requestId - an integer value which identifies the
  //             dateRequest object to return.
  // returns:
  // A dateRequest object corresponding to 'requestId'
  // if found, otherwise returns null.       
  //
  shelter.getDateRequestById = function(requestId) {
    // check param is provided
    if (arguments.length != 1) {
      throw Error("getRequestById requires requestId parameter.")
    }

    if (dateRequests == null){ 
      throw Error("Error: calling getDateRequestsById() before fetching shelter data.");
    }

    // check if dateRequests has been initiated
    if (!dateRequests || !hasDateRequests()) {
      // refresh requests - pas this function as callback
      console.log("No dateRequests available for this shelter.");

      return null;
    }

    // iterate through available requests until we find reqId
    // and return it if found
    for (var i = 0; i < dateRequests.length; i++) {
      if (dateRequests[i].id === requestId) {
        // we have a match, return it
        console.log("found match" + dateRequests[i].daterProfile.fName);
        return dateRequests[i];
      }
    }
    
    return null;
  };

  // Gets all date requests available for this shelter
  //
  // return: 
  //   An array containing dateRequestObjects. Returns null if this
  //   shelter's dateRequest array has has not yet been initialized 
  //
  shelter.getDateRequests = function() {
    if (dateRequests == null){ 
      throw Error("Error: calling getDateRequests() before fetching shelter data.");
    }

    if(!hasDateRequests()) {
      console.log("No dateRequests available for this shelter.");
      return [];
    }
    return dateRequests;
  };

  // private helper function checks is shelter.dateRequests is
  // empty.
  // returns:
  //   false if there are no date requests for this shelter
  //   true otherwise.
  //
  var hasDateRequests = function() {
    return (dateRequests.length > 0);
  };

  // Private helper function which filters daterequests 
  // based on its status determined by the passed parameter.
  // 
  // param: 
  //   filterBy - a string which determines which set of
  //              date requests to return.
  // returns:
  //   An array of dateRequest objects with status == 'P' if
  //   parameter 'filterBy' == "pendign", or status == 'A' | 'D' if 
  //   parameter 'filetrBy' == "history".
  var filterDateRequests = function(filterBy) {
    var resultArray = [];

    // iterate through each request available and filter
    for (var i = 0; i < dateRequests.length; i++) {      
      var status = dateRequests[i].status;

      if (filterBy == 'pending' && status == 'P') {
        // want pending requests, so status of 'P'
        resultArray.push(dateRequests[i]); 

      } else if (filterBy == 'history' && status != 'P') {
        // any other status not equal to 'P' is history
        resultArray.push(dateRequests[i]);
      }
    }

    return resultArray;
  };


  return shelter;
};

module.exports = Shelter;
