//var practicesCntx = document.getElementById("marks-practices-chart-canvas").getContext('2d');

// AJAX CALL TO RETRIEVE THE INFO FROM THE SERVER

/* 
$document.ready(function () {
	
	$ajax({
		url:"/api/moodle/getMarks/practices/"
	}).done(function(practices){
		console.log(practices);
		
		practicesLabels [];
		practicesCalifications[];
		
		for (practice in practices)	{
			practicesLabels.push(practice[0]);
			practicesCalifications.push(practice[1]);
		}
		var myChart = new Chart (practicesCntx, {
		    type : 'bar',
		    data: {
		         labels: practicesLabels,
		        datasets: [{
		            label: 'Score',
		            data: practicesCalifications,
		            backgroundColor:'rgba(255, 99, 132, 0.9)',
		            borderColor: 'rgba(255,99,132,1)',
		            borderWidth: 1
		        }]
		    },
		    options: {
		        scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero:true
		                }
		            }]
		        }
		    }   
		});
	});
});

		

*/