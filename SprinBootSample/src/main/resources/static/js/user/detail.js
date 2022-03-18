'use strict';

/** Processing when loading the screen */
jQuery(function($){

	/** Update button processing */
	$('#btn-update').click(function (event) {
		//User update 
		updateUser();	
	});
	
	/** Delete button processing */
	$('#btn-delete').click(function (event) {
		//User delete 
		deleteUser();	
	});

});

/** User update processing */
function updateUser() {

	//Get the value of the form
	var formData = $('#user-detail-form').serializeArray();
	
	//ajax communication
	$.ajax({
		type : "PUT",
		cache : false,
		url : '/user/update',
		data : formData,
		dataType : 'json',
	}).done(function(data) {
		//ajax success
		alert('Updated user');
		//redirect to user list screen
		window.location.href = '/user/list';
	}).fail(function(jqXHR, textStatus, errorThrown) {
		//ajax failed
		alert('Failed to update user');
	}).always(function() {
		//process to always execute
	});
}

/** User delete processing */
function deleteUser() {

	//Get the value of the form
	var formData = $('#user-detail-form').serializeArray();
	
	//ajax communication
	$.ajax({
		type : "DELETE",
		cache : false,
		url : '/user/delete',
		data : formData,
		dataType : 'json',
	}).done(function(data) {
		//ajax success
		alert('Deleted user');
		//redirect to user list screen
		window.location.href = '/user/list';
	}).fail(function(jqXHR, textStatus, errorThrown) {
		//ajax failed
		alert('Failed to delete user');
	}).always(function() {
		//process to always execute
	});
}


