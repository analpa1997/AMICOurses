import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { MoodleService } from './moodle.service';
import { LoginService } from '../login/login.service';
import { Studyitem } from '../model/studyitem.model';
import {saveAs as importedSaveAs} from "file-saver";
import { Practices } from '../model/practices.model';





@Component({
  selector: 'moodle-evaluation-component',
  templateUrl: './moodleEvaluation.component.html',
  styleUrls : ['../../assets/css/student-subject.css']
})



export class MoodleEvaluationComponent implements OnInit{

  @Input()
  private subjectName : string;
  @Input()
  private courseName : string;

  practices: Studyitem [];


  private hasMoreSubmissions: boolean [];
  private isLastPractice : boolean;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService : MoodleService) {

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
            res =>{console.log(i); this.practices[i].practices = res['content']},
          );
        });
      },
      error => this.moodleService.errorHandler(error),

    );
}

getPracticeFile(practice : Studyitem){
    this.moodleService.downloadStudyItemFile(this.courseName, this.subjectName, practice);
}

modifyCalification(practice : Studyitem, practiceSubmission : Practices, newCalification : number, index : number, practiceSub: number) {
  practiceSubmission.calification = newCalification;
  console.log(newCalification);
  this.moodleService.modifyPracticeSubmission(this.courseName, this.subjectName, practice, practiceSubmission).subscribe(
    res => {
      this.practices[index].practices[practiceSub].calification = res.calification;
    },

    error => this.moodleService.errorHandler(error),
  );
}

createPractice (name : string, type: string, file : any){
  if (file.files[0] && name.length>0 && type.length>0){
    let practice = new Studyitem();
    practice.name = name;
    practice.type = type;
    this.moodleService.createPractice(this.courseName, this.subjectName, practice).subscribe(
      res => {
        this.moodleService.uploadFile(this.courseName, this.subjectName, res , file.files[0]).subscribe(
          res => this.practices.push(res),
          error => this.moodleService.errorHandler(error),
        );
      }, error => this.moodleService.errorHandler(error)
    )
  } else {
    alert ("There are empty parameters");
  }
}

  /*
  generateContent(numberModules : number) {
    this.modules  = Array(numberModules).fill(0);
    this.studyItems = Array(numberModules);
    this.studyItemsPage = Array(numberModules).fill(0);
    this.studyItemsisLast = Array(numberModules).fill(false);

    for (let i = 0; i < this.modules.length; i++){
      this.moodleService.getStudyItemsFromModule(this.courseName, this.subjectName, i+1).subscribe(
        response => {
          this.studyItems[i] = response['content'];
          this.studyItemsPage[i] = response["number"];
          this.studyItemsisLast[i] = response["last"];
        },
        error => {
          console.log("Error " + error.status);
          this.moodleService.errorHandler(error);
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

  modifyStudyItem(newName : string, newType : string, module: number, index :number){
    console.log(newName + " " + newType + " " + module + " "  +  index);

    this.studyItems[module][index].name = newName;
    this.studyItems[module][index].icon = newType;

    this.moodleService.modifyStudyItem(this.courseName, this.subjectName, this.studyItems[module][index]).subscribe(
      res => {
        this.studyItems[module][index].name = res.name;
        this.studyItems[module][index].type = res.type;
        this.studyItems[module][index].icon = res.icon;
      },

      error => {
        if (error.status == 400) {
          alert("Incorrect params. Revise that there is not an empty field");
        } else 
        this.moodleService.errorHandler(error)
      },
    )
  }

  deleteStudyItem(module : number, index : number) {
    this.moodleService.deleteStudyItem(this.courseName, this.subjectName, this.studyItems[module][index]).subscribe(
      res => this.studyItems[module].splice(index,1),
      error => this.moodleService.errorHandler(error),
    )
  }

  createStudyItem (name : string, type: string, file : any, module : number){
    if (file.files[0] && name.length>0 && type.length>0){
      let studyItem = new Studyitem();
      studyItem.name = name;
      studyItem.type = type;
      this.moodleService.createStudyItem(this.courseName, this.subjectName, module+1, studyItem,).subscribe(
        res => {
          this.moodleService.uploadFile(this.courseName, this.subjectName, res , file.files[0]).subscribe(
            res => this.studyItems[module].push(res),
            error => this.moodleService.errorHandler(error),
          );
        }, error => this.moodleService.errorHandler(error)
      )
    } else {
      alert ("There are empty parameters");
    }
  }

  */
}