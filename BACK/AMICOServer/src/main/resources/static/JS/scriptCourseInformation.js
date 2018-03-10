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
		$("courseDescription").append(
			"<p>"+data.items[i].course.name+"</p>") + 
		    "<p>"+data.items[i].course.courseDescription+"</p>") +
            "<p>"+data.items[i].course.startDateString+"</p>") +
		   "<p>"+data.items[i].course.endDateString+"</p>");
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
				for(var i=0; i<data.items.length; i++){
					$("courseSkills").append(
					"<p>"+data.items[i].skillsList[i].skillName+"</p>") +
					"<p>"+data.items[i].skillsList[i].skillDescription+"</p>");
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
			for(var i=0; i<data.items.length; i++){
				$("courseSubjects").append(
				"<p>"+data.items[i].subjectsList[i].name+"</p>") +
				"<p>"+data.items[i].skillsList[i].description+"</p>");
		},
		error: function(exception){console.log('ERROR');}
	})
}

function showCourseAdded(){
	
	var name = $('#textSearch').val().replace(new RegExp(" ", 'g'), "-").toLowerCase();
	
	$.ajax({
		type: 'GET',
		url: urlSearch "&name=" + name + "/add",
		success: function(data){
			;
		},
		error: function(exception){console.log('ERROR');}
	})
}