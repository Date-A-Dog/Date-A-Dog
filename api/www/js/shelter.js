/*****Testing dependencies ***********************/
//This block is only executed when testing inside node.js
if (typeof require !== "undefined") {
 var fs = require("fs");
 var DaterProfile = require("./daterprofile");
 var DogProfile = require("./dogprofile");
 var DateRequest = require("./daterequest");
 var Address = require("./address");
}
/************************************************/

/**
 * shelter object represents a shelter session for the front
 * end webapp. This object is composed of a set of date requests
 * and methods to query and update server data.
 *
 */
var Shelter = function(_token, _testingMockData) {
  
  if (arguments < 1 || _token === undefined) {
    throw new Error("User token is required when constructing Shelter object.");
  }

  /** Te array where all date requests for this shelter are stored*/
  var dateRequests = undefined;
  /** The shelter id - determinded by petfinder.com */
  var token = _token;
  /** Enumerator used to filter requests according to status */
  var filter = { pending: "pending", history: "history"};
  /**object to which all functinality is attached */
  var shelter = {};

  /** Determines if we are testing - defaults to false if no second arg given */
  var testingMockData = _testingMockData || false;

  // This function makes XMLHttpRequest to back end API
  // server for date requests associated with the shelter token
  // This funtion is asynchronous, and takes a callback function
  // as the second parameter to be executed after successful
  // HttpRequest. Any calls made to this shelter object must be made
  // from within the passed callback funtion, as all shelter
  // funtionality is dependent on the availability of dateRequests
  // for this shelter object.
  //
  // param:
  //   token - the string token associated with shelter account
  //           for which dateRequests are to be returned if any.
  //   callback - The funtion to be executed upon successful data
  //           retrieval.
  shelter.updateDateRequests = function(callback) {
    if (!testingMockData) {
      // Request data from server through asynchronous call
      var httpRequest = new XMLHttpRequest();
      httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
          var data = JSON.parse(httpRequest.responseText);
          if (callback) {
            // pass filtered array to callback
            console.log(data);
            dateRequests = extractDateRequestProperties(data);
            callback();
          }
        }
      };
      // prepare and send http request
      httpRequest.open('POST', '/api/getShelterRequests');
      httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
      httpRequest.setRequestHeader("access_token", token);
      httpRequest.send(JSON.stringify());

    } else {  // this is a test run
      // Are we testing data from a specified testFile?
      if (shelter.testFile == undefined) {
        // set default mock data test file if one is not provided
        shelter.testFile = "./test/mockData/testData.json";
      }
      // read from mock data throgh synchronous call
      var content = fs.readFileSync(shelter.testFile);
      var jsonContent = JSON.parse(content);
      dateRequests = extractDateRequestProperties(jsonContent);
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
    if (dateRequests === undefined){
      throw new Error("Calling getPendingRequests() before fetching shelter data.");
    }
    // return fileterd date requests
    return filterDateRequests(filter.pending);
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
    if (dateRequests === undefined){
      throw new Error("Calling getHistoryRequests() before fetching shelter data.");
    }
    // return fileterd date requests
    return filterDateRequests(filter.history);
  };

  // Makes appropiate call to database/API to update
  // status for the specified requestId - careful to update
  // the current screen to reflect changes
  shelter.updateRequestStatus = function(requestId, newStatus, feedback) {
    if (typeof(feedback) === "undefined") {
      // we reset feedback if none provided
      feedback = "";
    }
    console.log("Your feedback: " + feedback);
    var httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === 4 && httpRequest.status === 200) {
        console.log("updateStatus response: " +  httpRequest.responseText);
      }
    };

    httpRequest.open('POST', '/api/updateRequestStatus');
    httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpRequest.setRequestHeader("access_token", token);
    httpRequest.send(JSON.stringify({"id": requestId, "status": newStatus, "feedback": feedback}));
  };

  // Private helper function which filters daterequests
  // based on its status determined by the passed parameter 'filterBy'.
  //
  // param:
  //   filterBy - a string which determines which set of
  //              date requests to return.
  // returns:
  //   An array of dateRequest objects with status == 'P' if
  //   parameter 'filterBy' == "pendign", or an
  //   objects with status == 'A' | 'D' if  parameter 'filetrBy' == "history".
  function filterDateRequests(filterBy) {
    var resultArray = [];
    // iterate through each request available and filter
    for (var i = 0; i < dateRequests.length; i++) {
      var status = dateRequests[i].status;

      if (filterBy == filter.pending && status == 'P') {
        // want pending requests, so status of 'P'
        resultArray.push(dateRequests[i]);
      } else if (filterBy == filter.history && status != 'P') {
        // any other status not equal to 'P' is history
        resultArray.push(dateRequests[i]);
      }
    }
    return resultArray;
  };

  // Iterates the given array of dateRequests extracts properties
  // and generates a new object array. Each dateRequest object
  // in the retuned array has the following tree properties;
  //
  //  {
  //     "id": 187326,
  //     "dateTime": "tomorrow",
  //     "status": "P",
  //     "daterProfile": {
  //       "fName": "Mike",
  //       "lName": "Jones",
  //       "email": "Mike.Jones@gmial.com",
  //       "phone": "281-330-8004",
  //       "address": {
  //         "street": "652 N 16th St",
  //         "city": "Seattle",
  //         "state": "WA",
  //         "zipcode": "92104"
  //       }
  //     },
  //     "dogProfile": {
  //       "id": 14553,
  //       "name": "Chewbacca",
  //       "sex": "M",
  //       "age:": "Adult",
  //       "photoURL": "somephotourl.com"
  //     }
  //  }
  //
  // param:
  //   data - is the unfiltered data which usually contains more fileds
  //          than we need for shelter dateRequests.
  // return:
  //   An unordered array containing date requests with limited properties
  //
  function extractDateRequestProperties(data) {
    var filteredArray = [];
    // parse data into array of dateRequests objects. Here we
    // extract the desired fields to build each dateRequest
    for (var i = 0; i < data.length; i++) {
      var r =  validateRequestProperties(data[i]);   // current result

      // parse user's address
      var address = Address(r.user.street,
                            r.user.city,
                            r.user.state,
                            r.user.zip);
      // parse daterProfile into object
      var dater = DaterProfile (r.user.fname,
                                r.user.lname,
                                r.user.email,
                                r.user.phone,
                                address);
      // parse dogProfile inot object
      var dog = DogProfile(r.dog.id,
                           r.dog.name,
                           r.dog.sex,
                           r.dog.age,
                           r.dog.media.photos[1].pn);
      // build dateRequest from parsed data
      var parsedRequest = DateRequest(r.request.id,
                                      dog, dater,
                                      epochToString(r.request.epoch),
                                      r.request.status,
                                      r.request.reason,
                                      r.request.feedback);
                                     
      // add new request
      filteredArray.push(parsedRequest);
    }

    return filteredArray;
  };

  
  // Validates request properties for undefined, null, or empty strings
  // param: r - the request object to verify
  // 
  // return:
  //  A request object with invalid fields replaced with "Non provided"
  //
  function validateRequestProperties (r) {
    // Verify user profile properties
    if (typeof(r.user.street) === "undefined" || r.user.street === null || r.user.street === "") { r.user.street = "None provided";}
    if (typeof(r.user.city) === "undefined" || r.user.city === null || r.user.city === "") { r.user.city = "None provided";}
    if (typeof(r.user.state) === "undefined" || r.user.state === null || r.user.state === "") { r.user.state = "None provided";}
    if (typeof(r.user.zip) === "undefined" || r.user.zip === null || r.user.zip === "") { r.user.zip = "None provided";}
    if (typeof(r.user.fname) === "undefined" || r.user.fname === null || r.user.fname === "") { r.user.fname = "None provided";}
    if (typeof(r.user.lname) === "undefined" || r.user.lname === null || r.user.lname === "") { r.user.lname = "None provided";}
    if (typeof(r.user.email) === "undefined" || r.user.email === null || r.user.email === "") { r.user.email = "None provided";}
    if (typeof(r.user.phone) === "undefined" || r.user.phone=== null || r.user.phone === "") { r.user.phone = "None provided";}

    // Verify dog profile properties
    if (typeof(r.dog.id) === "undefined" || r.dog.id === null || r.dog.id === "" ) { r.dog.id = "None provided";}
    if (typeof(r.dog.name) === "undefined" || r.dog.name === null || r.dog.name === "") { r.dog.name = "None provided";}
    if (typeof(r.dog.sex) === "undefined" || r.dog.sex === null || r.dog.sex === "") { r.dog.sex = "None provided";}
    if (typeof(r.dog.age) === "undefined" || r.dog.age === null || r.dog.age === "") { r.dog.age = "None provided";}
    if (typeof(r.dog.media.photos[1]) === "undefined") { r.dog.media.photos[1] = "Non provided";}
   
    // Verify request properties
    if (typeof(r.request.id) === "undefined" || r.request.id === null || r.request.id === "") { r.request.id = "None provided";}
    if (typeof(r.request.status) === "undefined" || r.request.status === null || r.request.status === "") { r.request.status = "P";}
    if (typeof(r.request.epoch) === "undefined" || r.request.epoch === null || r.request.epoch) { r.request.epoch = "None provided";}
    if (typeof(r.request.reason) === "undefined" || r.request.reason === null || r.request.reason === "") { r.request.reason = "None provided";}
    if (typeof(r.request.feedback) === "undefined" || r.request.feedback === "") { r.request.feedback = "None provided";}
    return r;
  }

  // Formats epoch and returns a human readable date:time
  // Private helper function which formats the given epoch
  // into a human readable date string in the following format;
  //
  //   "mm/dd/year at hr:mi"
  //
  // param:
  //   epoch - the epoch value to be convertedinto
  function epochToString(epoch) {
    var d = new Date(epoch);
    var month  = d.getMonth() + 1; // account for month offset [0,11]
    var day    = d.getDate();
    var year   = d.getFullYear();
    var hr     =  "" + d.getHours();
    var min    = "" + d.getMinutes();
    // maintain two digit time format
    hr = (hr.length < 2) ? "0" + hr: hr;
    min = (min.length < 2) ? "0" + min: min;

    return month + "/" + day + "/" + year + " at " + hr + ":" + min;
  }

  return shelter;
};

if (typeof module !== "undefined" && typeof module.exports !== "undefined") {
 module.exports = Shelter;
} else {
  window.Shelter = Shelter;
}
