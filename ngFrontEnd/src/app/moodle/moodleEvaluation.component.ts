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
  selector: 'moodle-evaluation-component',
  templateUrl: './moodleEvaluation.component.html',
  styleUrls: ['../../assets/css/student-subject.css']
})



export class MoodleEvaluationComponent implements OnInit {

  @Input()
  private subjectName: string;
  @Input()
  private courseName: string;

  practices: Studyitem[];


  private hasMoreSubmissions: boolean[];
  private isLastPractice: boolean;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService: MoodleService) {

  }

  ngOnInit() {
    this.getPractices()
  }


  getPractices() {
    this.moodleService.getStudyItemsPractices(this.courseName, this.subjectName).subscribe(
      res => {
        this.practices = res['content'];
        let i = 0;
        let practice;
        this.practices.forEach((practice, i) => {
          this.moodleService.getPracticesResponse(this.courseName, this.subjectName, practice).subscribe(
            res => {
              this.practices[i].practices = res['content']
              if (this.loginService.isStudent) {
                if (this.practices[i].practices.length == 0) {
                  let newPractice = new Practices();
                  newPractice.practiceName = "Not Presented";
                  newPractice.originalName = "Not Presented";
                  newPractice.owner = this.loginService.user;
                  this.practices[i].practices = [newPractice];
                }
              }
            },
          );
        });
      },
      error => this.moodleService.errorHandler(error),

    );
  }

  getPracticeFile(practice: Studyitem) {
    this.moodleService.downloadFile(this.courseName, this.subjectName, practice);
  }

  modifyCalification(practice: Studyitem, practiceSubmission: Practices, newCalification: number, index: number, practiceSub: number) {
    practiceSubmission.calification = newCalification;
    console.log(newCalification);
    this.moodleService.modifyPracticeSubmission(this.courseName, this.subjectName, practice, practiceSubmission).subscribe(
      res => {
        this.practices[index].practices[practiceSub].calification = res.calification;
      },

      error => this.moodleService.errorHandler(error),
    );
  }

  createPractice(name: string, type: string, file: any) {
    if (file.files[0] && name.length > 0 && type.length > 0) {
      let practice = new Studyitem();
      practice.name = name;
      practice.type = type;
      this.moodleService.createPractice(this.courseName, this.subjectName, practice).subscribe(
        res => {
          this.moodleService.uploadFile(this.courseName, this.subjectName, res, file.files[0]).subscribe(
            res => this.practices.push(res),
            error => this.moodleService.errorHandler(error),
          );
        }, error => this.moodleService.errorHandler(error)
      )
    } else {
      alert("There are empty parameters");
    }
  }

  savePracticeSubmission(name: string, file: any, index: number, practiceSub: number, update: boolean) {
    if (file.files[0] && name.length > 0) {
      let practiceSubmission = this.practices[index].practices[practiceSub];
      practiceSubmission.practiceName = name;
      this.moodleService.savePracticeSubmission(this.courseName, this.subjectName, this.practices[index], practiceSubmission).subscribe(
        res => {
          console.log(res);
          this.moodleService.uploadFile(this.courseName, this.subjectName, this.practices[index], file.files[0], res, update).subscribe(
            res => { this.practices[index].practices[practiceSub] = res; },
            error => this.moodleService.errorHandler(error),
          );
        },
        error => this.moodleService.errorHandler(error),
      );

    } else {
      alert("There are empty parameters");
    }
  }

  getPracticeSubmissionFile(practice : Studyitem, practiceSubmission: Practices){
      this.moodleService.downloadFile(this.courseName, this.subjectName, practice, practiceSubmission);
  }

  
}