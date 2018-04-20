import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from './course.service';
import { LoginService } from '../login/login.service';
import { environment } from '../../environments/environment';


@Component({
  selector: 'app-addCourse-toUser',
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

    this.courseService.addCourseToUser(this.id).subscribe(
      _ => {
        window.alert('Trying ....'),
        this.router.navigate(['/profile', this.loginService.user.internalName]);
      },
          error => { window.history.back(),
                          window.alert('You are already registered in this course');
          }
    );
  }


}
