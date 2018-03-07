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
	
