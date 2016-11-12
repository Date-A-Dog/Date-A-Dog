
/*******comment out when not testing ********/
// var DaterProfile = require("./daterprofile");
// var DogProfile = require("./dogprofile");
// var DateRequest = require("./daterequest");
/*******************************************/
/*
// **Function temporarily inplace until we ca pull data from our data base.**
//
// Reads mock data JSON file located in the given path.
// This is an asyinc function and takes a callback function
// as the second parameter
//
 function fetchRequests(path, callback) {
   var httpRequest = new XMLHttpRequest();
   httpRequest.onreadystatechange = function() {
   	if (httpRequest.readyState === 4 && httpRequest.status === 200) {
   	  var data = JSON.parse(httpRequest.responseText);
   		if (callback) {
         // pass filtered array to callback
         callback(filterDateRequestProperties(data));
       }
   	}
  };

  httpRequest.open('GET', path);
  httpRequest.send();
 };
*/

/**** GOOD FUNCTION - use this once database calls are ready ****/
function fetchRequests(path, callback) {
  var httpRequest = new XMLHttpRequest();
  httpRequest.onreadystatechange = function() {
    if (httpRequest.readyState === 4 && httpRequest.status === 200) {
      var data = JSON.parse(httpRequest.responseText);
      if (callback) {
        // pass filtered array to callback
        console.log(data);
        callback(filterDateRequestProperties(data));
      }
    }
  };

  httpRequest.open('POST', '/api/getShelterRequests');
  httpRequest.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  httpRequest.setRequestHeader("access_token", "EAAPtY6fMq6ABAJKJ2TdW9LK0gKFMaMPuMFlDKCQUYwxHBbYzPGe210ZAmxSJpFqN5L2XxjJVEWfZBmgw47LZCwgqj6Pf7W98Nk0XWei5lyIpqhN1MZBeE0yjNYeIZAJIFRlkRJyH9BFazVvODLHKE1n8xtMZBbPfCsEukunkKBS5dgCAWYzUKe");
  httpRequest.send(JSON.stringify());
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
function filterDateRequestProperties(data) {
  var filteredArray = [];

  // parse data into array of dateRequests objects. Here we
  // extract the desired fields to build each dateRequest
  for (var i = 0; i < data.length; i++) {
    var r = data[i];
    //
    var address = Address(r.user.street,
                          r.user.city,
                          r.user.state,
                          r.user.zip);

    // parse daterProfile into object
    var dater = DaterProfile (r.user.fname,
                              r.user.lName,
                              r.user.email,
                              r.user.phone,
                              address);
    // parse dogProfile inot object
    var dog = DogProfile(r.dog.id,
                         r.dog.name,
                         r.dog.sex,
                         r.dog.media.photos[1].pn);
    // build dateRequest from parsed data
    var parsedRequest = DateRequest(r.request.id,
                                    dog, dater,
                                    r.request.epoch,
                                    r.request.status);
    // add new request
    filteredArray.push(parsedRequest);
  }

  return filteredArray;
};


/*function filterDateRequestProperties(data) {
  var filteredArray = [];

  // parse data into array of dateRequests objects. Here we
  // extract the desired fields to build each dateRequest
  for (var i = 0; i < data.length; i++) {
    var request = data[i];
    // parse daterProfile into object
    var dater = DaterProfile (request.daterProfile.fName,
                              request.daterProfile.lName,
                              request.daterProfile.email,
                              request.daterProfile.phone,
                              request.daterProfile.address);
    // parse dogProfile inot object
    var dog = DogProfile(request.dogProfile.id,
                         request.dogProfile.name,
                         request.dogProfile.sex,
                         request.dogProfile.photoURL);
    // build dateRequest from parsed data
    var parsedRequest = DateRequest(request.id,
                                    dog, dater,
                                    request.dateTime,
                                    request.status);
    // add new request
    filteredArray.push(parsedRequest);
  }

  return filteredArray;
}; */

