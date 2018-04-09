import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import {CourseService} from './course.service';

@Component({
  selector: 'app-subjects',
  templateUrl: './subjects.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class SubjectsComponent implements OnInit {

  course: Course;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    const courseID = activatedRoute.snapshot.params['id'];
    this.courseService.subjects(courseID).subscribe(data => {
      this.course = data;
      console.log(this.course);
    });
  }
  ngOnInit() {
  }

}
