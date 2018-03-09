/**
 * 
 */

var urlSearch = '/api/courseInformation/';
var name = $('#textSearch').val();
	
$('document').ready(function(){
$('#textSearch').val('');
name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
		
$.ajax({
	type: 'GET',
	url: urlSearch + "&name=" + name,
	success: function(data){
				;
	},
	error: function(exception){alert(exception.message);}
	})
})
	
function showSkills(){
		
		var name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
		
		$.ajax({
			type: 'GET',
			url: urlSearch "&name=" + name + "/Skills",
			success: function(data){
				;
			},
			error: function(exception){console.log('ERROR');}
		})
	}

function showSubjects(){
	
	var name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
	
	$.ajax({
		type: 'GET',
		url: urlSearch "&name=" + name + "/Subjects",
		success: function(data){
			;
		},
		error: function(exception){console.log('ERROR');}
	})
}