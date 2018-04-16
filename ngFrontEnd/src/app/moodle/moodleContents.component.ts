import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { MoodleService } from './moodle.service';
import { LoginService } from '../login/login.service';
import { Studyitem } from '../model/studyitem.model';
import {saveAs as importedSaveAs} from "file-saver";





@Component({
  selector: 'moodle-contents-component',
  templateUrl: './moodleContents.component.html',
  styleUrls : ['../../assets/css/student-subject.css']
})



export class MoodleContentsComponent{

   

  @Input()
  private subjectName : string;
  @Input()
  private courseName : string;

  private modules: number [];
  private studyItems : Studyitem [][];
  private studyItemsPage : number [];
  private studyItemsAreMore : boolean []

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService : MoodleService) {
    this.modules  = Array(1).fill(0);
    this.studyItems = Array(1);
    this.studyItemsPage = Array(1).fill(0);
    this.studyItemsAreMore = Array(1).fill(false);
  }

  generateContent(numberModules : number) {
    this.modules  = Array(numberModules).fill(0);
    this.studyItems = Array(numberModules);
    this.studyItemsPage = Array(numberModules).fill(0);
    this.studyItemsAreMore = Array(numberModules).fill(false);

    for (let i = 0; i < this.modules.length; i++){
      this.moodleService.getStudyItemsFromModule(this.courseName, this.subjectName, i+1).subscribe(
        response => {
          this.studyItems[i] = response['content'];
          this.studyItemsPage[i] = response["number"];
          this.studyItemsAreMore[i] = response["last"];
        },
        error => {
          console.log("Error " + error.status);
          this.errorHandler(error);
        }
      );
    }
  }

  getStudyItemFile(studyItem : Studyitem){

    this.moodleService.getStudyItemFile(this.courseName, this.subjectName, studyItem.studyItemID).subscribe(
      res => {
          importedSaveAs(res, studyItem.originalName);   
      },

      error => console.log
    );
  }

  errorHandler (error: any){
    if (error.status==401) {
      this.router.navigate(['/login']); //Forbidden
    }

    if (error.status == 500) {
      this.router.navigate(['/error404']); //Must be a 500 error
    }

    this.router.navigate(['/error404']);
  }
}