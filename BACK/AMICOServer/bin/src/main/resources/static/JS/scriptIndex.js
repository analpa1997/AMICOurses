var numPageDisplay = 0;
	var urlSearch = "";
	$('document').ready(function(){
		$('#textSearch').val("");
		urlSearch = '/api/listCourses/p';
		$.ajax({
			type: 'GET',
			url: urlSearch + (numPageDisplay) + '/',
			data: numPageDisplay,
			success: function(pageData){
				if (pageData.last == true){
					if(pageData.numberOfElements == 0) $('#btnMoreCourses').html('¡No Courses Found!');
					else $('#btnMoreCourses').html('No More courses');
					$('#btnMoreCourses').addClass('disabled');
				}
			},
			error: function(exception){alert(exception.message);}
		})
	})
	
	function showNewest(){
		$('#newbtn').html('<img src="./img/ajax-loader.gif">');
		urlSearch = '/api/listCourses/byNewest/p';
		numPageDisplay = 0;
		$.ajax({
			type: 'GET',
			url: urlSearch + numPageDisplay  + '/',
			data: numPageDisplay,
			success: function(pageData){
				if (pageData.last == true){
					if(pageData.numberOfElements == 0) $('#btnMoreCourses').html('¡No Courses Found!');
					else $('#btnMoreCourses').html('No More courses');
					$('#btnMoreCourses').addClass('disabled');
				}else{
					$('#btnMoreCourses').html('View more courses');
					$('#btnMoreCourses').removeClass('disabled');
				}
				$('#allCourses').html('');
				pageData.content.forEach(function(course){
					$('#allCourses').append('<div class="col-lg-6 col-md-12 col-sm-12 col-12">' + 
							  '<div class="p-3" align=center>' + 
							  '<a id = "link-course" href="./course/' + course.internalName + '.html">' +
							  '<h2 class="display-4">' + course.name + '</h2>' +
							  '</a><p class="lead">' + course.courseDescription + '</p>' + 
							  '<div class="container">'+ 
							  '<a href="../course/' + course.internalName + '.html">'+ 
							  '<img class="img-fluid rounded-circle imgCourse" src="' + course.urlImage + '" alt=" " >'+
		                      '</a>'+
		                      '<div class="mt-3 &nbsp"></div>'+
		                      '<div class = "text-center">Start date: ' + course.startDateString + '</div>'+
		                      '<div class = "text-center">End date: '+ course.endDateString + '</div>'+
		                      '</div></div></div>');
				});
				$('#newbtn').html('Course Type');
			},
			error: function(exception){console.log('ERROR');}
		})
	}
	
	function showByNameLike(){
		$('#searchbtn').html('<img src="./img/ajax-loader.gif">');
		var text = $('#textSearch').val();
		if (text === "") urlSearch = '/api/listCourses/p';
		else urlSearch = '/api/listCourses/partialName/' + text.replace(new RegExp(" ", 'g'), "-").toLowerCase() + '/p';
		numPageDisplay = 0;
		$.ajax({
			type: 'GET',
			url: urlSearch + numPageDisplay  + '/',
			data: numPageDisplay,
			success: function(pageData){
				if (pageData.last == true){
					if(pageData.numberOfElements == 0) $('#btnMoreCourses').html('¡No Courses Found!');
					else $('#btnMoreCourses').html('No More courses');
					$('#btnMoreCourses').addClass('disabled');
				}else{
					$('#btnMoreCourses').html('View more courses');
					$('#btnMoreCourses').removeClass('disabled');
				}
				$('#allCourses').html('');
				pageData.content.forEach(function(course){
					$('#allCourses').append('<div class="col-lg-6 col-md-12 col-sm-12 col-12">' + 
							  '<div class="p-3" align=center>' + 
							  '<a id = "link-course" href="./course/' + course.internalName + '.html">' +
							  '<h2 class="display-4">' + course.name + '</h2>' +
							  '</a><p class="lead">' + course.courseDescription + '</p>' + 
							  '<div class="container">'+ 
							  '<a href="../course/' + course.internalName + '.html">'+ 
							  '<img class="img-fluid rounded-circle imgCourse" src="' + course.urlImage + '" alt=" " >'+
		                      '</a>'+
		                      '<div class="mt-3 &nbsp"></div>'+
		                      '<div class = "text-center">Start date: ' + course.startDateString + '</div>'+
		                      '<div class = "text-center">End date: '+ course.endDateString + '</div>'+
		                      '</div></div></div>');
				});
				$('#searchbtn').html('Search');
			},
			error: function(exception){console.log('ERROR');}
		})
	}
	function showByNumberUsers(){
		$('#trendbtn').html('<img src="./img/ajax-loader.gif">');
		urlSearch = '/api/listCourses/byNumberUsers/p';
		numPageDisplay = 0;
		$.ajax({
			type: 'GET',
			url: urlSearch + numPageDisplay  + '/',
			data: numPageDisplay,
			success: function(pageData){
				if (pageData.last == true){
					if(pageData.numberOfElements == 0) $('#btnMoreCourses').html('¡No Courses Found!');
					else $('#btnMoreCourses').html('No More courses');
					$('#btnMoreCourses').addClass('disabled');
				}else{
					$('#btnMoreCourses').html('View more courses');
					$('#btnMoreCourses').removeClass('disabled');
				}
				$('#allCourses').html('');
				pageData.content.forEach(function(course){
					$('#allCourses').append('<div class="col-lg-6 col-md-12 col-sm-12 col-12">' + 
							  '<div class="p-3" align=center>' + 
							  '<a id = "link-course" href="./course/' + course.internalName + '.html">' +
							  '<h2 class="display-4">' + course.name + '</h2>' +
							  '</a><p class="lead">' + course.courseDescription + '</p>' + 
							  '<div class="container">'+ 
							  '<a href="../course/' + course.internalName + '.html">'+ 
							  '<img class="img-fluid rounded-circle imgCourse" src="' + course.urlImage + '" alt=" " >'+
		                      '</a>'+
		                      '<div class="mt-3 &nbsp"></div>'+
		                      '<div class = "text-center">Start date: ' + course.startDateString + '</div>'+
		                      '<div class = "text-center">End date: '+ course.endDateString + '</div>'+
		                      '</div></div></div>');
				});
				$('#trendbtn').html('Trending');
			},
			error: function(exception){console.log('ERROR');}
		})
	}
	
	function showByType(){
		$('#typebtn').html('<img src="./img/ajax-loader.gif">');
		var type = $('#selectedType option:selected').val();;
		console.log(type);
		urlSearch = '/api/listCourses/type/' + type.replace(new RegExp(" ", 'g'), "-").toLowerCase() + '/p';
		numPageDisplay = 0;
		$.ajax({
			type: 'GET',
			url: urlSearch + numPageDisplay  + '/',
			data: numPageDisplay,
			success: function(pageData){
				if (pageData.last == true){
					if(pageData.numberOfElements == 0) $('#btnMoreCourses').html('¡No Courses Found!');
					else $('#btnMoreCourses').html('No More courses');
					$('#btnMoreCourses').addClass('disabled');
				}else{
					$('#btnMoreCourses').html('View more courses');
					$('#btnMoreCourses').removeClass('disabled');
				}
				$('#allCourses').html('');
				pageData.content.forEach(function(course){
					$('#allCourses').append('<div class="col-lg-6 col-md-12 col-sm-12 col-12">' + 
							  '<div class="p-3" align=center>' + 
							  '<a id = "link-course" href="./course/' + course.internalName + '.html">' +
							  '<h2 class="display-4">' + course.name + '</h2>' +
							  '</a><p class="lead">' + course.courseDescription + '</p>' + 
							  '<div class="container">'+ 
							  '<a href="../course/' + course.internalName + '.html">'+ 
							  '<img class="img-fluid rounded-circle imgCourse" src="' + course.urlImage + '" alt=" " >'+
		                      '</a>'+
		                      '<div class="mt-3 &nbsp"></div>'+
		                      '<div class = "text-center">Start date: ' + course.startDateString + '</div>'+
		                      '<div class = "text-center">End date: '+ course.endDateString + '</div>'+
		                      '</div></div></div>');
				});
				$('#typebtn').html('Course Type');
			},
			error: function(exception){console.log('ERROR');}
		})
	}
	
	function showMore(){
		$('#btnMoreCourses').html('<img src = "./img/ajax-loader.gif">');
		$.ajax({
			type: 'GET',
			url: urlSearch + (numPageDisplay+1)  + '/',
			data: (numPageDisplay+1),
			success: function(pageData){
				if (pageData.last == true){
					if(pageData.numberOfElements == 0) $('#btnMoreCourses').html('¡No Courses Found!');
					else $('#btnMoreCourses').html('No More courses');
					$('#btnMoreCourses').addClass('disabled');
				}else{
					$('#btnMoreCourses').html('View more courses');
				}
				numPageDisplay++;
				pageData.content.forEach(function(course){
					$('#allCourses').append('<div class="col-lg-6 col-md-12 col-sm-12 col-12">' + 
							  '<div class="p-3" align=center>' + 
							  '<a id = "link-course" href="./course/'+ course.internalName + '.html">' +
							  '<h2 class="display-4">' + course.name + '</h2>' +
							  '</a><p class="lead">' + course.courseDescription + '</p>' + 
							  '<div class="container">'+ 
							  '<a href="../course/' + course.internalName + '.html">'+ 
							  '<img class="img-fluid rounded-circle imgCourse" src="' + course.urlImage + '" alt=" " >'+
		                      '</a>'+
		                      '<div class="mt-3 &nbsp"></div>'+
		                      '<div class = "text-center">Start date: ' + course.startDateString + '</div>'+
		                      '<div class = "text-center">End date: '+ course.endDateString + '</div>'+
		                      '</div></div></div>');
				});
			},
			error: function(exception){alert(exception);}
		})
	}