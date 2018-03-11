/**
 * 
 */

var urlSearch = 'localhost:8080/api/courseInformation/';
var name = $('#textSearch').val();
	
$('document').ready(function(){
$('#textSearch').val('');
name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
		
$.ajax({
	type: 'GET',
	contentType: "application/json",
	url: urlSearch + "&name=" + name,
	dataType: 'json',
	success: function(data) {
		$("body").append(
				"<p>" +  data.course.internalName + "</p>" +
				"<p>" +  data.course.courseDescription + "</p>" +
				"<p>" +  data.course.startDateString + "</p>" +
				"<p>" +  data.course.endDateString + "</p>");
	},
	error: function(exception){alert(exception.message);}
	})
})
	
function showSkills(){
		
		name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
		
		$.ajax({
			type: 'GET',
			url: urlSearch + "&name=" + name + "/Skills",
			success: function(data) {
				for(var i=0; i<data.items.length; i++){
					$("body").append(
					'<p>'+data.items[i].skillsList[i].skillName+'</p>' +
					'<p>'+data.items[i].skillsList[i].skillDescription+'</p>');
			};
			},
			error: function(exception){console.log('ERROR');}
		})
	}

function showSubjects(){
	
	name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
	
	$.ajax({
		type: 'GET',
		url: urlSearch + "&name=" + name + "/Subjects",
		success: function(data){
			for(var i=0; i<data.items.length; i++){
				$("body").append(
				'<p>'+data.items[i].subjectsList[i].name+'<p>' +
				'<p>'+data.items[i].skillsList[i].description+'<p>');
		};
		},
		error: function(exception){console.log('ERROR');}
	})
}

function showCourseAdded(){
	
	name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
	
	$.ajax({
		type: 'GET',
		url: urlSearch + "&name=" + name + "/add",
		success: function(data){
			;
		},
		error: function(exception){console.log('ERROR');}
	})
}