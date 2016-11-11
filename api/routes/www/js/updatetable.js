// This function populates rows in a table identified by tableId
// (the html tag id). All existing rows in the tableId are 
// removed before appending new dateRequests rows, except for 
// the table header row.
// 
// params: 
// tableId - the html tag for table to add rows
// dateRequests - an array of requests to be added as rows to table
//          
updateTable = function(tableId, dateRequests) {
  var table = document.getElementById(tableId);

  // remove all record rows from table
  while(table.rows.length > 1) {
    table.deleteRow(1);
  }

  // iterate through each request in array
  for (var i = 0; i < dateRequests.length; i++) {
    var request = dateRequests[i];
    // create a new row
    var row = document.createElement('tr');
    //  list of properties for request
    var properties = ['id', 'daterProfile', 'dogProfile', 'dateTime', 'status'];

    // append each property to current row
    for (var j = 0; j < properties.length; j++) {
      var cell = document.createElement('td');

      // set properties
      if (j === 1) {  // extract user name
        cell.innerHTML = request[properties[j]].fName + " " + request[properties[j]].lName;

      } else if (j === 2) { //extract dog name
        cell.innerHTML = request[properties[j]].name;

      } else {
        cell.innerHTML = request[properties[j]];

      }
      // append new cell
      row.append(cell);
    }
    // append new row
    table.append(row);
  }

};

exports.updateTable;