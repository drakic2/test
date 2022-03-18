'use strict';

/** Processing when loading the screen */
jQuery(function($){

	/** Signup button processing */
	$('#btn-signup').click(function (event) {
		//User signup
		signupUser();	
	});

});

/** User signup processing */
function signupUser() {

	//clear validation results
	removeValidationResult();

	//Get the value of the form
	var formData = $('#signup-form').serializeArray();
	
	//ajax communication
	$.ajax({
		type : "POST",
		cache : false,
		url : '/user/signup/rest',
		data : formData,
		dataType : 'json',
	}).done(function(data) {
	
		//ajax success
		console.log(data);
		
		if(data.result == 90) {
			//when a validation error occurs
			$.each(data.errors, function (key, value) {
				reflectValidResult(key, value)
			});
			
		} else if (data.result == 0) {
			alert('Signed up user');
			//redirect to login screen
			window.location.href = '/login';
			
		}

	}).fail(function(jqXHR, textStatus, errorThrown) {
		//ajax failed
		alert('Failed to update user');
	}).always(function() {
		//process to always execute
	});	
}

//clear validation results
function removeValidationResult() {
	$('.is-invalid').removeClass('is.invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
	
}

//reflection of the validation results
function reflectValidResult(key, value) {

	//Add error message
	if(key == 'gender') { //for gender fields
		//apply css
		$('input[name=' + key + ']').addClass('is-invalid');
		//add error message
		$('input[name=' + key + ']')
			.parent().parent()
			.append('<div class="text-danger">' + value + '</div>');
	} else {
		//apply css
		$('input[id=' + key + ']').addClass('is-invalid');
		//add error message
		$('input[id=' + key + ']')
			.after('<div class="invalid-feedback">' + value + '</div>');
	
	}
	
	
}





