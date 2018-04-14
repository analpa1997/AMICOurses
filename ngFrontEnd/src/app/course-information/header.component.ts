import {Component, Input} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';


@Component({
  selector: 'app-course-information-header',
  templateUrl: './header.component.html',
  styleUrls: ['./clean-blog.component.css']
})
export class HeaderComponent {

  @Input()
  private course: Course ;

  constructor(private router: Router) {
  }

  goDescription () {
    this.router.navigate(['oneCourse', this.course.courseID]);
  }

  goSkills () {

    this.router.navigate(['oneCourse', this.course.courseID, 'skills']);
  }

  goSubjects () {

    this.router.navigate(['oneCourse', this.course.courseID, 'subjects']);
  }
}
