'use strict';

var userData = null;
var table = null; //DataTables object
var tableHead = null;

/** Processing when loading the screen */
jQuery(function($) {

	//DataTables initialization
	createDataTables();

	/** Search button processing */
	$('#btn-search').click(function (event) {
		//search
		search();	
	});

});

/** Search processing */
function search() {

	//Get the value of the form
	var formData = $('#user-search-form').serializeArray();
	
	//ajax communication
	$.ajax({
		type : "GET",
		cache : false,
		url : '/user/get/list',
		data : formData,
		dataType : 'json',
		contentType:'application/json; charset=UTF-8',
		cache:false,
		timeout:5000,
	}).done(function(data) {
	
		//ajax success
		console.log(data);

		//put JSON to variable
		userData = data
		//create datatables
		createDataTables();

	}).fail(function(jqXHR, textStatus, errorThrown) {
		//ajax failed
		alert('Search process failes');
		
	}).always(function() {
		//process to always execute
	});	
}

//Create data tables
function createDataTables() {

	//If DataTables
	if(table != null) {
		//DataTables discard
		table.destroy();
	}
	
	if(userData == null) {
		return;
	}

	
	var tbody = $('#user-list-table');
	
	if(tableHead == null) {
		
			    //create the cells inside thead
	    var row = `<tr>
	    				<th class='th-width'>User ID</th>
	    				<th class='th-width'>User Name</th>
	    				<th class='th-width'>Birthday</th>
	    				<th class='th-width'>Age</th>
	    				<th class='th-width'>Gender</th>
	    				<th class='th-width'></th>
	    			</tr>`
	    					 
	    tableHead = `<thead class='thead-light'> ${row} </thead>`
	    tbody.append(tableHead);
	    
	    /*
	    //create the cells inside thead
	    var row = $("<tr />").append("<th class='th-width'>User ID</th>")
	    					 .append("<th class='th-width'>User Name</th>")
	    					 .append("<th class='th-width'>Birthday</th>")
	    					 .append("<th class='th-width'>Age</th>")
	    					 .append("<th class='th-width'>Gender</th>")
	    					 .append("<th class='th-width'></th>");
	    					 
	    tableHead = $("<thead class='thead-light'/>").append(row);
	    tbody.append(tableHead);
	    */
	    
    }
		
	//Create DataTables
	table = tbody.DataTable({
		//Display data
		data: userData,
		//Data and column mapping
		columns:[
			{data: 'userId'},
			{data: 'userName'},
			{
				data: 'birthday',
				render: function( data, type, row) {
					var date = new Date(data);
					var year = date.getFullYear();
					var month = date.getMonth() + 1;
					var date = date.getDate();
					return date + '/' + month + '/' + year;
				}
			},
			{data: 'age'},
			{
				data:'gender',
				render: function (data, type, row) {
					var gender = '';
					if(data == 1) {
						gender = 'Male';
					} else {
						gender = 'Female';
					}
					return gender;
				}
			},
			{
				data: 'userId', //Url of user details screen
				render: function(data, type, row) {
					var url = '<a class="btn btn-primary" href="/user/detail/' + data + '">Details</a>';
					return url;
				}
			},
		]
	});
			
}


