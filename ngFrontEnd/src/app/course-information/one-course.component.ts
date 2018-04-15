import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from './course.service';
import {environment} from '../../environments/environment';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-one-course',
  templateUrl: './one-course.component.html',
  styleUrls: ['./clean-blog.component.css']

})
export class OneCourseComponent  implements OnInit {

  id: number;
  course: Course;
  imageURL: string;

constructor(private router: Router, private courseService: CourseService, private activatedRoute: ActivatedRoute, private _sanitizer: DomSanitizer) {
    this.imageURL = environment.URL;
  }

  public sanitizeImage(image: string) {
    return this._sanitizer.bypassSecurityTrustStyle(`url(${image}`);
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


