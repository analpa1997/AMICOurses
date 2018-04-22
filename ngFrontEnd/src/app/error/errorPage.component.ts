import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';


@Component({
  templateUrl: './errorPage.component.html',
  styleUrls: ['../../assets/css/404Error.css']
})



export class ErrorPage {


  error: string;
  message : string [];

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    this.error = "404";
    this.message = ["", ""];
    this.error = (activatedRoute.snapshot.params['error']);
    console.log(this.error);
    switch (this.error) {
      case "401": {
        this.message[0] = "Unauthorised" ;this.message[1] = "You don't have permission to access that resource"; break;
      }
      case "500": {
        this.message[0] = "Internal Server Error"; this.message[1] = "Internal server error, if the problem continues please contact us at amicourses@invented-mail.xyz"; break;
      }
      case "404": {
        this.message[0] = "Not found"; this.message[1] = "The resource you requested was not found!...."; break;
      }
      default: {
        this.message[0] = "Error"; this.message[1] = "Error " + this.error + ", if the problem continues please contact us at amicourses@invented-mail.xyz "; break;
      }
    }
  }



}