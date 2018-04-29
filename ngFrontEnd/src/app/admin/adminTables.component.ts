import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from '../course-information/course.service';
import { User } from '../model/user.model';
import { Subject } from '../model/subject.model';
import {Course} from '../model/course.model';
import {UserService} from '../user/user.service';
import {environment} from '../../environments/environment';


@Component({
  selector: 'admin-tables-component',
  templateUrl: './adminTables.component.html',
  styleUrls : ['../../assets/css/sb-admin.css']
})

export class AdminTablesComponent implements OnInit {

  courses: Course[];
  teachers: User[];
  page: number;
  totalElements: number;
  pageSize: number;
  pageElements: number;
  imageURL: string;
  pageTeacher: number;
  totalElementsTeacher: number;
  pageSizeTeacher: number;
  pageElementsTeacher: number;

  constructor(private router: Router, private courseService: CourseService, private userService: UserService) {
    this.imageURL = environment.URL;
  }

  ngOnInit() {
    this.page = 0;
    this.pageSize = 10;
    this.courseService.getCourses(this.page, '', 'all', 'courseID').subscribe(
      courses => {
        this.courses = courses['content'];
        this.pageElements = courses['content'].length;
        this.totalElements = courses['totalElements'];
      },
      error => console.log(error)
    );
    this.pageSizeTeacher = 10;
    this.pageTeacher = 0;
    this.userService.getTeachers(this.pageTeacher).subscribe(
      teachrs => {
        this.teachers = teachrs['content'];
        this.pageElementsTeacher = teachrs['content'].length;
        this.totalElementsTeacher = teachrs['totalElements'];
      },
      error => console.log(error)

    );
  }

  updateTeacherPage(newPage: number) {
    this.pageTeacher = newPage;
    if(this.pageTeacher != 0) {
      newPage -= 1;
    }
    this.userService.getTeachers(newPage).subscribe(
      teachrs => {
        this.teachers = teachrs['content'];
        console.log(teachrs['content']);
        this.pageElementsTeacher = teachrs['content'].length;
      },
      error => console.log(error)

    );
  }

  updateCoursePage(newPage: number) {
    this.page = newPage;
    if(this.page != 0) {
      newPage -= 1;
    }
    this.courseService.getCourses(newPage, '', 'all', 'courseID').subscribe(
      courses => {
        this.courses = courses['content']
        this.pageElements = courses['content'].length;
      },
      error => console.log(error)
    );
  }

  deleteCourse(internalName: string){
    this.courseService.deleteCourse(internalName).subscribe(
        res => this.updateCoursePage(this.page),
          error => console.log(error)
    );
  }

  modifyCourse(newName: string, newLanguage: string, newType: string, newDescription: string, newCourse: Course){
    newCourse.name = newName;
    newCourse.courseDescription = newDescription;
    newCourse.courseLanguage = newLanguage;
    newCourse.type = newType;
    this.courseService.modifyCourse(newCourse).subscribe(
      res => this.updateCoursePage(this.page),
      error => console.log(error)
    );
  }

  generateCourses(){
    this.courseService.getCourses(this.page, '', 'all', 'courseID').subscribe(
      courses => this.courses = courses['content'],
      error => console.log(error)
    );
  }

  deleteTeacher(teacherInternalName: string){
    this.userService.deleteUser(teacherInternalName).subscribe(
      res => this.updateTeacherPage(this.page),
        error => console.log(error)
    );

  }
}
