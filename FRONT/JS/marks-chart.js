var ctx = document.getElementById("marks-chart-canvas").getContext('2d');

var myChart = new Chart (ctx, {
    type : 'bar',
    data: {
         labels: ["First exam", "Second Exam", "Third Exam", "Forth Exam", "Quint Exam"],
        datasets: [{
            label: 'Score',
            data: [7, 7.5, 2, 7, 7.7535],
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