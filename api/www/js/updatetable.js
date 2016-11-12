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
  var tableOuter = document.getElementById(tableId);
  var table = tableOuter.getElementsByTagName('tbody')[0];

  // remove all record rows from table
  if(table.rows != null){
    while(table.rows.length > 1) {
      table.deleteRow(1);
    }
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
	//temp buttons
	var buttoncell = document.createElement('td');
	var approve = document.createElement('button');
	var decline = document.createElement('button');
	var undo = document.createElement('button');
	approve.setAttribute("type", "button");
	decline.setAttribute("type", "button");
	undo.setAttribute("type", "button");
	approve.setAttribute("class", "request-buttons btn btn-success btn-sm");
	decline.setAttribute("class", "request-buttons btn btn-warning btn-sm");
	undo.setAttribute("class", "request-buttons btn btn-secondary btn-sm");
	approve.innerHTML = "Approve";
	decline.innerHTML = "Decline";
	undo.innerHTML = "Undo";
	approve.setAttribute("onclick", "updateRequest(this)");
	decline.setAttribute("onclick", "updateRequest(this)");
	undo.setAttribute("onclick", "updateRequest(this)");
	buttoncell.appendChild(approve);
	buttoncell.appendChild(decline);
	buttoncell.appendChild(undo);
	row.append(buttoncell);
	
	//chevron element of each row
	//var chevcell = document.createElement('td');
	//var chevron = document.createElement('i');
	//$('i').addClass("indicator glyphicon glyphicon-chevron-up pull-right");
	//chevcell.appendChild(chevron);
	//row.append(chevcell);
    
	// append new row
    table.append(row);
  }

};

