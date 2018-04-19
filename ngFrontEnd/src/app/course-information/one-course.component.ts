import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from './course.service';
import {environment} from '../../environments/environment';
import { LoginService} from '../login/login.service';

@Component({
  selector: 'app-one-course',
  templateUrl: './one-course.component.html',
  styleUrls: ['./clean-blog.component.css', './clean-blog.min.component.css']

})
export class OneCourseComponent  implements OnInit {

  id: number;
  course: Course;
  imageURL: string;

constructor(private router: Router, private loginService: LoginService, private courseService: CourseService,
            private activatedRoute: ActivatedRoute) {
    this.imageURL = environment.URL;
  }

  ngOnInit () {
    this.id = this.activatedRoute.snapshot.params['id'];

    this.courseService.oneCourse(this.id).subscribe(data => {
        this.course = data;
      },
      error => console.error(error)
    );

  }

}


