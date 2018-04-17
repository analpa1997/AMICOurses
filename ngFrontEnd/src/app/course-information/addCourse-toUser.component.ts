import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from './course.service';
import { LoginService } from '../login/login.service';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-add-course-to-User',
  templateUrl: './one-course.component.html',
  styleUrls: ['./clean-blog.component.css']

})
export class AddCourseToUserComponent  implements OnInit {

  id: number;
  course: Course;
  imageURL: string;

  constructor(private router: Router, private loginService: LoginService, private courseService: CourseService,
              private activatedRoute: ActivatedRoute) {
    this.imageURL = environment.URL;
  }

  ngOnInit () {
    this.id = this.activatedRoute.snapshot.params['id'];

    this.courseService.addCourseToUser(this.id).subscribe(course => {},
      error => alert(error)
    );
  }
}
