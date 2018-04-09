import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import {CourseService} from './course.service';

@Component({
  selector: 'app-one-course',
  templateUrl: './one-course.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class OneCourseComponent implements OnInit {

  course: Course;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    const courseID = activatedRoute.snapshot.params['id'];
    this.courseService.oneCourse(courseID).subscribe(data => {
      this.course = data;
      console.log(this.course);
    });
  }
  ngOnInit() {
  }

}
