import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { LoginService } from '../login/login.service';
import { Studyitem } from '../model/studyitem.model';
import { User } from '../model/user.model';
import { SubjectListService } from './subjectList.service';
import { FormsModule } from '@angular/forms';


@Component({
  templateUrl: './subjectList.component.html',
  styleUrls: ['../../assets/css/course-overview.css']
})



export class SubjectListComponent implements OnInit {


  subjects: Subject[];
  course: Course;
  courseName: string;

  allTeachers: User[];

  selectedOptions: Boolean[][];

  URL: string;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private subjectListService: SubjectListService) {
    this.URL = environment.URL;
    this.courseName = activatedRoute.snapshot.params['courseName'];
    this.allTeachers = [];
  }

  ngOnInit() {
    this.generatePage();

  }

  generatePage() {
    this.allTeachers = [];
    this.subjectListService.getCourse(this.courseName).subscribe(
      res => {
        this.course = res;
        this.selectedOptions = new Array(this.course.subjects.length);
        this.selectedOptions.fill([]);
      },
      error => this.subjectListService.errorHandler(error),
    );

    let page = 0;
    this.getTeachers(page);
  }

  getTeachers(page: number) {
    this.subjectListService.getTeachers(page).subscribe(
      res => {
        console.log(this.allTeachers);
        res['content'].forEach(teacher => {
          if (!teacher.roles.includes("ROLE_ADMIN")) {
            this.allTeachers.push(teacher);
          }
        });
        if (!res['last']) {
          page++;
          this.getTeachers(page);
        }
      },
      //error => this.subjectListService.errorHandler(error),
    );
  }

  changeSubjectInformation(subject, name: string, description: string) {
    if (name.length > 0) {
      let subj = { internalName: subject.internalName, name: name, description: description };
      this.subjectListService.modifySubject(this.courseName, subj).subscribe(
        res => this.generatePage(),
        error => this.subjectListService.errorHandler(error),
      );
    } else {
      alert("Subject name cannot be empty");
    }
  }

  changeTeachers(index: number) {
    let newTeachers = [];
    this.selectedOptions[index].forEach((option, i) => {
      if (option) {
        newTeachers.push(this.allTeachers[i]);
      }
    });

    let subject = { internalName: this.course.subjects[index].internalName, teachers: newTeachers };
    this.subjectListService.modifySubject(this.courseName, subject).subscribe(
      res => this.generatePage(),
      error => this.subjectListService.errorHandler(error),
    );

  }

  setSelected(selectElement, index) {
    let arr = new Array(selectElement.options.length);

    for (let i = 0; i < selectElement.options.length; i++) {
      arr[i] = selectElement.options[i].selected;
    }

    this.selectedOptions[index] = arr;
  }


}