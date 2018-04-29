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
  imageURL: string;

  constructor(private router: Router, private courseService: CourseService, private userService: UserService) {
    this.imageURL = environment.URL;
  }

  ngOnInit() {
    this.page = 0;
    this.courseService.getCourses(this.page, '', 'all', 'courseID').subscribe(
      courses => this.courses = courses['content'],
      error => console.log(error)
    );
    this.userService.getTeachers(this.page).subscribe(
      teachrs => this.teachers = teachrs['content'],
      error => console.log(error)

    );
  }

  updateTeacherPage(newPage: number) {
    this.page = newPage;
    this.userService.getTeachers(this.page).subscribe(
      teachrs => this.teachers = teachrs['content'],
      error => console.log(error)

    );
  }

  updateCoursePage(newPage: number) {
    this.page = newPage;
    this.courseService.getCourses(this.page, '', 'all', 'courseID').subscribe(
      courses => this.courses = courses['content'],
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
