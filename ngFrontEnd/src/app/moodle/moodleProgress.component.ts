import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { MoodleService } from './moodle.service';
import { LoginService } from '../login/login.service';
import { Studyitem } from '../model/studyitem.model';
import { saveAs as importedSaveAs } from "file-saver";
import { Practices } from '../model/practices.model';





@Component({
  selector: 'moodle-progress-component',
  templateUrl: './moodleProgress.component.html',
  styleUrls: ['../../assets/css/student-subject.css']
})



export class MoodleProgressComponent implements OnInit {

  @Input()
  private subjectName: string;
  @Input()
  private courseName: string;

  public chartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true,
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true,
          max: 10
        }
      }]
    }
  };

  public chartData: Array<{ data: any, label: any }>;
  public chartLabels: string[];
  public chartType: string = 'bar';
  public chartLegend: boolean = true;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService: MoodleService) {
    this.chartData = new Array(1);
    this.chartData[0] = { data: [], label: "" };
  }

  ngOnInit() {
    this.getChartData();
  }

  getChartData() {

    console.log(this.chartData);
    this.moodleService.getChartData(this.courseName, this.subjectName).subscribe(
      res => {
        let practicesLabels = [];
        var practicesCalifications = [];
        for (let i = 0; i < res.length; i++) {
          practicesLabels.push(res[i][0]);
          practicesCalifications.push(res[i][1]);
        }

        this.chartData[0].data = practicesCalifications;
        this.chartLabels = practicesLabels;
        this.chartData[0].label = 'Scores';
      },
      error => this.loginService.errorHandler(error),
    );
  }


}