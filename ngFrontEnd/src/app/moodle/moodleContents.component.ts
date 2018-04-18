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
  private studyItemsisLast : boolean []

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService : MoodleService) {
    this.modules  = Array(1).fill(0);
    this.studyItems = Array(1);
    this.studyItemsPage = Array(1).fill(0);
    this.studyItemsisLast = Array(1).fill(false);
  }

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
        this.errorHandler(error)
      },
    )
  }

  deleteStudyItem(module : number, index : number) {
    this.moodleService.deleteStudyItem(this.courseName, this.subjectName, this.studyItems[module][index]).subscribe(
      res => this.studyItems[module].splice(index,1),
      error => this.errorHandler(error),
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
            error => this.errorHandler(error),
          );
        }, error => this.errorHandler(error)
      )
    } else {
      alert ("There are empty parameters");
    }
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