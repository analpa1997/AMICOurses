import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import {CourseService} from './course.service';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class SkillsComponent implements OnInit {

  course: Course;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    const courseID = activatedRoute.snapshot.params['id'];
    this.courseService.skills(courseID).subscribe(data => {
      this.course = data;
      console.log(this.course);
    });
  }
  ngOnInit() {
  }
}
